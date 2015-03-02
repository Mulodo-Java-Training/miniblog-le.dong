package com.mulodo.miniblog.form;

import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.util.Status;

public class EditCommentsForm {
	@FormParam("id")
    public int id;

    @FormParam("postid")
    public int postid;

    @Pattern(regexp = Status.COMMENT_STRING_RANGE)
    @FormParam("comment")
    public String comment;
}
