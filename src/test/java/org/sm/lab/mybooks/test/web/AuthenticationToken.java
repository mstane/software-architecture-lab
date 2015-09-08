package org.sm.lab.mybooks.test.web;

public class AuthenticationToken {
	
    private UserDetailsDto principal = null;
    private boolean authenticated = false;

    public AuthenticationToken() {
    	
    }

	public UserDetailsDto getPrincipal() {
		return principal;
	}

	public void setPrincipal(UserDetailsDto principal) {
		this.principal = principal;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
    
    
    
}
