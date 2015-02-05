package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.PostsDAO;
import com.mulodo.miniblog.model.Posts;
import com.mulodo.miniblog.service.PostsService;
@Service
public class PostsServiceImpl implements PostsService{

	@Autowired
	PostsDAO postsDAO;
	
	@Transactional
	public boolean create(Posts p) {
		// TODO Auto-generated method stub
		try {
			postsDAO.create(p);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return false;
	}

	@Transactional
	public boolean update(Posts p) {
		// TODO Auto-generated method stub
		try {
			postsDAO.update(p);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Transactional
	public boolean delete(Posts p) {
		// TODO Auto-generated method stub
		try {
				postsDAO.delete(p);
				return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}

	@Transactional
	public boolean active(int id) {
		// TODO Auto-generated method stub
		try {
			Posts p = postsDAO.get(id);
			if(p!= null)
			{
				p.setStatus(true);
				postsDAO.update(p);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Transactional
	public boolean deactive(int id) {
		// TODO Auto-generated method stub
		try {
			Posts p = postsDAO.get(id);
			if(p!= null)
			{
				p.setStatus(false);
				postsDAO.update(p);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}


	@Transactional
	public List<Posts> getAllPostsByUser(int id) {
		// TODO Auto-generated method stub
		try {
			List<Posts> posts = postsDAO.getAllPostsByUser(id);
			if(posts != null)
				return posts;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Transactional
	public Posts get(int id) {
		// TODO Auto-generated method stub
		try {
			Posts posts = postsDAO.get(id);
			if(posts != null)
				return posts;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Transactional
	public List<Posts> getAllPostsActive() {
		// TODO Auto-generated method stub
				try {
					String query = "from Posts where status = 1";
					List<Posts> posts = postsDAO.getAll(query);
					if(posts != null)
						return posts;
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;
	}

	@Transactional
	public List<Posts> getAllPostsDeactive() {
		// TODO Auto-generated method stub
				try {
					String query = "from Posts where status = 0";
					List<Posts> posts = postsDAO.getAll(query);
					if(posts != null)
						return posts;
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;
	}

}
