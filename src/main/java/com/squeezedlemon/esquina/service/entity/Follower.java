package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "FOLLOWERS", uniqueConstraints = 
		@UniqueConstraint(columnNames = {"AC_NAME", "AC_NAME_FOLLOWING"}))
public class Follower implements Serializable {

	private static final long serialVersionUID = 4538042749997640197L;
	
	@Id
	@SequenceGenerator(name = "sequence_followers_id", sequenceName="SEQ_FO_ID", allocationSize=1)
	@GeneratedValue(generator = "sequence_followers_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "FO_ID")
	private Long id;
	
	@Column(name = "FO_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AC_NAME")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AC_NAME_FOLLOWING")
	private User following;

	public Follower() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFollowing() {
		return following;
	}

	public void setFollowing(User following) {
		this.following = following;
	}

}
