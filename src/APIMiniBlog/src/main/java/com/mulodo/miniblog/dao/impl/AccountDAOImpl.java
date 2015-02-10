package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mulodo.miniblog.dao.AccountDAO;
import com.mulodo.miniblog.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

	//variable SessionFactory of Hibernate
	@Autowired
	SessionFactory sessionFactory;
	//method insert Account,input object Account return true or false
	@Override
	public boolean insert(Account acc) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(acc);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method login ,input username and password return object Account
	@Override
	public Account login(String username, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		Account acc = (Account) session.createCriteria(Account.class)
				.add(Restrictions.eq("username", username)).list().get(0);
		if (acc.getPassword().equals(password)) {
			return acc;
		}
		return null;
	}
	//method findByID,input id return object Account
	@Override
	public Account findByID(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Account acc = (Account) session.createCriteria(Account.class)
				.add(Restrictions.eq("id", id)).list().get(0);
		try {
			if (acc != null)
				return acc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	//method getAll Account,input query select all account return List object Account
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAll(String query) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts = session.createQuery(query).list();
		try {
			if (accounts != null)
				return accounts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	//method update info Account,input object Account return true or false
	@Override
	public boolean update(Account acc) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(acc);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method delete Account,input id return true or false
	@Override
	public boolean delete(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Account a = findByID(id);
			if (a != null) {
				session.delete(a);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	//method findByUsername,input username return object Account
	@Override
	public Account findByUsername(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account where username = '"
				+ username + "'");
		Account acc = (Account) query.uniqueResult();
		try {
			if (acc != null)
				return acc;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
