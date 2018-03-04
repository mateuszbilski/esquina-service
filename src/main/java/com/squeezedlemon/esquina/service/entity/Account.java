package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.squeezedlemon.esquina.service.validation.RegexConstants;

@Entity @Table(name = "ACCOUNTS")
public class Account implements Serializable {
	
	private static final long serialVersionUID = 498747823064137765L;

	@Pattern(regexp = RegexConstants.ACCOUNT_NAME_PATTERN, message = "error.valid.account.invalidAccountName") @NotEmpty(message = "error.valid.empty")
	@Id @Column(name = "AC_NAME", nullable = false)
	private String accountName;
	
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "AC_ENC_PASS", nullable = false)
	private String encryptedPassword;
	
	@Column(name = "AC_ROLE")
	private String role;
	
	@NotNull(message = "error.valid.null")
	@Column(name = "AC_ENABLED", nullable = false)
	private Boolean enabled;
	
	@NotNull(message = "error.valid.null")
	@Column(name = "AC_CREATED", nullable = false) @Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name = "AC_DELETED") @Temporal(TemporalType.TIMESTAMP)
	private Date deleted;
	
	// Relationships
	
	@OneToOne(mappedBy = "account", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private User user;
	
	public Account() {
		
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	
}
