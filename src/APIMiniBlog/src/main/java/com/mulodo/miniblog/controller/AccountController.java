package com.mulodo.miniblog.controller;

import java.util.Date;
import java.util.List;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.util.MD5Hash;
import com.mulodo.miniblog.util.Status;
import com.mulodo.miniblog.util.Util;

@Controller
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
	@Autowired
	private AccountService accountService;

	@POST
	@Path("/login")
	@ValidateRequest
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("u") @NotNull(message = "{username is not null}") String username,
			@FormParam("p") @NotNull(message = "{password is not null}") @Size(min = 6, message = "size min is >=6 characters") String password) {
		if (username != null && password != null) {
			String pass = MD5Hash.MD5(password);
			Account a = accountService.login(username, pass);
			if (a != null) {
				Token t = new Token();
				t.setAccess_token(Util.randomString());
				t.setCreate_at(new Date());
				t.setExpired_at(new Date());
				t.setAccount(a);
				accountService.createToken(t);
				return Response.status(Status.status_200)
						.entity("Access Token:" + t.getAccess_token() + " ")
						.build();
			} else {
				return Response.status(Status.status_2002)
						.entity("username or password wrong").build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("username and password required!").build();
	}

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
					return Response.status(Status.status_200)
							.entity("Details Accounts:" + a).build();
				else
					return Response.status(Status.status_2009)
							.entity("register failled").build();
			} else {
				return Response.status(Status.status_2001)
						.entity("username is existed!").build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("username, password,email required!").build();
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfo(@PathParam("id") int id) {
		if (id != 0) {
			Account a = this.accountService.getInfo(id);
			if (a != null) {
				return Response.status(Status.status_200).entity("Info:" + a)
						.build();
			} else {
				return Response.status(Status.status_2006)
						.entity("id not existed").build();
			}
		}
		return Response.status(Status.status_1001).entity("id required!")
				.build();
	}

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
			Account a = (Account) accountService.getAccountByToken(accesstoken);
			a.setUsername(username);
			a.setEmail(email);
			a.setLastname(lastname);
			a.setFirstname(firstname);
			a.setModified_at(new Date());
			if (a.getPassword().equals(MD5Hash.MD5(password))) {
				boolean result = accountService.update(a);
				if (result) {
					return Response.status(Status.status_200)
							.entity("Info Update:" + a).build();
				} else {
					return Response.status(Status.status_2004)
							.entity("Update failed").build();
				}
			} else {
				return Response.status(Status.status_2005)
						.entity("password not match!you cann't update info")
						.build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("username,password,email,lastname,firstname required!")
				.build();
	}

	@PUT
	@Path("/changepass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response chanepass(@HeaderParam("token") String accesstoken,
			@FormParam("oldpass") String oldpass,
			@FormParam("newpass") String newpass) {
		if (accesstoken != null && oldpass != null && newpass != null) {
			Account a = accountService.getAccountByToken(accesstoken);
			if (a.getPassword().equals(MD5Hash.MD5(oldpass))) {
				a.setPassword(MD5Hash.MD5(newpass));
				boolean result = accountService.update(a);
				if (result) {
					return Response
							.status(Status.status_200)
							.entity("Change password success. New Pass: "
									+ newpass).build();
				} else {
					return Response.status(Status.status_2010)
							.entity("change password failed").build();
				}
			} else {
				return Response.status(Status.status_2005)
						.entity("old password not match!you cann't change password...").build();
			}
		}
		return Response.status(Status.status_1001)
				.entity("accesstoken,oldpass,newpass required!").build();
	}

	@GET
	@Path("/search/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("name") String name) {
		if (name != null) {
			List<Account> accounts = accountService.searchByName(name);
			if (accounts != null) {
				return Response.status(Status.status_200)
						.entity("Data: " + accounts).build();
			} else {
				return Response
						.status(Status.status_2007)
						.entity("username,lastname,firstname not existed with keyword "
								+ name + "!").build();
			}
		}
		return Response.status(Status.status_1001).entity("name is required!")
				.build();
	}

	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		return Response.status(Status.status_200).entity("logout successfully")
				.build();
	}

}
