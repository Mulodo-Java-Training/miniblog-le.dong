package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.PostsDAO;
import com.mulodo.miniblog.model.Posts;
@Repository
public class PostsDAOImpl implements PostsDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public boolean create(Posts p) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(p);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@Override
	public boolean update(Posts p) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(p);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean delete(Posts p) {
		Session session = sessionFactory.getCurrentSession();
		try {
			if(p != null)
			{
				session.delete(p);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Posts> getAll(String query) {
		Session session = sessionFactory.getCurrentSession();
		try {
			List<Posts> posts = session.createQuery(query).list();
			if(posts != null)
				return posts;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Posts> getAllPostsByUser(int id_acc) {
		Session session = sessionFactory.getCurrentSession();
		try {
			List<Posts> posts = session.createQuery("from Posts where account_id = ?").setParameter(0, id_acc).list();
			if(posts != null)
				return posts;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Posts get(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		try {
			Posts posts = (Posts) session.createCriteria(Posts.class).add(Restrictions.eq("id", id)).list().get(0);
			if(posts != null)
				return posts;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
