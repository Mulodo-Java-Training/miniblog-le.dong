package com.mulodo.miniblog.form;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.util.Status;

public class AddCommentsForm {
	@NotNull
    @Pattern(regexp = Status.COMMENT_STRING_RANGE)
    @FormParam("comment")
    public String comment;
	
	public Comments setData(Account acc,Posts p)
	{
		Comments c = new Comments();
		c.setComment(this.comment);
		c.setCreate_at(Calendar.getInstance().getTime());
		c.setModified_at(Calendar.getInstance().getTime());
		c.setAccount(acc);
		c.setPosts(p);
		return c;
	}
}
