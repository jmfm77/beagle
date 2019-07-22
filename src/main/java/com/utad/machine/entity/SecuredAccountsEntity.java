package com.utad.machine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "secured_accounts")
public class SecuredAccountsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_secured_account")
	@Positive
	private Long securedAccountId;

	@Column(name = "name")
	@NotBlank
	@Size(min = 1, max = 100)
	private String name;

	@Column(name = "description")
	@Size(max = 250)
	private String description;

	@Column(name = "username")
	@Size(max = 256)
	private String username;

	@Column(name = "password")
	@Size(max = 256)
	private String password;

	@Column(name = "token")
	@Size(max = 45)
	private String token;

	@Column(name = "uri")
	@Size(max = 500)
	private String uri;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user")
	@NotNull
	private UserEntity user;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getSecuredAccountId() {
		return securedAccountId;
	}

	public void setSecuredAccountId(Long securedAccountId) {
		this.securedAccountId = securedAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
