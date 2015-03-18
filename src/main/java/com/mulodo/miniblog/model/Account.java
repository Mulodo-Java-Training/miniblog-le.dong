package com.mulodo.miniblog.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mulodo.miniblog.util.CustomJsonDateDeserializer;
import com.mulodo.miniblog.util.CustomJsonDateSeralizer;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@Column(columnDefinition = "INT(16) UNSIGNED")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(columnDefinition = "VARCHAR(32)", nullable = false, unique = true)
	private String username;

	@Column(columnDefinition = "VARCHAR(72)", nullable = false)
	private String password;

	@Column(columnDefinition = "VARCHAR(32)", nullable = false)
	private String lastname;

	@Column(columnDefinition = "VARCHAR(32)", nullable = false)
	private String firstname;

	@Column(columnDefinition = "VARCHAR(254)", nullable = false, unique = true)
	private String email;

	@Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSeralizer.class)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date create_at;

	@Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSeralizer.class)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date modified_at;

	@OneToMany(targetEntity = Posts.class, mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Posts> account_posts;

	@OneToMany(targetEntity = Comments.class, mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comments> account_comments;

	@OneToMany(targetEntity = Token.class, mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Token> account_token;

	public Account() {
	}

	public Account(int id, String username, String password, String lastname,
			String firstname, String email, Date create_at, Date modified_at,
			List<Posts> account_posts, List<Comments> account_comments,
			List<Token> account_token) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.create_at = create_at;
		this.modified_at = modified_at;
		this.account_posts = account_posts;
		this.account_comments = account_comments;
		this.account_token = account_token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Date getModified_at() {
		return modified_at;
	}

	public void setModified_at(Date modified_at) {
		this.modified_at = modified_at;
	}
	
	public List<Posts> getAccount_posts() {
		return account_posts;
	}

	public void setAccount_posts(List<Posts> account_posts) {
		this.account_posts = account_posts;
	}

	public List<Comments> getAccount_comments() {
		return account_comments;
	}

	public void setAccount_comments(List<Comments> account_comments) {
		this.account_comments = account_comments;
	}

	public List<Token> getAccount_token() {
		return account_token;
	}

	public void setAccount_token(List<Token> account_token) {
		this.account_token = account_token;
	}

	@Override
	public String toString() {
		return "[" + username + "-" + email + "-" + create_at + " - "
				+ modified_at + " - " + lastname + firstname + "]";
	}

}
