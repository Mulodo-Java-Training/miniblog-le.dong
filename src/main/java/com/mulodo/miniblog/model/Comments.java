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

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.ForeignKey;

import com.mulodo.miniblog.util.CustomJsonDateDeserializer;
import com.mulodo.miniblog.util.CustomJsonDateSeralizer;

@Entity
@Table(name = "comments")
public class Comments {
	@ManyToOne
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_comments_account")
	private Account account;

	@ManyToOne
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_comments_posts")
	private Posts posts;

	@Id
	@Column(columnDefinition = "INT(20) UNSIGNED", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(columnDefinition = "VARCHAR(254)", nullable = false)
	private String comment;

	@Column(columnDefinition = "TIMESTAMP(0)")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSeralizer.class)
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date create_at;

	@Column(columnDefinition = "TIMESTAMP(0)")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSeralizer.class)
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date modified_at;

	public Comments() {
	}

	public Comments(Account account, Posts posts, int id, String comment,
			Date create_at, Date modified_at) {
		super();
		this.account = account;
		this.posts = posts;
		this.id = id;
		this.comment = comment;
		this.create_at = create_at;
		this.modified_at = modified_at;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	@Override
	public String toString() {
		return "[ " + comment + "-" + create_at + "-" + modified_at + " ]";
	}

}
