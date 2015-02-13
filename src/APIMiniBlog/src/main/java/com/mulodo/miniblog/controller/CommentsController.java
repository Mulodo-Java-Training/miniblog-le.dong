package com.mulodo.miniblog.controller;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.CommentsService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.util.ReturnFormat;
import com.mulodo.miniblog.util.Status;

@Controller
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class CommentsController {
	// variable AccountService
	@Autowired
	private AccountService accountService;
	// variable CommentsService
	@Autowired
	private CommentsService commentsService;
	// variable PostsService
	@Autowired
	private PostsService postsService;

	// method add Comments
	@POST
	@Path("/{id_posts}/comments/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("id_posts") int id_posts,
			@FormParam("comment") String comment,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (comment != null && id_posts != 0 && accesstoken != null) {
			Comments c = new Comments();
			c.setComment(comment);
			c.setCreate_at(new Date());
			c.setModified_at(new Date());
			c.setAccount(accountService.checkToken(accesstoken));
			c.setPosts(postsService.get(id_posts));
			boolean result = commentsService.create(c);
			if (result) {
				json.meta.code(200);
				json.meta.description = "add comments success!";
				return Response.status(Status.STATUS_200).entity(json).build();
			} else {
				json.meta.code(4006);
				return Response.status(Status.STATUS_4006).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method update Comments
	@PUT
	@Path("/{id_posts}/comments/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id,
			@PathParam("id_posts") int id_posts,
			@FormParam("comment") String comment,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id != 0 && comment != null && accesstoken != null) {
			Comments c = commentsService.get(id);
			if (c != null) {
				Account a = accountService.checkToken(accesstoken);
				if (a.getId() == c.getAccount().getId()) {
					c.setComment(comment);
					c.setModified_at(new Date());
					c.setPosts(postsService.get(id_posts));
					boolean result = commentsService.update(c);
					if (result) {
						json.meta.code(200);
						json.meta.description = "edit commetns success!";
						return Response.status(Status.STATUS_200).entity(json)
								.build();
					} else {
						json.meta.code(4002);
						return Response.status(Status.STATUS_4002).entity(json)
								.build();
					}
				} else {
					json.meta.code(4009);
					return Response.status(Status.STATUS_4009).entity(json)
							.build();
				}
			} else {
				json.meta.code(4004);
				return Response.status(Status.STATUS_4004).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method delete Comments
	@DELETE
	@Path("/{id_posts}/comments/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,
			@PathParam("id_posts") int id_posts,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id != 0 && accesstoken != null) {
			Comments c = commentsService.get(id);
			if (c != null) {
				if (c.getPosts().getId() == id_posts) {
					Account a = accountService.checkToken(accesstoken);
					if (a.getId() == c.getAccount().getId()) {
						boolean result = commentsService.delete(c);
						if (result) {
							json.meta.code(200);
							json.meta.description = "delete comments success!";
							return Response.status(Status.STATUS_200)
									.entity(json).build();
						} else {
							json.meta.code(4003);
							return Response.status(Status.STATUS_4003)
									.entity(json).build();
						}
					} else {
						json.meta.code(4009);
						return Response.status(Status.STATUS_4009).entity(json)
								.build();
					}
				} else {
					json.meta.code(4008);
					return Response.status(Status.STATUS_4008).entity(json)
							.build();
				}
			} else {
				json.meta.code(4004);
				return Response.status(Status.STATUS_4004).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method getCommentsOfUser
	@GET
	@Path("/comments/getcommentsofuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsOfUser(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		Account a = accountService.checkToken(accesstoken);
		List<Comments> comments = commentsService.getCommentsOfUser(a.getId());
		if (comments != null) {
			json.meta.code(200);
			json.meta.description = "show all comments of user success!";
			json.data = comments;
			return Response.status(Status.STATUS_200).entity(json).build();
		} else {
			json.meta.code(4005);
			return Response.status(Status.STATUS_4005).entity(json).build();
		}
	}

	// method getCommentsOfPosts
	@GET
	@Path("/{id_posts}/comments/getcommentsofposts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsOfPosts(@PathParam("id_posts") int id_posts) {
		ReturnFormat json = new ReturnFormat();
		if (id_posts != 0) {
			List<Comments> comments = commentsService
					.getCommentsOfPosts(id_posts);
			if (comments != null) {
				json.meta.code(200);
				json.meta.description = "show all comments of posts success!";
				json.data = comments;
				return Response.status(Status.STATUS_200).entity(json).build();
			} else {
				json.meta.code(4006);
				return Response.status(Status.STATUS_4006).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}
}
