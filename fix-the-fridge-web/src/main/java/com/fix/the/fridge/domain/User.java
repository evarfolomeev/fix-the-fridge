package com.fix.the.fridge.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Artem Petrov
 */
@Entity
public class User {
	@Id
	private String nickName;
	private String email;
	private String name;
	@Column(updatable = false)
	private String password;
	@Column(updatable = false)
	private boolean enabled;
    @OneToOne
	private Attachment avatar;

	//<woodoo-style>
	@ElementCollection(targetClass = UserRole.class)
	@CollectionTable(name = "USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_ID"))
	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private Set<UserRole> roles;
	//</woodoo-style>

	public User() {
		this.roles = new HashSet<>();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Attachment getAvatar() {
        return avatar;
    }

    public void setAvatar(Attachment avatar) {
        this.avatar = avatar;
    }

	public Set<UserRole> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	public void addRole(UserRole role) {
		roles.add(role);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		User user = (User) o;

		if (enabled != user.enabled) {
			return false;
		}
		if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) {
			return false;
		}
		if (email != null ? !email.equals(user.email) : user.email != null) {
			return false;
		}
		if (name != null ? !name.equals(user.name) : user.name != null) {
			return false;
		}
		if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null) {
			return false;
		}
		if (password != null ? !password.equals(user.password) : user.password != null) {
			return false;
		}
		if (roles != null ? !roles.equals(user.roles) : user.roles != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = nickName != null ? nickName.hashCode() : 0;
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (enabled ? 1 : 0);
		result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
		result = 31 * result + (roles != null ? roles.hashCode() : 0);
		return result;
	}
}
