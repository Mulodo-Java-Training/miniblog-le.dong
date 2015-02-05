package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Token;

public interface TokenDAO {
	public boolean create(Token t);
	public boolean delete(Token t);
	public List<Token> getAllTokenOfUser(int id_acc);
	public Token getToken(int id);
	public Token getTokenByAccesstoken(String accesstoken);
}
