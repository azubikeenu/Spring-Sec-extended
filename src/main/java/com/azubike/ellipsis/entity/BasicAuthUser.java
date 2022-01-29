package com.azubike.ellipsis.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

public class BasicAuthUser {

	@Id
	private int userId;
	private String username;
	private String passwordHash;
	private String salt;
	private String displayName;

	@MappedCollection(idColumn = "user_id")
	private Set<BasicAclUserUriRef> allowedUris;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Set<BasicAclUserUriRef> getAllowedUris() {
		return allowedUris;
	}

	public void setAllowedUris(Set<BasicAclUserUriRef> allowedUris) {
		this.allowedUris = allowedUris;
	}

}
