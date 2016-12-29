package org.sm.lab.mybooks.security;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.SystemRole;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserDetailsImpl extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private long id;
    private SystemRole systemRole;
    private String displayName;

    public UserDetailsImpl(Reader reader) {
        super(reader.getEmail(), reader.getPassword(),
                AuthorityUtils.createAuthorityList(reader.getSystemRole().toString()));
        this.id = reader.getId();
        this.systemRole = reader.getSystemRole();
        setDisplayName(reader);
    }

    public Long getId() {
        return id;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public String getDisplayName() {
        return displayName;
    }

    private void setDisplayName(Reader reader) {
        displayName = reader.getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDetailsImpl other = (UserDetailsImpl) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
