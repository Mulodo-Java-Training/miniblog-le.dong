package com.mulodo.miniblog.form;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

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
    @Pattern(regexp = Status.EMAIL_STRING_RANGE)
    @FormParam("email")
    public String email;

    public Account setData()
    {
    	Account account = new Account();
        account.setUsername(this.username);
        account.setPassword(MD5Hash.MD5(this.password));
        account.setEmail(this.email);
        account.setLastname("");
        account.setFirstname("");
        account.setCreate_at(Calendar.getInstance().getTime());
        account.setModified_at(Calendar.getInstance().getTime());
        return account;
    }
}
