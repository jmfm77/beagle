package com.utad.beagle.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_user")
	@Positive
	private Long userId;

	@Column(name = "username")
	@NotBlank
	@Size(min = 6, max = 256)
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String username;

	@Column(name = "password")
	@NotBlank
	private String password;

	@Column(name = "role")
	@NotBlank
	@Pattern(regexp = "ROLE_(ADMIN|USER)")
	private String role;

	@Column(name = "token")
	private String token;

	@Column(name = "created_on")
	@NotBlank
	private String createdOn;

	@Column(name = "last_token")
	private String lastToken;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SecuredAccountsEntity> userSecuredAccounts;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastToken() {
		return lastToken;
	}

	public void setLastToken(String lastToken) {
		this.lastToken = lastToken;
	}

	public List<SecuredAccountsEntity> getUserSecuredAccounts() {
		return userSecuredAccounts;
	}

	public void setUserSecuredAccounts(List<SecuredAccountsEntity> userSecuredAccounts) {
		this.userSecuredAccounts = userSecuredAccounts;
	}

}
