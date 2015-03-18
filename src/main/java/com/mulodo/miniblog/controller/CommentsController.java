package com.mulodo.miniblog.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.form.AddCommentsForm;
import com.mulodo.miniblog.form.EditCommentsForm;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.CommentsService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.util.ReturnFormat;
import com.mulodo.miniblog.util.Status;

@Controller
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class CommentsController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CommentsService commentsService;

	@Autowired
	private PostsService postsService;
	
	@Autowired
    private TokenService tokenService;

	@POST
	@Path("/{id_posts}/comments/add")
	@ValidateRequest
	public Response add(@PathParam("id_posts") int id_posts,
			@HeaderParam("token") String accesstoken,
			@Form @Valid AddCommentsForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.comment == null || id_posts <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Account acc = accountService.checkToken(accesstoken);
		Posts p = postsService.get(id_posts);
		Comments c = form.setData(acc, p);
		int result = commentsService.create(c);
		if (result <= 0) {
			json.meta.code(4006);
			return Response.status(Status.STATUS_4006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "add comments success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@PUT
	@Path("/{id_posts}/comments/update/{id}")
	@ValidateRequest
	public Response update(@PathParam("id") int id,
			@PathParam("id_posts") int id_posts,
			@Form @Valid EditCommentsForm form,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0 || id_posts <= 0 || form.comment == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Comments c = commentsService.get(id);
		if (c == null) {
			json.meta.code(4004);
			return Response.status(Status.STATUS_4004).entity(json).build();
		}
		if (c.getPosts().getId() != id_posts) {
			json.meta.code(4008);
			return Response.status(Status.STATUS_4008).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (a.getId() != c.getAccount().getId()) {
			json.meta.code(4009);
			return Response.status(Status.STATUS_4009).entity(json).build();
		}
		c.setComment(form.comment);
		c.setModified_at(Calendar.getInstance().getTime());
		c.setPosts(postsService.get(id_posts));
		boolean result = commentsService.update(c);
		if (result == false) {
			json.meta.code(4002);
			return Response.status(Status.STATUS_4002).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "edit commetns success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@DELETE
	@Path("/{id_posts}/comments/delete/{id}")
	public Response delete(@PathParam("id") int id,
			@PathParam("id_posts") int id_posts,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0 || id_posts <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Comments c = commentsService.get(id);
		if (c == null) {
			json.meta.code(4004);
			return Response.status(Status.STATUS_4004).entity(json).build();
		}
		if (c.getPosts().getId() != id_posts) {
			json.meta.code(4008);
			return Response.status(Status.STATUS_4008).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (a.getId() != c.getAccount().getId()) {
			json.meta.code(4009);
			return Response.status(Status.STATUS_4009).entity(json).build();
		}
		boolean result = commentsService.delete(c);
		if (result == false) {
			json.meta.code(4003);
			return Response.status(Status.STATUS_4003).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "delete comments success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@GET
    @Path("/comment/getcomment/{id}")
    public Response get(@PathParam("id") int id) {
        ReturnFormat json = new ReturnFormat();
        if (id <= 0) {
            json.meta.code(1001);
            return Response.status(Status.STATUS_1001).entity(json).build();
        }
        Comments c = commentsService.get(id);
        if (c == null) {
            json.meta.code(4004);
            return Response.status(Status.STATUS_4004).entity(json).build();
        }
        json.meta.code(200);
        json.meta.description = "get comments success!";
        json.data = c;
        return Response.status(Status.STATUS_200).entity(json).build();
    }
	
	@GET
	@Path("/comments/getcommentsofuser")
	public Response getCommentsOfUser(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		Account a = accountService.checkToken(accesstoken);
		List<Comments> comments = commentsService.getCommentsOfUser(a.getId());
		if (comments == null) {
			json.meta.code(4005);
			return Response.status(Status.STATUS_4005).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "show all comments of user success!";
		json.data = comments;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("/{id_posts}/comments/getcommentsofposts")
	public Response getCommentsOfPosts(@PathParam("id_posts") int id_posts) {
		ReturnFormat json = new ReturnFormat();
		if (id_posts <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		List<Comments> comments = commentsService.getCommentsOfPosts(id_posts);
		if (comments == null) {
			json.meta.code(4006);
			return Response.status(Status.STATUS_4006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "show all comments of posts success!";
		json.data = comments;
		return Response.status(Status.STATUS_200).entity(json).build();
	}
}
