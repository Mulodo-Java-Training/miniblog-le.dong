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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "posts")
public class Posts {
	@ManyToOne
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_posts_account")
	private Account account;

	@OneToMany(targetEntity = Comments.class, mappedBy = "posts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comments> posts_comments;

	@Id
	@Column(columnDefinition = "INT(20) UNSIGNED")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(columnDefinition = "VARCHAR(100)", nullable = false)
	private String title;

	@Column(columnDefinition = "VARCHAR(2048)", nullable = false)
	private String content;

	@Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_at;

	@Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified_at;

	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean status = true;

	public Posts() {
		super();
	}

	public Posts(Account account, List<Comments> posts_comments, int id,
			String title, String content, Date create_at, Date modified_at,
			boolean status) {
		super();
		this.account = account;
		this.posts_comments = posts_comments;
		this.id = id;
		this.title = title;
		this.content = content;
		this.create_at = create_at;
		this.modified_at = modified_at;
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Comments> getPosts_comments() {
		return posts_comments;
	}

	public void setPosts_comments(List<Comments> posts_comments) {
		this.posts_comments = posts_comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "[ " + title + "-" + content + "-" + create_at + "-"
				+ modified_at + " ]";
	}
}
