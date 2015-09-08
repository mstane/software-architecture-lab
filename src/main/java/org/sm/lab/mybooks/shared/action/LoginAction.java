package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

public class LoginAction implements Action<LoginResult> {

	private String username;
	private String password;
	private boolean rememberMe;
	
	private String sessionId;
	
    LoginAction() {
    }
    
    public LoginAction(String username, String password, boolean rememberMe) {
    	this.username = username;
    	this.password = password;
    	this.rememberMe = rememberMe;
    }

    public LoginAction(String sessionId) {
    	this.sessionId = sessionId;
    }
    
    public String getUsername() {
    	return this.username;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public String getSessionId() {
    	return this.sessionId;
    }
    
    public boolean isRememberMe() {
        return this.rememberMe;
    }
    
}