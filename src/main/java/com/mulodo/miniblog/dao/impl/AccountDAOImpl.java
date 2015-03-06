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
	public void insert(Account acc) {
		sessionFactory.getCurrentSession().save(acc);
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
	public List<Account> search(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts = session.createQuery("from Account where username like '%" + name
				+ "%' or lastname like '%" + name + "%' or firstname like '%"
				+ name + "%' ").list();
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
	public void update(Account acc) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(acc);
	}
	//method delete Account,input id return true or false
	@Override
	public void delete(int id) {
		Session session = this.sessionFactory.getCurrentSession();
			Account a = findByID(id);
			if (a != null) {
				session.delete(a);
			}
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
	  //method findByEmail,input email return object Account
    @Override
    public Account findByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Account where email = '"
                + email + "'");
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
