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
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.util.Status;

@Controller
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class PostsController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PostsService postsService;

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@FormParam("title") String title,
			@FormParam("content") String content,
			@HeaderParam("token") String accesstoken) {
		if (title != null && content != null && accesstoken != null) {
			Posts p = new Posts();
			p.setTitle(title);
			p.setContent(content);
			p.setCreate_at(new Date());
			p.setModified_at(new Date());
			p.setStatus(true);
			p.setAccount(accountService.getAccountByToken(accesstoken));
			boolean result = postsService.create(p);
			if (result) {
				return Response.status(Status.STATUS_200)
						.entity("Create posts success!Detail: " + p).build();
			} else {
				return Response.status(Status.STATUS_3001)
						.entity("create posts failled!").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("title and content and accesstoken required!:").build();
	}

	@PUT
	@Path("/active/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response active(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		if (id > 0 && accesstoken != null) {
			Posts p = postsService.get(id);
			if (p != null) {
				Account a = accountService.getAccountByToken(accesstoken);
				if (a.getId() == p.getAccount().getId()) {
					p.setStatus(true);
					boolean result = postsService.update(p);
					if (result) {
						return Response
								.status(Status.STATUS_200)
								.entity("Active posts success!Detail: "
										+ p.isStatus()).build();
					} else {
						return Response.status(Status.STATUS_3002)
								.entity("Active Posts Failled!").build();
					}
				} else {
					return Response.status(Status.STATUS_3008)
							.entity("posts is not of account with token: "
									+ accesstoken).build();
				}
			} else {
				return Response.status(Status.STATUS_3005)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("id and accesstoken is required!").build();
	}

	@PUT
	@Path("/deactive/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deactive(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		if (id > 0 && accesstoken != null) {
			Posts p = postsService.get(id);
			if (p != null) {
				Account a = accountService.getAccountByToken(accesstoken);
				if (a.getId() == p.getAccount().getId()) {
					p.setStatus(false);
					boolean result = postsService.update(p);
					if (result) {
						return Response
								.status(Status.STATUS_200)
								.entity("Deactive posts success!Detail: "
										+ p.isStatus()).build();
					} else {
						return Response.status(Status.STATUS_3002)
								.entity("Deactive Posts Failled!").build();
					}
				} else {
					return Response
							.status(Status.STATUS_3008)
							.entity("posts is not of account with token: "
									+ accesstoken).build();
				}
			} else {
				return Response.status(Status.STATUS_3005)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("id and accesstoken is required!").build();
	}

	@PUT
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id,
			@FormParam("title") String title,
			@FormParam("content") String content,
			@HeaderParam("token") String accesstoken) {
		if (id != 0 && title != null && content != null && accesstoken != null) {
			Posts p = postsService.get(id);
			if (p != null) {
				Account a = accountService.getAccountByToken(accesstoken);
				if (a.getId() == p.getAccount().getId()) {
					p.setTitle(title);
					p.setContent(content);
					p.setModified_at(new Date());
					boolean result = postsService.update(p);
					if (result) {
						return Response.status(Status.STATUS_200)
								.entity("Update posts success!Detail: " + p)
								.build();
					} else {
						return Response.status(Status.STATUS_3003)
								.entity("update Posts Failled!").build();
					}
				} else {
					return Response
							.status(Status.STATUS_3008)
							.entity("posts is not of account with token: "
									+ accesstoken).build();
				}
			} else {
				return Response.status(Status.STATUS_3005)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("id,title,content,accesstoken is required!").build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		if (id != 0 && accesstoken != null) {
			Posts p = postsService.get(id);
			if (p != null) {
				Account a = accountService.getAccountByToken(accesstoken);
				if (a.getId() == p.getAccount().getId()) {
					boolean result = postsService.delete(p);
					if (result) {
						return Response.status(Status.STATUS_200)
								.entity("delete posts success!")
								.build();
					} else {
						return Response.status(Status.STATUS_3004)
								.entity("delete Posts Failled!").build();
					}
				} else {
					return Response
							.status(Status.STATUS_3008)
							.entity("posts is not of account with token: "
									+ accesstoken).build();
				}
			} else {
				return Response.status(Status.STATUS_3005)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("id and accesstoken is required!").build();
	}

	@GET
	@Path("/get/{id}")
	public Response get(@PathParam("id") int id) {
		if (id != 0) {
			Posts p = postsService.get(id);
			if (p != null) {
				return Response.status(Status.STATUS_200).entity("Info:" + p)
						.build();
			} else {
				return Response.status(Status.STATUS_3005)
						.entity("id not existed!").build();
			}
		}
		return Response.status(Status.STATUS_1001).entity("id is required!")
				.build();
	}

	@GET
	@Path("/getallactive")
	public Response getAllActive() {
		List<Posts> posts = postsService.getAllPostsActive();
		if (posts != null) {
			return Response.status(Status.STATUS_200).entity("Data:" + posts)
					.build();
		}
		return Response.status(Status.STATUS_3007).entity("No Data").build();
	}

	@GET
	@Path("/getalldeactive")
	public Response getAllDeactive() {
		List<Posts> posts = postsService.getAllPostsDeactive();
		if (posts != null) {
			return Response.status(Status.STATUS_200).entity("Data:" + posts)
					.build();
		}
		return Response.status(Status.STATUS_3007).entity("No Data").build();
	}

	@GET
	@Path("/getpostsofuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostsOfUser(@HeaderParam("token") String accesstoken) {

		Account a = accountService.getAccountByToken(accesstoken);
		List<Posts> posts = postsService.getAllPostsByUser(a.getId());
		if (posts != null) {
			return Response.status(Status.STATUS_200)
					.entity("All Posts of User:" + posts).build();
		}
		return Response.status(Status.STATUS_3006)
				.entity("not found posts with account!!").build();
	}
}
