package com.mulodo.miniblog.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import org.hibernate.validator.constraints.Email;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.util.MD5Hash;
import com.mulodo.miniblog.util.Status;

public class SignUpForm {
	@NotNull
    @Pattern(regexp = Status.USERNAME_STRING_RANGE)
    @FormParam("username")
    public String username;

    @NotNull
    @Pattern(regexp = Status.PASSWORD_STRING_RANGE)
    @FormParam("password")
    public String password;

    @NotNull
    @Email
    @FormParam("email")
    public String email;

    public Account setData()
    {
    	Account a = new Account();
        a.setUsername(this.username);
        a.setPassword(MD5Hash.MD5(this.password));
        a.setEmail(this.email);
        return a;
    }
}
