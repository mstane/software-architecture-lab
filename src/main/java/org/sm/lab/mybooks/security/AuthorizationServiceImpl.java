package org.sm.lab.mybooks.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.SystemRole;
import org.sm.lab.mybooks.repository.BookRepository;
import org.sm.lab.mybooks.repository.NoteRepository;
import org.sm.lab.mybooks.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

	@Autowired
	ReaderRepository readerRepository;
    
	@Autowired
	BookRepository bookRepository;
    
	@Autowired
	NoteRepository noteRepository;
	
	@Override
	public boolean isAdmin(UserDetailsImpl userDetails) {
		return userDetails != null && userDetails.getSystemRole() == SystemRole.Admin;
	}

	@Override
	public boolean canAccessReader(UserDetailsImpl userDetails, Reader reader) {
		if (reader == null || userDetails == null) {
			return false;
		} else if (reader.getId() == null) {
	        return true;
		} else {
			return canAccessReader(userDetails, reader.getId());
		}
	}	
	
    @Override
    public boolean canAccessReader(UserDetailsImpl userDetails, Long userId) {
        LOGGER.debug("Checking if user={} has access to user={}", userDetails, userId);
        return userDetails != null && (userDetails.getId().equals(userId) || isAdmin(userDetails));
    }

	@Override
	public boolean canAccessBook(UserDetailsImpl userDetails, Book book) {
		if (book == null || userDetails == null) {
			return false;
		} else if (book.getReader() == null) {
	        Reader reader = readerRepository.findOne(userDetails.getId());
	        book.setReader(reader);
	        return true;
		} else {
			return canAccessReader(userDetails, book.getReader().getId());
		}
	}

	@Override
	public boolean canAccessBook(UserDetailsImpl userDetails, Long bookId) {
		if (bookId == null || userDetails == null) return false;
		
		Book book = bookRepository.findOne(bookId);
		
		if (book == null) {
			return false;
		} else {
			return canAccessReader(userDetails, book.getReader().getId());
		}
	}

	@Override
	public boolean canAccessNote(UserDetailsImpl userDetails, Long noteId) {
		if (noteId == null || userDetails == null) return false;
		
		Note note = noteRepository.findOne(noteId);
		
		if (note == null) {
			return false;
		} else {
			return canAccessBook(userDetails, note.getBook());
		}
	}

}
