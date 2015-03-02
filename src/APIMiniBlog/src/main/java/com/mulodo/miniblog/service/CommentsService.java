package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Comments;

public interface CommentsService {
	public int create(Comments c);
	public boolean update(Comments c);
	public boolean delete(Comments c);
	public Comments get(int id);
	public List<Comments> getCommentsOfUser(int id_acc);
	public List<Comments> getCommentsOfPosts(int id_posts);
}
