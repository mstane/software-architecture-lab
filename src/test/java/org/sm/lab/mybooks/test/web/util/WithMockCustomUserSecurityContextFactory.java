package org.sm.lab.mybooks.test.web.util;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomUser> {
  
  @Autowired
  private UserDetailsService userDetailsService;
  
  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser mockUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    
    Reader reader = new Reader();
    reader.setId(mockUser.id());
    reader.setEmail(mockUser.email());
    reader.setPassword(mockUser.password());
    reader.setSystemRole(mockUser.systemRole());
    reader.setUsername(mockUser.username());
    
//    UserDetails principal = userDetailsService.loadUserByUsername(mockUser.email());

    UserDetailsImpl principal = new UserDetailsImpl(reader);
    Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}