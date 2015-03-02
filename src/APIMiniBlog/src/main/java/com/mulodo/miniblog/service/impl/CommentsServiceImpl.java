package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.CommentsDAO;
import com.mulodo.miniblog.model.Comments;
import com.mulodo.miniblog.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService {
	//variable CommentsDAO
	@Autowired
	CommentsDAO commentsDAO;
	//method create Comments.input object Comments return true or false
	@Transactional
	public int create(Comments c) {
		try {
			return commentsDAO.create(c);
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	//method update Comments.input object Comments return true or false
	@Transactional
	public boolean update(Comments c) {
		try {
			if(c!=null)
			{
			commentsDAO.update(c);
			return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method delete Comments.input object Comments return true or false
	@Transactional
	public boolean delete(Comments c) {
		try {
			if (c != null) {
				commentsDAO.delete(c);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method getCommentsOfUser.input account_id return list object Comments
	@Transactional
	public List<Comments> getCommentsOfUser(int id_acc) {
		try {
			List<Comments> comments = commentsDAO.getCommentsOfUser(id_acc);
			if (comments != null)
				return comments;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	//method getCommentsOfPosts.input posts_id return list object Comments
	@Transactional
	public List<Comments> getCommentsOfPosts(int id_posts) {

		try {
			List<Comments> comments = commentsDAO.getCommentsOfPosts(id_posts);
			if (comments != null)
				return comments;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	//method get Comments.input comments_id return object Comments
	@Transactional
	public Comments get(int id) {
		try {
			Comments c = commentsDAO.get(id);
			if (c != null)
				return c;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
