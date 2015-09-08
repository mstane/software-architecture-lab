package org.sm.lab.mybooks.test.web;

import java.io.Serializable;

import org.sm.lab.mybooks.enums.SystemRole;

public class UserDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private SystemRole systemRole;
	private String displayName;
	
    public UserDetailsDto() {
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SystemRole getSystemRole() {
		return systemRole;
	}

	public void setSystemRole(SystemRole systemRole) {
		this.systemRole = systemRole;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
    



}
