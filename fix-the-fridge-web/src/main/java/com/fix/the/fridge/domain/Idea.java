package com.fix.the.fridge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Artem Petrov
 */
@Entity
public class Idea {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private User user;
	private String title;
    @Lob
	private String description;
    @OneToOne
	private Attachment attachment;
	@ManyToMany
	@JoinTable(
			name = "IDEA_VOTERS",
			joinColumns = @JoinColumn(name = "IDEA_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"),
			uniqueConstraints =
			@UniqueConstraint(columnNames = {"IDEA_ID", "USER_ID"})
	)
	private Set<User> voters = new HashSet<>();
	@OneToMany(mappedBy = "idea")
	private List<Comment> comments = new ArrayList<>();
    @Enumerated(EnumType.STRING)
	private Status status;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATING_DATE", updatable = false)
	private Date creatingData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Set<User> getVoters() {
		return voters;
	}

	public void setVoters(Set<User> votedUd) {
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

	public Date getCreatingData() {
		return creatingData;
	}

	public void setCreatingData(Date creatingData) {
		this.creatingData = creatingData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Idea)) {
			return false;
		}

		Idea idea = (Idea) o;

		if (id != null ? !id.equals(idea.id) : idea.id != null) {
			return false;
		}
		if (attachment != null ? !attachment.equals(idea.attachment) : idea.attachment != null) {
			return false;
		}
		if (comments != null ? !comments.equals(idea.comments) : idea.comments != null) {
			return false;
		}
		if (creatingData != null ? !creatingData.equals(idea.creatingData) : idea.creatingData != null) {
			return false;
		}
		if (description != null ? !description.equals(idea.description) : idea.description != null) {
			return false;
		}
		if (status != idea.status) {
			return false;
		}
		if (title != null ? !title.equals(idea.title) : idea.title != null) {
			return false;
		}
		if (user != null ? !user.equals(idea.user) : idea.user != null) {
			return false;
		}
		if (voters != null ? !voters.equals(idea.voters) : idea.voters != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (attachment != null ? attachment.hashCode() : 0);
		result = 31 * result + (voters != null ? voters.hashCode() : 0);
		result = 31 * result + (comments != null ? comments.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (creatingData != null ? creatingData.hashCode() : 0);
		return result;
	}
}
