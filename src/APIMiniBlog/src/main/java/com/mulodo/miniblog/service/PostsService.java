package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Posts;

public interface PostsService {
	public boolean create(Posts p);
	public boolean update(Posts p);
	public boolean delete(Posts p);
	public boolean active(int id);
	public boolean deactive(int id);
	public Posts get(int id);
	public List<Posts> getAllPostsActive();
	public List<Posts> getAllPostsDeactive();
	public List<Posts> getAllPostsByUser(int id);
	public List<Posts> getAllPostsByContent(String content);
	public List<Posts> getAllPostsTop();
}
