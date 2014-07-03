package com.fix.the.fridge.dto;

/**
 * @author Artem Petrov
 */
public class CommentDto {
	private long id;
	private UserDto author;
	private String text;
	private Integer voters;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
}
