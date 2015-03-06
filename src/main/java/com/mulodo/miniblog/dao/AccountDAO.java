package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Account;

public interface AccountDAO {
	public void insert(Account acc);
	public Account login(String username,String password);
	public Account findByID(int id);
	public List<Account> search(String name);
	public void update(Account acc);
	public void delete(int id);
	public Account findByUsername(String username);
	public Account findByEmail(String email);
}
