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

import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.util.MD5Hash;
import com.mulodo.miniblog.util.SendMail;
import com.mulodo.miniblog.util.Status;
import com.mulodo.miniblog.util.Util;

@Controller
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
	//variable AccountService
	@Autowired
	private AccountService accountService;
	@Context 
	private HttpServletResponse response;
    @Context 
    private HttpServletRequest request;
	// home
	@GET
	@Path("/")
	public void home() throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	//method login
	@POST
	@Path("/login")
	@ValidateRequest
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("username") @NotNull(message = "{username is not null}") String username,
			@FormParam("password") @NotNull(message = "{password is not null}") @Size(min = 6, message = "size min is >=6 characters") String password) {
		if (username != null && password != null) {
			Account a = accountService.login(username, MD5Hash.MD5(password));
			if (a != null) {
				Token t = new Token();
				t.setAccess_token(Util.randomString());
				t.setCreate_at(new Date());
				t.setExpired_at(new Date());
				t.setAccount(a);
				accountService.createToken(t);
				return Response.status(Status.STATUS_200)
						.entity("Access Token:" + t.getAccess_token() + " ")
						.build();
			} else {
				return Response.status(Status.STATUS_2002)
						.entity("username or password wrong").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("username and password required!").build();
	}
	//method register
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("email") String email) {
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
				if (result2)
					return Response.status(Status.STATUS_200)
							.entity("Details Accounts:" + a).build();
				else
					return Response.status(Status.STATUS_2009)
							.entity("register failled").build();
			} else {
				return Response.status(Status.STATUS_2001)
						.entity("username is existed!").build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("username, password,email required!").build();
	}
	//method get info account
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfo(@PathParam("id") int id) {
		if (id != 0) {
			Account a = this.accountService.getInfo(id);
			if (a != null) {
				return Response.status(Status.STATUS_200).entity("Info:" + a)
						.build();
			} else {
				return Response.status(Status.STATUS_2006)
						.entity("id not existed").build();
			}
		}
		return Response.status(Status.STATUS_1001).entity("id required!")
				.build();
	}
	//method update info account
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("token") String accesstoken,
			@FormParam("username") String username,
			@FormParam("email") String email,
			@FormParam("lastname") String lastname,
			@FormParam("firstname") String firstname,
			@FormParam("password") String password) {
		if (username != null && email != null && lastname != null
				&& firstname != null) {
			Account a = (Account) accountService.checkToken(accesstoken);
			if (a.getUsername().equals(username)) {
				a.setUsername(username);
				a.setEmail(email);
				a.setLastname(lastname);
				a.setFirstname(firstname);
				a.setModified_at(new Date());
				if (a.getPassword().equals(MD5Hash.MD5(password))) {
					boolean result = accountService.update(a);
					if (result) {
						return Response.status(Status.STATUS_200)
								.entity("Info Update:" + a).build();
					} else {
						return Response.status(Status.STATUS_2004)
								.entity("Update failed").build();
					}
				} else {
					return Response
							.status(Status.STATUS_2005)
							.entity("password not match!you cann't update info")
							.build();
				}
			} else {
				Account acc = accountService.findByUsername(username);
				if (acc == null) {
					a.setUsername(username);
					a.setEmail(email);
					a.setLastname(lastname);
					a.setFirstname(firstname);
					a.setModified_at(new Date());
					if (a.getPassword().equals(MD5Hash.MD5(password))) {
						boolean result = accountService.update(a);
						if (result) {
							return Response.status(Status.STATUS_200)
									.entity("Info Update:" + a).build();
						} else {
							return Response.status(Status.STATUS_2004)
									.entity("Update failed").build();
						}
					} else {
						return Response
								.status(Status.STATUS_2005)
								.entity("password not match!you cann't update info")
								.build();
					}
				} else {
					return Response
							.status(Status.STATUS_2001)
							.entity("username is existed!please change username!")
							.build();
				}
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("username,password,email,lastname,firstname required!")
				.build();
	}
	//method change password account
	@PUT
	@Path("/changepass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response chanePass(@HeaderParam("token") String accesstoken,
			@FormParam("oldpass") String oldpass,
			@FormParam("newpass") String newpass) {
		if (accesstoken != null && oldpass != null && newpass != null) {
			Account a = accountService.checkToken(accesstoken);
			if (a.getPassword().equals(MD5Hash.MD5(oldpass))) {
				a.setPassword(MD5Hash.MD5(newpass));
				boolean result = accountService.update(a);
				if (result) {
					accountService.logout(a.getId());
					SendMail.send("lephuongdong9494@gmail.com",
							"[Miniblog]change password", "New Password:"
									+ newpass);
					return Response.status(Status.STATUS_200)
							.entity("Change password success.").build();
				} else {
					return Response.status(Status.STATUS_2010)
							.entity("change password failed").build();
				}
			} else {
				return Response
						.status(Status.STATUS_2005)
						.entity("old password not match!you cann't change password...")
						.build();
			}
		}
		return Response.status(Status.STATUS_1001)
				.entity("accesstoken,oldpass,newpass required!").build();
	}
	//method search account by username,lastname,firstname
	@GET
	@Path("/search/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("name") String name) {
		if (name != null) {
			List<Account> accounts = accountService.searchByName(name);
			if (accounts != null) {
				return Response.status(Status.STATUS_200)
						.entity("Data: " + accounts).build();
			} else {
				return Response
						.status(Status.STATUS_2007)
						.entity("username,lastname,firstname not existed with keyword "
								+ name + "!").build();
			}
		}
		return Response.status(Status.STATUS_1001).entity("name is required!")
				.build();
	}
	//method logout
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@HeaderParam("token") String accesstoken) {
		Account a = accountService.checkToken(accesstoken);
		boolean result = accountService.logout(a.getId());
		if (result) {
			return Response.status(Status.STATUS_200)
					.entity("logout successfully").build();
		}
		return Response.status(Status.STATUS_2003).entity("logout failled")
				.build();
	}

}
