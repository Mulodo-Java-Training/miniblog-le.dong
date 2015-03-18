package com.mulodo.miniblog.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.util.Status;

public class ChangePasswordForm {
    @NotNull
    @Pattern(regexp = Status.PASSWORD_STRING_RANGE)
    @FormParam("old_password")
    public String old_password;
	
    @NotNull
    @Pattern(regexp = Status.PASSWORD_STRING_RANGE)
    @FormParam("new_password")
    public String new_password;
}
