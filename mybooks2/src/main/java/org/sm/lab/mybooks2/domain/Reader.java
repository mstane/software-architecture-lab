package org.sm.lab.mybooks2.domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.datanucleus.api.jpa.annotations.Extension;
import org.sm.lab.mybooks2.enums.SystemRole;

@Entity
public class Reader {

    /**
     */
    @NotNull
    @Size(min = 2)
    private String username;

    /**
     */
    private String password;

    /**
     */
    private String firstName;

    /**
     */
    private String lastName;

    /**
     */
    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String email;

    /**
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private SystemRole systemRole;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reader")
    private List<Book> books = new ArrayList<Book>();

	public String getUsername() {
        return this.username;
    }

	public void setUsername(String username) {
        this.username = username;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public SystemRole getSystemRole() {
        return this.systemRole;
    }

	public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

	public List<Book> getBooks() {
        return this.books;
    }

	public void setBooks(List<Book> books) {
        this.books = books;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

	@Version
    @Column(name = "version")
    private Long version;

	public String getId() {
        return this.id;
    }

	public void setId(String id) {
        this.id = id;
    }

	public Long getVersion() {
        return this.version;
    }

	public void setVersion(Long version) {
        this.version = version;
    }
}
