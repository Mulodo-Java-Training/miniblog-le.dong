package com.mulodo.miniblog.form;

import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.util.Status;

public class UpdatePostsForm {
	@FormParam("id")
    public int id;

    @Pattern(regexp = Status.POST_TITLE_STRING_RANGE)
    @FormParam("title")
    public String title;

    @Pattern(regexp = Status.POST_CONTENT_STRING_RANGE)
    @FormParam("content")
    public String content;

    @FormParam("status")
    public boolean status;
}
