package com.mulodo.miniblog.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.util.Status;

public class SignInform {
    @NotNull
    @Pattern(regexp = Status.USERNAME_STRING_RANGE)
    @FormParam("username")
    public String username;

    @NotNull
    @Pattern(regexp = Status.PASSWORD_STRING_RANGE)
    @FormParam("password")
    public String password;
}
