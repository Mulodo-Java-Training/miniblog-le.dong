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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.form.CreatePostsForm;
import com.mulodo.miniblog.form.UpdatePostsForm;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.PostsService;
import com.mulodo.miniblog.util.ReturnFormat;
import com.mulodo.miniblog.util.Status;

@Controller
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PostsController {
	// method AccountService
	@Autowired
	private AccountService accountService;
	// method PostsService
	@Autowired
	private PostsService postsService;

	// method create Posts
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@HeaderParam("token") String accesstoken,
			@Form @Valid CreatePostsForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.title != null && form.content != null) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Account acc = accountService.checkToken(accesstoken);
				Posts p = form.setData(acc);
				int result = postsService.create(p);
				if (result > 0) {
					json.meta.code(200);
					json.meta.description = "create posts success!";
					return Response.status(Status.STATUS_200).entity(json)
							.build();
				} else {
					json.meta.code(3001);
					return Response.status(Status.STATUS_3001).entity(json)
							.build();
				}
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method active Posts
	@PUT
	@Path("active/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response active(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id > 0) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Posts p = postsService.get(id);
				if (p != null) {
					Account a = accountService.checkToken(accesstoken);
					if (a.getId() == p.getAccount().getId()) {
						boolean result = postsService.active(p.getId());
						if (result) {
							json.meta.code(200);
							json.meta.description = "active posts success!";
							return Response.status(Status.STATUS_200)
									.entity(json).build();
						} else {
							json.meta.code(3002);
							return Response.status(Status.STATUS_3002)
									.entity(json).build();
						}
					} else {
						json.meta.code(3008);
						return Response.status(Status.STATUS_3008).entity(json)
								.build();
					}
				} else {
					json.meta.code(3005);
					return Response.status(Status.STATUS_3005).entity(json)
							.build();
				}
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method deactive Posts
	@PUT
	@Path("deactive/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deactive(@PathParam("id") int id,
			@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		if (id > 0) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Posts p = postsService.get(id);
				if (p != null) {
					Account a = accountService.checkToken(accesstoken);
					if (a.getId() == p.getAccount().getId()) {
						p.setStatus(false);
						boolean result = postsService.deactive(p.getId());
						if (result) {
							json.meta.code(200);
							json.meta.description = "deactive posts success!";
							return Response.status(Status.STATUS_200)
									.entity(json).build();
						} else {
							json.meta.code(3002);
							return Response.status(Status.STATUS_3002)
									.entity(json).build();
						}
					} else {
						json.meta.code(3008);
						return Response.status(Status.STATUS_3008).entity(json)
								.build();
					}
				} else {
					json.meta.code(3005);
					return Response.status(Status.STATUS_3005).entity(json)
							.build();
				}
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method update Posts
	@PUT
	@Path("updateposts/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("token") String accesstoken,
			@PathParam("id") int id, @Form @Valid UpdatePostsForm form) {
		ReturnFormat json = new ReturnFormat();
		if (id > 0 && form.title != null && form.content != null) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Posts p = postsService.get(id);
				if (p != null) {
					Account a = accountService.checkToken(accesstoken);
					if (a.getId() == p.getAccount().getId()) {
						p.setTitle(form.title);
						p.setContent(form.content);
						p.setModified_at(Calendar.getInstance().getTime());
						boolean result = postsService.update(p);
						if (result) {
							json.meta.code(200);
							json.meta.description = "update posts success!";
							return Response.status(Status.STATUS_200)
									.entity(json).build();
						} else {
							json.meta.code(3003);
							return Response.status(Status.STATUS_3003)
									.entity(json).build();
						}
					} else {
						json.meta.code(3008);
						return Response.status(Status.STATUS_3008).entity(json)
								.build();
					}
				} else {
					json.meta.code(3005);
					return Response.status(Status.STATUS_3005).entity(json)
							.build();
				}
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method delete Posts
	@DELETE
	@Path("deleteposts/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@HeaderParam("token") String accesstoken,
			@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		if (id > 0) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Posts p = postsService.get(id);
				if (p != null) {
					Account a = accountService.checkToken(accesstoken);
					if (a.getId() == p.getAccount().getId()) {
						boolean result = postsService.delete(p);
						if (result) {
							json.meta.code(200);
							json.meta.description = "delete posts success!";
							return Response.status(Status.STATUS_200)
									.entity(json).build();
						} else {
							json.meta.code(3004);
							return Response.status(Status.STATUS_3004)
									.entity(json).build();
						}
					} else {
						json.meta.code(3008);
						return Response.status(Status.STATUS_3008).entity(json)
								.build();
					}
				} else {
					json.meta.code(3005);
					return Response.status(Status.STATUS_3005).entity(json)
							.build();
				}
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method get Posts
	@GET
	@Path("getposts/{id}")
	public Response get(@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		if (id != 0) {
			Posts p = postsService.get(id);
			if (p != null) {
				json.meta.code(200);
				json.meta.description = "get posts success!";
				json.data = p;
				return Response.status(Status.STATUS_200).entity(json).build();
			} else {
				return Response.status(Status.STATUS_3005)
						.entity("id not existed!").build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method getAllPostsActive
	@GET
	@Path("getallactive")
	public Response getAllActive() {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsActive();
		if (posts != null) {
			json.meta.code(200);
			json.meta.description = "show all posts success!";
			json.data = posts;
			return Response.status(Status.STATUS_200).entity(json).build();
		}
		json.meta.code(3007);
		return Response.status(Status.STATUS_3007).entity(json).build();
	}

	// method getAllPostsDeactive
	@GET
	@Path("getalldeactive")
	public Response getAllDeactive() {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsDeactive();
		if (posts != null) {
			json.meta.code(200);
			json.meta.description = "show all posts success!";
			json.data = posts;
			return Response.status(Status.STATUS_200).entity(json).build();
		}
		json.meta.code(3007);
		return Response.status(Status.STATUS_3007).entity(json).build();
	}

	// method getAllPostsOfUser
	@GET
	@Path("getpostsofuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostsOfUser(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		Account a = accountService.checkToken(accesstoken);
		List<Posts> posts = postsService.getAllPostsByUser(a.getId());
		if (posts != null) {
			json.meta.code(200);
			json.meta.description = "show all posts of user success!";
			json.data = posts;
			return Response.status(Status.STATUS_200).entity(json).build();
		}
		json.meta.code(3006);
		return Response.status(Status.STATUS_3006).entity(json).build();
	}

	// method search
	@GET
	@Path("searchposts/{content}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("content") String content) {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsByContent(content);
		if (posts != null) {
			json.meta.code(200);
			json.meta.description = "result";
			json.data = posts;
			return Response.status(Status.STATUS_200).entity(json).build();
		}
		json.meta.code(3006);
		return Response.status(Status.STATUS_3006).entity(json).build();
	}

	// method top
	@GET
	@Path("top")
	@Produces(MediaType.APPLICATION_JSON)
	public Response top() {
		ReturnFormat json = new ReturnFormat();
		List<Posts> posts = postsService.getAllPostsTop();
		if (posts != null) {
			json.meta.code(200);
			json.meta.description = "top posts";
			json.data = posts;
			return Response.status(Status.STATUS_200).entity(json).build();
		}
		json.meta.code(3006);
		return Response.status(Status.STATUS_3006).entity(json).build();
	}
}