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

	@POST
	@Path("/{id_posts}/comments/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@PathParam("id_posts") int id_posts,
			@FormParam("comment") String comment,
			@HeaderParam("token") String accesstoken) {
		if (comment != null && id_posts != 0 && accesstoken != null) {
			Comments c = new Comments();
			c.setComment(comment);
			c.setCreate_at(new Date());
			c.setModified_at(new Date());
			c.setAccount(accountService.getAccountByToken(accesstoken));
			c.setPosts(postsService.get(id_posts));
			boolean result = commentsService.create(c);
			if (result) {
				return Response.status(Status.status_200)
						.entity("add comments success!Details:" + c).build();
			} else {
				return Response.status(Status.status_4006)
						.entity("id_posts not existed!add comments failled!")
						.build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("comment and id_posts and accesstoken required!:").build();
	}

	@PUT
	@Path("/{id_posts}/comments/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id,
			@PathParam("id_posts") int id_posts,
			@FormParam("comment") String comment,
			@HeaderParam("token") String accesstoken) {
		if (id != 0 && comment != null && accesstoken != null) {
			Comments c = commentsService.get(id);
			if (c != null) {
				Account a = accountService.getAccountByToken(accesstoken);
				if (a.getId() == c.getAccount().getId()) {
					c.setComment(comment);
					c.setModified_at(new Date());
					c.setPosts(postsService.get(id_posts));
					boolean result = commentsService.update(c);
					if (result) {
						return Response.status(Status.status_200)
								.entity("Update comments success!Detail: " + c)
								.build();
					} else {
						return Response.status(Status.status_4002)
								.entity("update comments Failled!").build();
					}
				} else {
					return Response
							.status(Status.status_4009)
							.entity("comments not of account token :"
									+ accesstoken).build();
				}
			} else {
				return Response.status(Status.status_4004)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("id,content,accesstoken is required!").build();
	}

	@DELETE
	@Path("/{id_posts}/comments/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,
			@PathParam("id_posts") int id_posts,
			@HeaderParam("token") String accesstoken) {
		if (id != 0 && accesstoken != null) {
			Comments c = commentsService.get(id);
			if (c != null) {
				if (c.getPosts().getId() == id_posts) {
					Account a = accountService.getAccountByToken(accesstoken);
					if (a.getId() == c.getAccount().getId()) {
						boolean result = commentsService.delete(c);
						if (result) {
							return Response
									.status(Status.status_200)
									.entity("delete comments success!ID: " + id)
									.build();
						} else {
							return Response.status(Status.status_4003)
									.entity("delete comments Failled!").build();
						}
					} else {
						return Response
								.status(Status.status_4009)
								.entity("comments not of account token :"
										+ accesstoken).build();
					}
				} else {
					return Response.status(Status.status_4008)
							.entity("comment not of posts").build();
				}
			} else {
				return Response.status(Status.status_4004)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.status_1001).entity("id accesstoken is required!")
				.build();
	}

	@GET
	@Path("/comments/getcommentsofuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getcommentsofuser(@HeaderParam("token") String accesstoken) {
		Account a = accountService.getAccountByToken(accesstoken);
			List<Comments> comments = commentsService.getCommentsOfUser(a.getId());
			if (comments != null) {
				return Response.status(Status.status_200)
						.entity("All Comments of User:" + comments).build();
			} else {
				return Response.status(Status.status_4005)
						.entity("not found comments with account!").build();
			}
		
	}

	@GET
	@Path("/comments/getcommentsofposts/{id_posts}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getcommentsofposts(@PathParam("id_posts") int id_posts) {
		if (id_posts != 0) {
			List<Comments> comments = commentsService
					.getCommentsOfPosts(id_posts);
			if (comments != null) {
				return Response.status(Status.status_200)
						.entity("All Comments of Posts:" + comments).build();
			} else {
				return Response.status(Status.status_4006)
						.entity("id_posts not existed!").build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("id_posts is required!").build();
	}
}
