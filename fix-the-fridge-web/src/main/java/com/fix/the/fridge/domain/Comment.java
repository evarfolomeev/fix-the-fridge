package com.fix.the.fridge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Petrov
 */
@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private User author;
	@Lob
	private String text;
	@ManyToOne
	@JoinColumn(updatable = false)
	private Idea idea;
	@ManyToMany
	@JoinTable(
			name = "COMMENT_VOTERS",
			joinColumns = @JoinColumn(name = "COMMENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"),
			uniqueConstraints = @UniqueConstraint(columnNames = {"COMMENT_ID", "USER_ID"})
	)
	private Set<User> voters = new HashSet<>();
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATING_DATE", updatable = false)
	private Date creatingData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Set<User> getVoters() {
		return voters;
	}

	public void setVoters(Set<User> votedUd) {
		this.voters = votedUd;
	}

	public Date getCreatingData() {
		return creatingData;
	}

	public void setCreatingData(Date creatingData) {
		this.creatingData = creatingData;
	}

	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Comment)) {
			return false;
		}

		Comment comment = (Comment) o;

		if (id != null ? !id.equals(comment.id) : comment.id != null) {
			return false;
		}
		if (author != null ? !author.equals(comment.author) : comment.author != null) {
			return false;
		}
		if (creatingData != null ? !creatingData.equals(comment.creatingData) : comment.creatingData != null) {
			return false;
		}
		if (text != null ? !text.equals(comment.text) : comment.text != null) {
			return false;
		}
		if (voters != null ? !voters.equals(comment.voters) : comment.voters != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (voters != null ? voters.hashCode() : 0);
		result = 31 * result + (creatingData != null ? creatingData.hashCode() : 0);
		return result;
	}
}
