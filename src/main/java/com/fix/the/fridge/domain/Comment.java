package com.fix.the.fridge.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Petrov
 */
@Entity
public class Comment {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private User author;
	@Lob
	private String text;
	@ManyToMany
	private List<User> voters = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<User> getVoters() {
		return voters;
	}

	public void setVoters(List<User> votedUd) {
		this.voters = votedUd;
	}
}
