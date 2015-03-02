package com.mulodo.miniblog.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.AccountDAO;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.TokenService;

@Service
public class AccountServiceImpl implements AccountService {
	// variable AccountDAO
	@Autowired
	AccountDAO accountDAO;
	// variable TokenService
	@Autowired
	TokenService tokenService;

	// method register.input object Account return true or false
	@Transactional
	public boolean register(Account acc) {
		try {
			accountDAO.insert(acc);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method login.input username and password return object Account
	@Transactional
	public Account login(String username, String password) {
		try {
			Account a = accountDAO.login(username, password);
			if (a != null)
				return a;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method getInfo.input account_id return object Account
	@Transactional
	public Account getInfo(int id) {
		try {
			Account a = accountDAO.findByID(id);
			if (a != null)
				return a;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method searchByName.input keyword name return list object Account
	@Transactional
	public List<Account> searchByName(String name) {
		String query = "from Account where username like '%" + name
				+ "%' or lastname like '%" + name + "%' or firstname like '%"
				+ name + "%' ";
		try {
			List<Account> accounts = accountDAO.getAll(query);
			if (accounts != null)
				return accounts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method update info Account.input object Account return true or false
	@Transactional
	public boolean update(Account acc) {
		try {
			accountDAO.update(acc);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method changePassword.input id_account, oldpassword, newpassword return
	// true or false
	@Transactional
	public boolean changePassword(int id, String old_password,
			String new_password) {
		try {
			Account acc = accountDAO.findByID(id);
			if (acc != null) {
				accountDAO.update(acc);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method checkUser.input username return true or false
	@Transactional
	public boolean checkUser(String username) {
		try {
			Account a = accountDAO.findByUsername(username);
			if (a == null) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method findByUsername.input username return object Account
	@Transactional
	public Account findByUsername(String username) {
		try {
			Account a = accountDAO.findByUsername(username);
			if (a != null)
				return a;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method logout.input account_id return true or false
	@Transactional
	public boolean logout(int account_id) {
		boolean result = false;
		try {
			result = tokenService.delete(account_id);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// method createToken.input object Token return true or false
	@Transactional
	public boolean createToken(Token t) {
		try {
			tokenService.create(t);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// method checkToken.input accesstoken return object Account
	@Transactional
	public Account checkToken(String accesstoken) {
		try {
			Token t = tokenService.getTokenByAccesstoken(accesstoken);
			if (t != null) {
				return t.getAccount();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// method checkExpiredDate token
	@Transactional
	public int checkExpiredDate(String accesstoken) {
		Token t = tokenService.getTokenByAccesstoken(accesstoken);
		Calendar cal = Calendar.getInstance();
		return cal.getTime().compareTo(t.getExpired_at());
	}

	// method sumationExpiredDate token
	@Transactional
	public Date sumationExpiredDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	// method delete Token
	@Transactional
	public boolean deleteToken(String accesstoken) {
		boolean result = false;
		try {
			result = tokenService.deleteToken(accesstoken);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	@Transactional
	public boolean deleteUser(String username) {
		boolean result = false;
		try {
			Account a = accountDAO.findByUsername(username);
			if (a != null)
			{
				result = accountDAO.delete(a.getId());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

}
