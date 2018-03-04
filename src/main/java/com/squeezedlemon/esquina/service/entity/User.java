package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.squeezedlemon.esquina.service.validation.RegexConstants;

@Entity @Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 4743935074328143788L;

	@Pattern(regexp = RegexConstants.ACCOUNT_NAME_PATTERN, message = "error.valid.account.invalidAccountName") @NotEmpty(message = "error.valid.empty")
	@Id @Column(name = "AC_NAME")
	private String accountName;
	
	@Length(min = 2, message = "error.valid.user.short") @NotEmpty(message = "error.valid.empty")
	@Column(name = "US_FIRST_NAME", nullable = false)
	private String firstName;
	
	@Length(min = 2, message = "error.valid.user.short") 
	@Column(name = "US_MIDDLE_NAME")
	private String middleName;
	
	@Length(min = 2, message = "error.valid.user.short") @NotEmpty(message = "error.valid.empty")
	@Column(name = "US_LAST_NAME", nullable = false)
	private String lastName;
	
	@Past(message = "error.valid.past")	@NotNull(message = "error.valid.null")
	@Column(name = "US_BIRTH", nullable = false) @Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@NotNull(message = "error.valid.null")
	@Column(name = "US_GENDER", nullable = false) @Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "US_ABOUT_ME")
	private String aboutMe;
	
	//Images
	
	@OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "US_ICON")
	private Image icon;
	
	@OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "US_BIMAGE")
	private Image backgroundImage;

	//Relationships
	
	@OneToOne(fetch = FetchType.EAGER) @PrimaryKeyJoinColumn(name = "AC_NAME")
	private Account account;
	
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "LC_LANG_CODE")
	private LangCode langCode;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Checkin> checkins;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Follower> following;
	
	@OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
	private List<Follower> followers;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Track> tracks;
	
	public User() {
		
	}

	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public LangCode getLangCode() {
		return langCode;
	}

	public void setLangCode(LangCode langCode) {
		this.langCode = langCode;
	}

	public List<Checkin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<Checkin> checkins) {
		this.checkins = checkins;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public List<Follower> getFollowing() {
		return following;
	}

	public void setFollowing(List<Follower> following) {
		this.following = following;
	}

	public List<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}
	
}
