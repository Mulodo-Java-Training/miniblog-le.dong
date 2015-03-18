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
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.util.MD5Hash;
import com.mulodo.miniblog.util.ReturnFormat;
import com.mulodo.miniblog.util.Status;

@Controller
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
    private TokenService tokenService;

	@POST
	@Path("login")
	@ValidateRequest
	public Response login(@Form @Valid SignInform form) {
		ReturnFormat json = new ReturnFormat();
		if (form.username == null || form.password == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		Token token = accountService.login(form.username,
				MD5Hash.MD5(form.password));
		if (token == null) {
			json.meta.code(2002);
			return Response.status(Status.STATUS_2002).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "login success";
		json.data = new AccessToken(token.getAccess_token(),token.getAccount().getId(),token.getAccount().getUsername());
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@POST
	@Path("register")
	@ValidateRequest
	public Response register(@Form @Valid SignUpForm data) {
		ReturnFormat json = new ReturnFormat();
		if (data.username == null || data.password == null
				|| data.email == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		Account a = data.setData();
		if (accountService.checkUser(data.username) == false) {
			json.meta.code(2001);
			return Response.status(Status.STATUS_2001).entity(json).build();
		}
        if (accountService.checkEmail(data.email) == false) {
            json.meta.code(2011);
            return Response.status(Status.STATUS_2011).entity(json).build();
        }
		if (accountService.register(a) == false) {
			json.meta.code(2009);
			return Response.status(Status.STATUS_2009).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "register success";
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@GET
	@Path("getuser/{id}")
	public Response getInfo(@HeaderParam("token") String accesstoken,@PathParam("id") int id) {
		ReturnFormat json = new ReturnFormat();
		int rs = tokenService.checkExpiredDate(accesstoken);
        if (rs > 0) {
            tokenService.deleteToken(accesstoken);
            json.meta.code(2011);
            return Response.status(Status.STATUS_2008).entity(json).build();
        }
		if (id <= 0) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		Account a = this.accountService.getInfo(id);
		if (a == null) {
			json.meta.code(2006);
			return Response.status(Status.STATUS_2006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "get user success!";
		json.data = a;
		return Response.status(Status.STATUS_200).entity(json).build();
	}
	
	@GET
	@Path("profile")
	public Response profile(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Account a = (Account) accountService.checkToken(accesstoken);
		if (a == null) {
			json.meta.code(2006);
			return Response.status(Status.STATUS_2006).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "profile!";
		json.data = a;
		return Response.status(Status.STATUS_200).entity(json).build();
	}

	@PUT
	@Path("updateuser")
	@ValidateRequest
	public Response update(@HeaderParam("token") String accesstoken,
			@Form @Valid UpdateAccountForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.lastname == null || form.firstname == null
				|| form.password == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Account a = (Account) accountService.checkToken(accesstoken);
		if (!a.getPassword().equals(MD5Hash.MD5(form.password))) {
			json.meta.code(2005);
			return Response.status(Status.STATUS_2005).entity(json).build();
		}
		a.setLastname(form.lastname);
		a.setFirstname(form.firstname);
		a.setModified_at(Calendar.getInstance().getTime());
		boolean result = accountService.update(a);
		if (result == false) {
			json.meta.code(2004);
			return Response.status(Status.STATUS_2004).entity(json).build();

		}
		json.meta.code(200);
		json.meta.description = "update success!";
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@PUT
	@Path("changepass")
	@ValidateRequest
	public Response changePass(@HeaderParam("token") String accesstoken,
			@Form @Valid ChangePasswordForm form) {
		ReturnFormat json = new ReturnFormat();
		if (form.old_password == null || form.new_password == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}

		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		if (!a.getPassword().equals(MD5Hash.MD5(form.old_password))) {
			json.meta.code(2005);
			return Response.status(Status.STATUS_2005).entity(json).build();
		}
		Token token = accountService.changePassword(a.getId(),
				form.old_password, form.new_password);
		if (token == null) {
			json.meta.code(2010);
			return Response.status(Status.STATUS_2010).entity(json).build();

		}
		accountService.logout(a.getId());
		json.meta.code(200);
		json.meta.description = "change password success!";
		json.data = null;
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@GET
	@Path("searchuser/{name}")
	public Response search(@HeaderParam("token") String accesstoken,
			@PathParam("name") String name) {
		ReturnFormat json = new ReturnFormat();
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		if (name == null) {
			json.meta.code(1001);
			return Response.status(Status.STATUS_1001).entity(json).build();
		}

		List<Account> accounts = accountService.searchByName(name);
		if (accounts == null) {
			json.meta.code(2007);
			return Response.status(Status.STATUS_2007).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "result";
		json.data = accounts;
		return Response.status(Status.STATUS_200).entity(json).build();

	}

	@POST
	@Path("logout")
	public Response logout(@HeaderParam("token") String accesstoken) {
		ReturnFormat json = new ReturnFormat();
		int rs = tokenService.checkExpiredDate(accesstoken);
		if (rs > 0) {
			tokenService.deleteToken(accesstoken);
			json.meta.code(2011);
			return Response.status(Status.STATUS_2008).entity(json).build();
		}
		Account a = accountService.checkToken(accesstoken);
		boolean result = accountService.logout(a.getId());
		if (result == false) {
			json.meta.code(2003);
			return Response.status(Status.STATUS_2003).entity(json).build();
		}
		json.meta.code(200);
		json.meta.description = "logout success!";
		return Response.status(Status.STATUS_200).entity(json).build();
		
	}

}
