package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.PostsDAO;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.PostsService;

@Service
public class PostsServiceImpl implements PostsService {
	// variable PostsDAO
	@Autowired
	PostsDAO postsDAO;

	// method create Posts.input object Posts return true or false
	@Transactional
	public int create(Posts p) {
		try {
			if (p != null) {
				return postsDAO.create(p);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	// method update Posts.input object Posts return true or false
	@Transactional
	public boolean update(Posts p) {
		try {
			if (p != null) {
				postsDAO.update(p);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method delete Posts.input object Posts return true or false
	@Transactional
	public boolean delete(Posts p) {
		try {
			if (p != null) {
				postsDAO.delete(p);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method active Posts.input posts_id return true or false
	@Transactional
	public boolean active(int id) {
		try {
			Posts p = postsDAO.get(id);
			if (p != null) {
				p.setStatus(true);
				postsDAO.update(p);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method deactive Posts.input posts_id return true or false
	@Transactional
	public boolean deactive(int id) {
		try {
			Posts p = postsDAO.get(id);
			if (p != null) {
				p.setStatus(false);
				postsDAO.update(p);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method get Posts.input posts_id return object Posts
	@Transactional
	public Posts get(int id) {
		try {
			Posts posts = postsDAO.get(id);
			if (posts != null)
				return posts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method getAllPostsByUser.input account_id return list object Posts
	@Transactional
	public List<Posts> getAllPostsByUser(int id) {
		try {
			List<Posts> posts = postsDAO.getAllPostsByUser(id);
			if (posts != null)
				return posts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method getAllPostsActive.return list object Posts
	@Transactional
	public List<Posts> getAllPostsActive() {
		try {
			String query = "from Posts where status = 1";
			List<Posts> posts = postsDAO.getAll(query);
			if (posts != null)
				return posts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method getAllPostsDeactive.return list object Posts
	@Transactional
	public List<Posts> getAllPostsDeactive() {
		try {
			String query = "from Posts where status = 0";
			List<Posts> posts = postsDAO.getAll(query);
			if (posts != null)
				return posts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method getAllPostsByContent.input content return list object Posts
	@Transactional
	public List<Posts> getAllPostsByContent(String content) {
		try {
			String query = "from Posts where content like '%" + content + "%'";
			List<Posts> posts = postsDAO.getAll(query);
			if (posts != null)
				return posts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method getAllPostsTop
	@Transactional
	public List<Posts> getAllPostsTop() {
		try {
			String query = "from Posts where status = 1 order by create_at desc limit 10";
			List<Posts> posts = postsDAO.getAll(query);
			if (posts != null)
				return posts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
