package com.mulodo.miniblog.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
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

import returnform.AccessToken;

import com.mulodo.miniblog.form.ChangePasswordForm;
import com.mulodo.miniblog.form.SignInform;
import com.mulodo.miniblog.form.SignUpForm;
import com.mulodo.miniblog.form.UpdateAccountForm;
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

	// method login
	@POST
	@Path("login")
	@ValidateRequest
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Form @Valid SignInform form) {
		ReturnFormat json = new ReturnFormat();
		if (form.username != null && form.password != null) {
			Account a = accountService.login(form.username,
					MD5Hash.MD5(form.password));
			if (a != null) {
				Token t = new Token();
				t.setAccess_token(Util.randomString());
				t.setCreate_at(Calendar.getInstance().getTime());
				t.setExpired_at(accountService.sumationExpiredDate());
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
	public Response register(@Form @Valid SignUpForm data) {
		ReturnFormat json = new ReturnFormat();
		if (data.username != null && data.password != null
				&& data.email != null) {
			Account a = data.setData();
			boolean result = accountService.checkUser(data.username);
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
			@Form @Valid UpdateAccountForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.lastname != null && form.firstname != null
				&& form.password != null) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Account a = (Account) accountService.checkToken(accesstoken);
				if (a.getPassword().equals(MD5Hash.MD5(form.password))) {
					a.setLastname(form.lastname);
					a.setFirstname(form.firstname);
					a.setModified_at(Calendar.getInstance().getTime());
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
					return Response.status(Status.STATUS_2005).entity(json)
							.build();
				}
			}

		}
		json.meta.code(1001);
		return Response.status(Status.STATUS_1001).entity(json).build();
	}

	// method change password account
	@PUT
	@Path("changepass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePass(@HeaderParam("token") String accesstoken,
			@Form @Valid ChangePasswordForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.old_password != null && form.new_password != null) {
			int rs = accountService.checkExpiredDate(accesstoken);
			if (rs > 0) {
				accountService.deleteToken(accesstoken);
				json.meta.code(2011);
				return Response.status(Status.STATUS_2008).entity(json).build();
			} else {
				Account a = accountService.checkToken(accesstoken);
				if (a.getPassword().equals(MD5Hash.MD5(form.old_password))) {
					a.setPassword(MD5Hash.MD5(form.new_password));
					boolean result = accountService.changePassword(a.getId(), form.old_password, form.new_password);
					if (result) {
						accountService.logout(a.getId());
//						SendMail.send(a.getEmail(),
//								"[Miniblog]change password", "New Password:"
//										+ form.new_password);
						Token t = new Token();
						t.setAccess_token(Util.randomString());
						t.setCreate_at(Calendar.getInstance().getTime());
						t.setExpired_at(accountService.sumationExpiredDate());
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
					return Response.status(Status.STATUS_2005).entity(json)
							.build();
				}
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
		int rs = accountService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			accountService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		} else {
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
		int rs = accountService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			accountService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		} else {
			Account a = accountService.checkToken(accesstoken);
			boolean result = accountService.logout(a.getId());
			if (result) {
				json.meta.code(200);
				json.meta.description = "logout success!";
				return Response.status(Status.STATUS_200).entity(json).build();
			}
		}
		json.meta.code(2003);
		return Response.status(Status.STATUS_2003).entity(json).build();
	}

}
