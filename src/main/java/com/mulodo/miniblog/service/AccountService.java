package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;

public interface AccountService {
	public boolean register(Account acc);
	public Token login(String username,String password);
	public Token changePassword(int id,String old_password,String new_password);
	public Account getInfo(int id);
	public List<Account> searchByName(String name);
	public boolean update(Account acc);
	public boolean checkUser(String username);
	public boolean checkEmail(String email);
	public boolean logout(int account_id);
	public Account findByUsername(String username);
	public Account checkToken(String accesstoken);
	public boolean deleteUser(String username);
}
