package com.mulodo.miniblog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="token")
public class Token {
	@ManyToOne
	@JoinColumn(nullable = false)
	@ForeignKey(name="fk_token_account")
	private Account account;
	
	@Id
	@Column(columnDefinition = "INT(16) UNSIGNED")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(columnDefinition = "VARCHAR(100)",nullable=false,unique=true)
	private String access_token;
	
	@Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_at;
	
	@Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expired_at;

	public Token() {
	}

	public Token(Account account, int id, String access_token, Date create_at,
			Date expired_at) {
		super();
		this.account = account;
		this.id = id;
		this.access_token = access_token;
		this.create_at = create_at;
		this.expired_at = expired_at;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Date getExpired_at() {
		return expired_at;
	}

	public void setExpired_at(Date expired_at) {
		this.expired_at = expired_at;
	}
	
	@Override
	public String toString() {
		return this.access_token +"-" + this.account.getUsername();
	}
	
	
	
}
