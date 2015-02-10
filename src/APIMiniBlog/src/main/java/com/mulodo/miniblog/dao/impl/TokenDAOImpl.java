package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.TokenDAO;
import com.mulodo.miniblog.model.Token;

@Repository
public class TokenDAOImpl implements TokenDAO {
	//variable SessionFactory of Hibernate
	@Autowired
	SessionFactory sessionFactory;
	//method create Token,input object Token return true or false
	@Override
	public boolean create(Token t) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(t);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method delete Token,input object Token return true or false
	@Override
	public boolean delete(Token t) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (t != null) {
				session.delete(t);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method getTokenByAccesstoken,input accesstoken return object Token
	@Override
	public Token getTokenByAccesstoken(String accesstoken) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Token t = (Token) session.createCriteria(Token.class)
					.add(Restrictions.eq("access_token", accesstoken)).list()
					.get(0);
			if (t != null)
				return t;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	//method getToken,input token_id return object Token
	@Override
	public Token getToken(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Token t = (Token) session.createCriteria(Token.class)
					.add(Restrictions.eq("id", id)).list().get(0);
			if (t != null)
				return t;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	//method getAllTokenOfUser,input account_id return list object Token
	@SuppressWarnings("unchecked")
	@Override
	public List<Token> getAllTokenOfUser(int id_acc) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			List<Token> tokens = session.createQuery(
					"from Token where account_id= " + id_acc + "").list();
			if (tokens != null) {
				return tokens;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
