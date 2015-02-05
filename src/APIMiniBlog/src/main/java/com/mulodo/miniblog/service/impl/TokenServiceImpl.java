package com.mulodo.miniblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mulodo.miniblog.dao.TokenDAO;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService{

	@Autowired
	TokenDAO tokenDAO;
	
	@Override
	public boolean create(Token t) {
		try {
			tokenDAO.create(t);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@Override
	public boolean delete(int id_acc) {
		try {
			List<Token> tokens = tokenDAO.getAllTokenOfUser(id_acc);
			if(tokens!=null)
			{
				for(Token t:tokens)
				{
					tokenDAO.delete(t);
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@Override
	public List<Token> getAllTokenOfUser(int id_acc) {
		try {
			List<Token> tokens = tokenDAO.getAllTokenOfUser(id_acc);
			if(tokens != null)
				return tokens;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public Token getTokenByAccesstoke(String accesstoken) {
		try {
			Token token = tokenDAO.getTokenByAccesstoken(accesstoken);
			if(token != null)
				return token;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
