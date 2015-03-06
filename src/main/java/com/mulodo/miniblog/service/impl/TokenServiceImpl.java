package com.mulodo.miniblog.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.TokenDAO;
import com.mulodo.miniblog.model.Token;
import com.mulodo.miniblog.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenDAO tokenDAO;

	@Transactional
	public boolean create(Token t) {
		try {
			tokenDAO.create(t);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@Transactional
	public boolean delete(int id_acc) {
		try {
			List<Token> tokens = tokenDAO.getAllTokenOfUser(id_acc);
			if (tokens != null) {
				for (Token t : tokens) {
					tokenDAO.delete(t);
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@Transactional
	public List<Token> getAllTokenOfUser(int id_acc) {
		try {
			List<Token> tokens = tokenDAO.getAllTokenOfUser(id_acc);
			if (tokens != null)
				return tokens;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Transactional
	public Token getTokenByAccesstoken(String accesstoken) {
		try {
			Token token = tokenDAO.getTokenByAccesstoken(accesstoken);
			if (token != null)
				return token;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	@Transactional
	public boolean deleteToken(String accesstoken) {
		try {
			Token t = tokenDAO.getTokenByAccesstoken(accesstoken);
			if (t != null) {
				tokenDAO.delete(t);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
    @Transactional
    public int checkExpiredDate(String accesstoken) {
        Token t = tokenDAO.getTokenByAccesstoken(accesstoken);
        Calendar cal = Calendar.getInstance();
        return cal.getTime().compareTo(t.getExpired_at());
    }

    @Transactional
    public Date sumationExpiredDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

}
