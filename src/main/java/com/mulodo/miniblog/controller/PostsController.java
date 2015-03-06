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

import com.mulodo.miniblog.form.CreatePostsForm;
import com.mulodo.miniblog.form.UpdatePostsForm;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.util.ReturnFormat;
import com.mulodo.miniblog.util.Status;

@Controller
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PostsController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PostsService postsService;
	
	@Autowired
    private TokenService tokenService;

	@POST
	@Path("create")
	@ValidateRequest
	public Response create(@HeaderParam("token") String accesstoken,
			@Form @Valid CreatePostsForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.title == null || form.content == null) {
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
		Posts p = form.setData(acc);
		int result = postsService.create(p);
		if (result <= 0) {
			json.meta.code(3001);
			return Response.status(Status.STATUS_3001).entity(json).build();

		}
		json.meta.code(200);
		json.meta.description = "create posts success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@PUT
	@Path("active/{id}")
	public Response active(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Posts p = postsService.get(id);
		if (p == null) {
			json.meta.code(3005);
			return Response.status(Status.STATUS_3005).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (a.getId() != p.getAccount().getId()) {
			json.meta.code(3008);
			return Response.status(Status.STATUS_3008).entity(json).build();
		}
		boolean result = postsService.active(p.getId());
		if (result == false) {
			json.meta.code(3002);
			return Response.status(Status.STATUS_3002).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "active posts success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@PUT
	@Path("deactive/{id}")
	public Response deactive(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Posts p = postsService.get(id);
		if (p == null) {
			json.meta.code(3005);
			return Response.status(Status.STATUS_3005).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (a.getId() != p.getAccount().getId()) {
			json.meta.code(3008);
			return Response.status(Status.STATUS_3008).entity(json).build();
		}
		p.setStatus(false);
		boolean result = postsService.deactive(p.getId());
		if (result == false) {
			json.meta.code(3002);
			return Response.status(Status.STATUS_3002).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "deactive posts success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@PUT
	@Path("updateposts/{id}")
	@ValidateRequest
	public Response update(@HeaderParam("token") String accesstoken,
			@PathParam("id") int id, @Form @Valid UpdatePostsForm form) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0 && form.title == null || form.content == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}

		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Posts p = postsService.get(id);
		if (p == null) {
			json.meta.code(3005);
			return Response.status(Status.STATUS_3005).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (a.getId() != p.getAccount().getId()) {
			json.meta.code(3008);
			return Response.status(Status.STATUS_3008).entity(json).build();
		}
		p.setTitle(form.title);
		p.setContent(form.content);
		p.setModified_at(Calendar.getInstance().getTime());
		boolean result = postsService.update(p);
		if (result == false) {
			json.meta.code(3003);
			return Response.status(Status.STATUS_3003).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "update posts success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@DELETE
	@Path("deleteposts/{id}")
	public Response delete(@HeaderParam("token") String accesstoken,
			@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
		    tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Posts p = postsService.get(id);
		if (p == null) {
			json.meta.code(3005);
			return Response.status(Status.STATUS_3005).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (a.getId() != p.getAccount().getId()) {
			json.meta.code(3008);
			return Response.status(Status.STATUS_3008).entity(json).build();
		}
		boolean result = postsService.delete(p);
		if (result == false) {
			json.meta.code(3004);
			return Response.status(Status.STATUS_3004).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "delete posts success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@GET
	@Path("getposts/{id}")
	public Response get(@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		if (id <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		Posts p = postsService.get(id);
		if (p == null) {
			return Response.status(Status.STATUS_3005)
					.entity("id not existed!").build();
		}
		json.meta.code(200);
		json.meta.description = "get posts success!";
		json.data = p;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("getallactive")
	public Response getAllActive() {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsActive();
		if (posts == null) {
			json.meta.code(3007);
			return Response.status(Status.STATUS_3007).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "show all posts success!";
		json.data = posts;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("getalldeactive")
	public Response getAllDeactive() {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsDeactive();
		if (posts == null) {
			json.meta.code(3007);
			return Response.status(Status.STATUS_3007).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "show all posts success!";
		json.data = posts;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("getpostsofuser")
	public Response getPostsOfUser(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		Account a = accountService.checkToken(accesstoken);
		List<Posts> posts = postsService.getAllPostsByUser(a.getId());
		if (posts == null) {
			json.meta.code(3006);
			return Response.status(Status.STATUS_3006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "show all posts of user success!";
		json.data = posts;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("getpostsbyuser/{id}")
	public Response getPostsOfUserByID(@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsByUser(id);
		if (posts == null) {
			json.meta.code(3006);
			return Response.status(Status.STATUS_3006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "show posts by user success!";
		json.data = posts;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("searchposts/{description}")
	public Response search(@PathParam("description") String description) {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.search(description);
		if (posts == null) {
			json.meta.code(3006);
			return Response.status(Status.STATUS_3006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "result";
		json.data = posts;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("top")
	public Response top() {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsTop();
		if (posts == null) {
			json.meta.code(3006);
			return Response.status(Status.STATUS_3006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "top posts";
		json.data = posts;
		return Response.status(Status.STATUS_200).entity(json).build();
	}
}
