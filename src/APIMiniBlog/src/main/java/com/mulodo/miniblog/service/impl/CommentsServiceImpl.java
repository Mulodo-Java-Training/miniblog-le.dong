package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.CommentsDAO;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService{

	@Autowired
	CommentsDAO commentsDAO;
	
	@Transactional
	public boolean create(Comments c) {
		// TODO Auto-generated method stub
		try {
			commentsDAO.create(c);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Transactional
	public boolean update(Comments c) {
		// TODO Auto-generated method stub
		try {
			commentsDAO.update(c);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Transactional
	public boolean delete(Comments c) {
		// TODO Auto-generated method stub
		try {
			if(c!= null)
			{
				commentsDAO.delete(c);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Transactional
	public List<Comments> getCommentsOfUser(int iduser) {
		// TODO Auto-generated method stub
		try {
			List<Comments> comments = commentsDAO.getCommentsOfUser(iduser);
			if(comments != null)
				return comments;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Transactional
	public List<Comments> getCommentsOfPosts(int idpost) {
		// TODO Auto-generated method stub
		try {
			List<Comments> comments = commentsDAO.getCommentsOfPosts(idpost);
			if(comments != null)
				return comments;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Transactional
	public Comments get(int id) {
		// TODO Auto-generated method stub
		try {
			Comments c = commentsDAO.get(id);
			if(c != null)
				return c;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
