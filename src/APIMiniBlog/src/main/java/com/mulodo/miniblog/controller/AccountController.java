package com.mulodo.miniblog.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import returnform.AccessToken;

import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.util.MD5Hash;
import com.mulodo.miniblog.util.ReturnFormat;
import com.mulodo.miniblog.util.SendMail;
import com.mulodo.miniblog.util.Status;
import com.mulodo.miniblog.util.Util;

@Controller
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
	// variable AccountService
	@Autowired
	private AccountService accountService;
	@Context
	private HttpServletResponse response;
	@Context
	private HttpServletRequest request;

	// // home
	// @GET
	// @Path("")
	// public void home() throws ServletException, IOException {
	// request.getRequestDispatcher("index.jsp").forward(request, response);
	// }
	// method login
	@POST
	@Path("login")
	@ValidateRequest
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("username") @NotNull(message = "{username is not null}") String username,
			@FormParam("password") @NotNull(message = "{password is not null}") @Size(min = 6, message = "size min is >=6 characters") String password) {
		ReturnFormat json = new ReturnFormat();
		if (username != null && password != null) {
			Account a = accountService.login(username, MD5Hash.MD5(password));
			if (a != null) {
				Token t = new Token();
				t.setAccess_token(Util.randomString());
				t.setCreate_at(new Date());
				t.setExpired_at(new Date());
				t.setAccount(a);
				accountService.createToken(t);
				json.meta.code(200);
				json.meta.description = "login success";
				json.data = new AccessToken(t.getAccess_token());
				return Response.status(Status.STATUS_200).entity(json).build();
			} else {
				json.meta.code(2002);
				return Response.status(Status.STATUS_2002).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method register
	@POST
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("email") String email) {
		ReturnFormat json = new ReturnFormat();
		if (username != null && password != null && email != null) {
			Account a = new Account();
			a.setUsername(username);
			a.setPassword(MD5Hash.MD5(password));
			a.setEmail(email);
			a.setLastname("");
			a.setFirstname("");
			a.setCreate_at(new Date());
			a.setModified_at(new Date());
			boolean result = accountService.checkUser(username);
			if (result) {
				boolean result2 = accountService.register(a);
				if (result2) {
					json.meta.code(200);
					json.meta.description = "register success";
					return Response.status(Status.STATUS_200).entity(json)
							.build();
				} else {
					json.meta.code(2009);
					return Response.status(Status.STATUS_2009).entity(json)
							.build();
				}
			} else {
				json.meta.code(2001);
				return Response.status(Status.STATUS_2001).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method get info account
	@GET
	@Path("getuser/{id}")
	public Response getInfo(@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		if (id != 0) {
			Account a = this.accountService.getInfo(id);
			if (a != null) {
				json.meta.code(200);
				json.meta.description = "get user success!";
				json.data = a;
				return Response.status(Status.STATUS_200).entity(json).build();
			} else {
				json.meta.code(2006);
				return Response.status(Status.STATUS_2006).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method update info account
	@PUT
	@Path("updateuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("token") String accesstoken,
			@FormParam("email") String email,
			@FormParam("lastname") String lastname,
			@FormParam("firstname") String firstname,
			@FormParam("password") String password) {
		ReturnFormat json = new ReturnFormat();
		if (email != null && lastname != null && firstname != null) {
			Account a = (Account) accountService.checkToken(accesstoken);
			a.setEmail(email);
			a.setLastname(lastname);
			a.setFirstname(firstname);
			a.setModified_at(new Date());
			if (a.getPassword().equals(MD5Hash.MD5(password))) {
				boolean result = accountService.update(a);
				if (result) {
					json.meta.code(200);
					json.meta.description = "update success!";
					return Response.status(Status.STATUS_200).entity(json)
							.build();
				} else {
					json.meta.code(2004);
					return Response.status(Status.STATUS_2004).entity(json)
							.build();
				}
			} else {
				json.meta.code(2005);
				return Response.status(Status.STATUS_2005).entity(json).build();
			}

		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method change password account
	@PUT
	@Path("changepass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response chanePass(@HeaderParam("token") String accesstoken,
			@FormParam("oldpass") String oldpass,
			@FormParam("newpass") String newpass) {
		ReturnFormat json = new ReturnFormat();
		if (accesstoken != null && oldpass != null && newpass != null) {
			Account a = accountService.checkToken(accesstoken);
			if (a.getPassword().equals(MD5Hash.MD5(oldpass))) {
				a.setPassword(MD5Hash.MD5(newpass));
				boolean result = accountService.update(a);
				if (result) {
					accountService.logout(a.getId());
					SendMail.send(a.getEmail(), "[Miniblog]change password",
							"New Password:" + newpass);
					Token t = new Token();
					t.setAccess_token(Util.randomString());
					t.setCreate_at(new Date());
					t.setExpired_at(new Date());
					t.setAccount(a);
					accountService.createToken(t);
					json.meta.code(200);
					json.meta.description = "change password success!";
					json.data = new AccessToken(t.getAccess_token());
					return Response.status(Status.STATUS_200).entity(json)
							.build();
				} else {
					json.meta.code(2010);
					return Response.status(Status.STATUS_2010).entity(json)
							.build();
				}
			} else {
				json.meta.code(2005);
				return Response.status(Status.STATUS_2005).entity(json).build();
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method search account by username,lastname,firstname
	@GET
	@Path("searchuser/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@HeaderParam("token") String accesstoken,
			@PathParam("name") String name) {
		ReturnFormat json = new ReturnFormat();
		Account a = accountService.checkToken(accesstoken);
		if (a != null) {
			if (name != null) {
				List<Account> accounts = accountService.searchByName(name);
				if (accounts != null) {
					json.meta.code(200);
					json.meta.description = "result";
					json.data = accounts;
					return Response.status(Status.STATUS_200).entity(json)
							.build();
				} else {
					json.meta.code(2007);
					return Response.status(Status.STATUS_2007).entity(json)
							.build();
				}
			}
		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();

	}

	// method logout
	@POST
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		Account a = accountService.checkToken(accesstoken);
		boolean result = accountService.logout(a.getId());
		if (result) {
			json.meta.code(200);
			json.meta.description = "logout success!";
			return Response.status(Status.STATUS_200).entity(json).build();
		}
		json.meta.code(2003);
		return Response.status(Status.STATUS_2003).entity(json).build();
	}

}
