package org.sm.lab.mybooks3.service;

import java.util.List;
import java.util.Optional;

import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {

	@Autowired
    ReaderRepository readerRepository;
	
    @Autowired
    PasswordEncoder passwordEncoder;

	public long countAllReaders() {
        return readerRepository.count();
    }

	public void deleteReader(Long id) {
        readerRepository.delete(id);
    }

	public Reader findReader(Long id) {
        return readerRepository.findOne(id);
    }
	
	public Optional<Reader> findByUsername(String username) {
		return readerRepository.findByUsername(username);
	}
	
	public Optional<Reader> findByEmail(String email) {
		return readerRepository.findByEmail(email);
	}

	public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

	public Page<Reader> findReaderEntries(int pageNumber, int pageSize) {
        return readerRepository.findAll(new PageRequest(pageNumber, pageSize));
    }
	
	public List<Reader> search(String keyword, int firstResult, int maxResults) {
		return readerRepository.search(keyword.toLowerCase(), new PageRequest(firstResult / maxResults, maxResults)).getContent();
	}

	public Reader saveReader(Reader reader) {
		encodePassword(reader);
        return readerRepository.save(reader);
    }

	public Reader updateReader(Reader reader) {
		encodePassword(reader);
		return readerRepository.save(reader);
    }
	
	private void encodePassword(Reader reader) {
		String password = reader.getPassword();
		if (password != null) {
			password  = passwordEncoder.encode(password);
			reader.setPassword(password);
		}
		
	}
}
