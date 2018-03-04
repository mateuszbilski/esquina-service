package com.squeezedlemon.esquina.service.util;

public enum ApplicationRole {
	USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
	
	private String roleName;
	
	ApplicationRole(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}
	
}
