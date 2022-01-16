package com.azubike.ellipsis.api.request.sqlInjection;

public class JdbcCustomerPatchRequest {
	private String fullName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
