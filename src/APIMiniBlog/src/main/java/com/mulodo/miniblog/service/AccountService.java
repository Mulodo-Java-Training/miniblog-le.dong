package com.mulodo.miniblog.service;

import java.util.List;

import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;

public interface AccountService {
	public boolean register(Account acc);
	public Account login(String username,String password);
	public boolean changePassword(int id,String oldpass,String newpass);
	public Account getInfo(int id);
	public List<Account> searchByName(String name);
	public boolean update(Account acc);
	public String checkToken(int id_acc);
	public boolean createToken(Token t);
	public boolean checkUser(String username);
	public boolean logout(int account_id);
	public Account findByUsername(String username);
	public Account getAccountByToken(String accesstoken);
}
