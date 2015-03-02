package com.mulodo.miniblog.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.util.Status;

public class UpdateAccountForm {
	@NotNull
    @Pattern(regexp = Status.LASTNAME_STRING_RANGE)
    @FormParam("lastname")
    public String lastname;

	@NotNull
    @Pattern(regexp = Status.FIRSTNAME_STRING_RANGE)
    @FormParam("firstname")
    public String firstname;
	
    @NotNull
    @Pattern(regexp = Status.PASSWORD_STRING_RANGE)
    @FormParam("password")
    public String password;
}
