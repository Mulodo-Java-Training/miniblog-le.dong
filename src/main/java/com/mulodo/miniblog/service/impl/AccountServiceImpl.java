package com.mulodo.miniblog.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.AccountDAO;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.AccountService;
import com.mulodo.miniblog.service.TokenService;
import com.mulodo.miniblog.util.MD5Hash;
import com.mulodo.miniblog.util.Util;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	TokenService tokenService;

	@Transactional
	public boolean register(Account acc) {
		try {
			accountDAO.insert(acc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Transactional
	public Token login(String username, String password) {
		try {
			Account a = accountDAO.login(username, password);
			if (a != null)
			{
			    Token token = new Token();
		        token.setAccess_token(Util.randomString());
		        token.setCreate_at(Calendar.getInstance().getTime());
		        token.setExpired_at(tokenService.sumationExpiredDate());
		        token.setAccount(a);
		        tokenService.create(token);
				return token;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

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

	@Transactional
	public List<Account> searchByName(String name) {
		try {
			List<Account> accounts = accountDAO.search(name);
			if (accounts != null)
				return accounts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

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

	@Transactional
	public Token changePassword(int id, String old_password,
			String new_password) {
		try {
			Account acc = accountDAO.findByID(id);
			if (acc != null) {
				acc.setPassword(MD5Hash.MD5(new_password));
				accountDAO.update(acc);
				Token token = new Token();
                token.setAccess_token(Util.randomString());
                token.setCreate_at(Calendar.getInstance().getTime());
                token.setExpired_at(tokenService.sumationExpiredDate());
                token.setAccount(acc);
                tokenService.create(token);
				return token;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

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
	
    @Transactional
    public boolean checkEmail(String email) {
        try {
            Account a = accountDAO.findByEmail(email);
            if (a == null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

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

	@Transactional
	public boolean deleteUser(String username) {
		try {
			Account a = accountDAO.findByUsername(username);
			if (a != null)
			{
				accountDAO.delete(a.getId());
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}
