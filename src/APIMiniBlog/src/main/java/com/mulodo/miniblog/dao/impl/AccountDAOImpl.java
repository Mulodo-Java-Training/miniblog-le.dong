package com.mulodo.miniblog.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mulodo.miniblog.dao.AccountDAO;
import com.mulodo.miniblog.model.Account;
import com.mulodo.miniblog.util.MD5Hash;

@Repository
public class AccountDAOImpl implements AccountDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public boolean insert(Account acc) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(acc);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Account login(String username, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username",username)).list().get(0);
		if(acc.getPassword().equals(password))
		{
			return acc;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Account findByID(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("id",id)).list().get(0);
		try {
			if(acc != null)
			return acc;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAll(String query) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts =  session.createQuery(query).list();
		try {
			if(accounts != null)
			return accounts;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public boolean update(Account acc) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(acc);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Account a = findByID(id);
			if(a != null)
			{
				session.delete(a);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account findByUsername(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Account acc = (Account) session.createCriteria(Account.class).add(Restrictions.eq("username",username)).list().get(0);
		try {
			if(acc != null)
			return acc;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
