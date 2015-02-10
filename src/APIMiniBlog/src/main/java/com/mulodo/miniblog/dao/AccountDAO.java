package com.mulodo.miniblog.dao;

import java.util.List;

import com.mulodo.miniblog.model.Account;

public interface AccountDAO {
	public boolean insert(Account acc);
	public Account login(String username,String password);
	public Account findByID(int id);
	public List<Account> getAll(String query);
	public boolean update(Account acc);
	public boolean delete(int id);
	public Account findByUsername(String username);
}
