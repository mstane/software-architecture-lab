package org.sm.lab.mybooks3;

import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.enums.SystemRole;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private long id;
	private SystemRole systemRole;
	
    public CurrentUser(Reader reader) {
        super(reader.getEmail(), reader.getPassword(), AuthorityUtils.createAuthorityList(reader.getSystemRole().toString()));
        this.id = reader.getId();
        this.systemRole = reader.getSystemRole();
    }

    public Long getId() {
        return id;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "id=" + id +
                "systemRole=" + systemRole +
                "} " + super.toString();
    }
}
