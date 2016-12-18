package org.sm.lab.mybooks.service;

import java.util.Optional;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.SystemRole;
import org.sm.lab.mybooks.repository.data.ReaderKVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReaderKVServiceImpl implements ReaderKVService {

	@Autowired
    ReaderKVRepository readerKVRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	
	@Override
	@PreAuthorize("@authorizationService.isAdmin(principal)")
	public Page<Reader> findAllReaders(int pageNumber, int pageSize) {
        return readerKVRepository.findAll(new PageRequest(pageNumber, pageSize));
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessReader(principal, #id)")
	public Reader findReader(Long id) {
        return readerKVRepository.findOne(id);
    }
	
	@Override
	@PreAuthorize("@authorizationService.isAdmin(principal)")
	public Page<Reader> search(String keyword, int pageNumber, int pageSize) {
		return readerKVRepository.search(keyword.toLowerCase(), new PageRequest(pageNumber, pageSize));
	}
	
	@Override
	@PreAuthorize("@authorizationService.canAccessReader(principal, #reader)")
	public Reader saveReader(Reader reader) {
        reader = readerKVRepository.save(reader);
        return reader;
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessReader(principal, #id)")
	public void deleteReader(Long id) {
        readerKVRepository.delete(id);
    }
	
	@Override
	public Optional<Reader> findByUsername(String username) {
		return readerKVRepository.findByUsername(username);
	}
	
	@Override
	public Optional<Reader> findByEmail(String email) {
		return readerKVRepository.findByEmail(email);
	}
	
	@Override
	public Reader registerReader(Reader reader) {
		reader.setSystemRole(SystemRole.Common);
		reader.setPassword(encodePassword(reader.getPassword()));
		return readerKVRepository.save(reader);
	}

	@Override
	public Reader changePassword(String generatedPassword, Reader reader) {
		reader.setPassword(encodePassword(generatedPassword));
		return readerKVRepository.save(reader);
	}
	
	private String encodePassword(String password) {
		if (password != null) {
			password  = passwordEncoder.encode(password);
			return password;
		}
		return null;
	}
	
}
