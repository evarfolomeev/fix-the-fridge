package com.fix.the.fridge.dto;

import java.util.Date;

/**
 * @author Artem Petrov
 */
public class IdeaDto {
    private Long id;
    private UserDto user;
    private String title;
    private String description;
    private String attachmentName;
    private Integer voters;
    private Integer comments;
    private StatusDto status;
	private Date creationDate;
	private boolean votedByCurrentUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
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

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentNames) {
        this.attachmentName = attachmentNames;
    }

	public Integer getVoters() {
		return voters;
	}

	public void setVoters(Integer voters) {
		this.voters = voters;
	}

	public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isVotedByCurrentUser() {
		return votedByCurrentUser;
	}

	public void setVotedByCurrentUser(boolean votedByCurrentUser) {
		this.votedByCurrentUser = votedByCurrentUser;
	}
}
