package com.mulodo.miniblog.form;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.util.Status;

public class CreatePostsForm {
    @NotNull
    @Pattern(regexp = Status.POST_TITLE_STRING_RANGE)
    @FormParam("title")
    public String title;

    @NotNull
    @Pattern(regexp = Status.POST_CONTENT_STRING_RANGE)
    @FormParam("content")
    public String content;
    
    public Posts setData(Account acc)
    {
    	Posts p = new Posts();
    	p.setTitle(this.title);
    	p.setContent(this.content);
    	p.setCreate_at(Calendar.getInstance().getTime());
    	p.setModified_at(Calendar.getInstance().getTime());
    	p.setStatus(true);
    	p.setAccount(acc);
    	System.out.println(p + "-----" + p.getContent());
    	return p;
    }
}
