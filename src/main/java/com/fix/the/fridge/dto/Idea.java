package com.fix.the.fridge.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Petrov
 */
public class Idea {
    private long id;
    private User user;
    private String title;
    private String description;
    private List<String> attachmentNames = new ArrayList<>();
    private Integer voters;
//    private List<Comment> comments = new ArrayList<>();
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

    public List<String> getAttachmentNames() {
        return attachmentNames;
    }

    public void setAttachmentNames(List<String> attachmentNames) {
        this.attachmentNames = attachmentNames;
    }

	public Integer getVoters() {
		return voters;
	}

	public void setVoters(Integer voters) {
		this.voters = voters;
	}

//	public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
