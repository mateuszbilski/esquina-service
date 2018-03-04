package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CHECKINS")
public class Checkin implements Serializable {

	private static final long serialVersionUID = 4233754559744839801L;

	@Id
	@SequenceGenerator(name = "seq_checkins_id", sequenceName="SEQ_CH_ID", allocationSize=1)
	@GeneratedValue(generator = "seq_checkins_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "CH_ID")
	private Long id;
	
	@Min(value = 0, message = "error.valid.checkin.score.min")
	@Max(value = 5, message = "error.valid.checkin.score.max")
	@Column(name = "CH_SCORE")
	private BigDecimal score;
	
	@Column(name = "CH_COMMENT")
	private String comment;

	@NotNull(message = "error.valid.null")
	@Column(name = "CH_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LO_ID")
	private Location location;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AC_NAME")
	private User user;
	
	public Checkin() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
