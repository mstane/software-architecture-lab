package org.sm.lab.mybooks2.security;

import java.util.Collection;
import java.util.HashSet;

import org.sm.lab.mybooks2.enums.SystemRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	private Long id;
	private String password;
	private String username;
	private SystemRole systemRole;

	public UserDetailsImpl(Long id, String username, String password,
			Collection<GrantedAuthority> authorities, SystemRole systemRole) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.systemRole = systemRole;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public SystemRole getSystemRole() {
		return systemRole;
	}

	public boolean isAdmin() {
		return systemRole == SystemRole.Admin;
	}

}