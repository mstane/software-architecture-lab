
package org.sm.lab.mybooks.server.db;

import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Reader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reader")
    private List<Book> books = new ArrayList<Book>(0);

    public Reader() {
    }

    public Reader(ReaderDto readerDto) {
        setDto(readerDto);
    }
    
    public void setDto(ReaderDto readerDto) {
        this.id = readerDto.getId();
        this.username = readerDto.getUsername();
        this.password = readerDto.getPassword();
        this.firstName = readerDto.getFirstName();
        this.lastName = readerDto.getLastName();
        this.email = readerDto.getEmail();
    }

    public ReaderDto getDto() {
        List<BookDto> bookDtoList = new ArrayList<BookDto>();
        for (Book book : books) {
            bookDtoList.add(new BookDto(book.getId(), book.getTitle()));
        }
        return new ReaderDto(this.id, this.username, this.password, this.firstName, this.lastName, this.email, bookDtoList);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
