package com.fix.the.fridge.dto;

import java.util.Date;

/**
 * @author Artem Petrov
 */
public class CommentDto {
	private Long id;
	private UserDto author;
	private String text;
	private Integer voters;
	private Date creationDate;
	private IdeaDto ideaDto;
	private boolean votedByCurrentUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(UserDto author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getVoters() {
		return voters;
	}

	public void setVoters(Integer voters) {
		this.voters = voters;
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

	public IdeaDto getIdeaDto() {
		return ideaDto;
	}

	public void setIdeaDto(IdeaDto ideaDto) {
		this.ideaDto = ideaDto;
	}
}
