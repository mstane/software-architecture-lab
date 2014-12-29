package org.sm.lab.mybooks2.security;

import java.io.Serializable;

import org.sm.lab.mybooks2.enums.SystemRole;

public class ReaderSession implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private String password;
	private SystemRole systemRole;    
    
    public ReaderSession() {
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean isSystemAdmin() {
		return systemRole == SystemRole.Admin;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public SystemRole getSystemRole() {
		return systemRole;
	}
	
	public void setSysteRole(SystemRole systemRole) {
		this.systemRole = systemRole;
	}    
    
}