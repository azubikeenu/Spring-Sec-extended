package com.azubike.ellipsis.entity;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;

public class JdbcCustomer {
	@Id
	private int customerId;
	private String fullName;
	@Email
	private String email;

	private LocalDate birthDate;
	@Pattern(regexp = "^[MF]$")
	private String gender;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
