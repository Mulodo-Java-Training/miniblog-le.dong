package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Token;

public interface TokenService {
	public boolean create(Token t);
	public boolean delete(int id_acc);
	public boolean deleteToken(String accesstoken);
	public List<Token> getAllTokenOfUser(int iduser);
	public Token getTokenByAccesstoken(String accesstoken);
}
