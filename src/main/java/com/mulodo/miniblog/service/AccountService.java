package com.mulodo.miniblog.service;

import java.util.Date;
import java.util.List;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;

public interface AccountService {
	public boolean register(Account acc);
	public Account login(String username,String password);
	public boolean changePassword(int id,String old_password,String new_password);
	public Account getInfo(int id);
	public List<Account> searchByName(String name);
	public boolean update(Account acc);
	public boolean createToken(Token t);
	public boolean checkUser(String username);
	public boolean logout(int account_id);
	public Account findByUsername(String username);
	public Account checkToken(String accesstoken);
	public int checkExpiredDate(String accesstoken);
	public Date sumationExpiredDate();
	public boolean deleteToken(String accesstoken);
	public boolean deleteUser(String username);
}
