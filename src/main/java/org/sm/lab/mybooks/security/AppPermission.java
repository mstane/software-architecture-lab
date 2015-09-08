package org.sm.lab.mybooks.security;

import org.springframework.security.core.Authentication;

public abstract class AppPermission {
	
	public abstract boolean isAllowed(Authentication authentication, Object targetDomainObject);
	
	protected UserDetailsImpl getUserDetails(Authentication authentication) {
    	UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        return userDetails;
    }
 
	protected boolean isValidId(Object targetDomainObject) {
        return targetDomainObject instanceof String && targetDomainObject != null;
    }
 
	protected boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl;
    }
}
