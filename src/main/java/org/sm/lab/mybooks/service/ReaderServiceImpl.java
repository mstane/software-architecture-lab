package org.sm.lab.mybooks.service;

import java.util.List;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.SystemRole;
import org.sm.lab.mybooks.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {

	@Autowired
	private ReaderRepository readerRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	
	@Override
	@PreAuthorize("@authorizationService.isAdmin(principal)")
	public Page<Reader> findAllReaders(int pageNumber, int pageSize) {
		List<Reader> list = readerRepository.findAll();
		Page<Reader> page = new PageImpl<Reader>(list);
        return page;
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessReader(principal, #id)")
	public Reader findReader(Long id) {
        return readerRepository.findOne(id);
    }
	
	@Override
	@PreAuthorize("@authorizationService.isAdmin(principal)")
	public Page<Reader> search(String keyword, int pageNumber, int pageSize) {
//		List<Reader> list = readerRepository.search(keyword.toLowerCase());
//		Page<Reader> page = new PageImpl<Reader>(list);
//        return page;
		return null;
	}
	
	@Override
	@PreAuthorize("@authorizationService.canAccessReader(principal, #reader)")
	public Reader saveReader(Reader reader) {
        return readerRepository.save(reader);
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessReader(principal, #id)")
	public void deleteReader(Long id) {
        readerRepository.delete(id);
    }
	
	@Override
	public Reader findByEmail(String email) {
		return readerRepository.findByEmail(email);
	}
	
	@Override
	public Reader registerReader(Reader reader) {
		reader.setSystemRole(SystemRole.Common);
		reader.setPassword(encodePassword(reader.getPassword()));
		return readerRepository.save(reader);
	}

	@Override
	public Reader changePassword(String generatedPassword, Reader reader) {
		reader.setPassword(encodePassword(generatedPassword));
		return readerRepository.save(reader);
	}
	
	private String encodePassword(String password) {
		if (password != null) {
			password  = passwordEncoder.encode(password);
			return password;
		}
		return null;
	}
	
}
