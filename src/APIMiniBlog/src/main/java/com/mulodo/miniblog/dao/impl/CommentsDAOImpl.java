package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.CommentsDAO;
import com.mulodo.miniblog.model.Comments;
@Repository
public class CommentsDAOImpl implements CommentsDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public boolean create(Comments c) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(c);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean update(Comments c) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(c);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> getCommentsOfUser(int id_acc) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			List<Comments> comments = session.createQuery("from Comments where account_id = ?").setParameter(0, id_acc).list();
			if(comments != null)
				return comments;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> getCommentsOfPosts(int id_posts) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			List<Comments> comments = session.createQuery("from Comments where posts_id = ?").setParameter(0, id_posts).list();
			if(comments != null)
				return comments;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public boolean delete(Comments c) {
		Session session = sessionFactory.getCurrentSession();
		try {
			if(c != null)
			{
				session.delete(c);
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comments get(int id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Comments comment = (Comments) session.createCriteria(Comments.class).add(Restrictions.eq("id", id)).list().get(0);
			if(comment != null)
			{
				
				return comment;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
