package com.squeezedlemon.esquina.service.form;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squeezedlemon.esquina.service.entity.Gender;
import com.squeezedlemon.esquina.service.entity.User;
import com.squeezedlemon.esquina.service.validation.RegexConstants;


public class CreateUserAccountForm {
	
	private static final String ABOUT_ME_PROPERTY = "aboutMe";

	private static final String GENDER_PROPERTY = "gender";

	private static final String BIRTH_DATE_PROPERTY = "birthDate";

	private static final String LAST_NAME_PROPERTY = "lastName";

	private static final String MIDDLE_NAME_PROPERTY = "middleName";

	private static final String FIRST_NAME_PROPERTY = "firstName";

	private static final String ACCOUNT_NAME_PROPERTY = "accountName";

	@Valid
	private User user;

	@Pattern(regexp=RegexConstants.ACCOUNT_PASSWORD_PATTERN, message="error.valid.account.weakPassword") @NotEmpty(message = "error.valid.empty")
	private String password;
	
	@NotEmpty(message = "error.valid.empty")
	private String language;

	public CreateUserAccountForm() {
		
	}
	
	public CreateUserAccountForm(User user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	
	@JsonCreator
	public CreateUserAccountForm(
			@JsonProperty(ACCOUNT_NAME_PROPERTY) String accountName, 
			@JsonProperty(FIRST_NAME_PROPERTY) String firstName,
			@JsonProperty(MIDDLE_NAME_PROPERTY) String middleName,
			@JsonProperty(LAST_NAME_PROPERTY) String lastName,
			@JsonProperty(BIRTH_DATE_PROPERTY) Date birthDate,
			@JsonProperty(GENDER_PROPERTY) Gender gender,
			@JsonProperty(ABOUT_ME_PROPERTY) String aboutMe) {
		
		user = new User();
		user.setAccountName(accountName);
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setBirthDate(birthDate);
		user.setGender(gender);
		user.setAboutMe(aboutMe);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
