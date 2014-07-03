package com.fix.the.fridge.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Petrov
 */
@Entity
public class Idea {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private User user;
	private String title;
    @Lob
	private String description;
    @ManyToMany
	private List<Attachment> attachments = new ArrayList<>();
	@ManyToMany
	private List<User> voters = new ArrayList<>();
	@OneToMany
	private List<Comment> comments = new ArrayList<>();
    @Enumerated(EnumType.STRING)
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<User> getVoters() {
		return voters;
	}

	public void setVoters(List<User> votedUd) {
		this.voters = votedUd;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
