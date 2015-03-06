package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Posts;

public interface PostsDAO {
	public int create(Posts p);
	public boolean update(Posts p);
	public boolean delete(Posts p);
	public Posts get(int id);
	public List<Posts> getTopPosts();
	public List<Posts> getAllActive();
	public List<Posts> getAllDeactive();
	public List<Posts> search(String description);
	public List<Posts> getAllPostsByUser(int id_acc);
}
