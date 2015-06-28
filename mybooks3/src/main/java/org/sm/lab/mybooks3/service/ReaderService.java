package org.sm.lab.mybooks3.service;
import java.util.List;
import java.util.Optional;

import org.sm.lab.mybooks3.domain.Reader;
import org.springframework.data.domain.Page;

public interface ReaderService {

	public abstract long countAllReaders();


	public abstract void deleteReader(Long id);


	public abstract Reader findReader(Long id);
	
	
	public abstract Optional<Reader> findByUsername(String username);
	
	
	public abstract Optional<Reader> findByEmail(String email);


	public abstract List<Reader> findAllReaders();


	public abstract Page<Reader> findReaderEntries(int pageNumber, int pageSize);
	
	
	public abstract List<Reader> search(String keyword, int pageNumber, int pageSize);

	
	public abstract Reader saveReader(Reader reader);


	public abstract Reader updateReader(Reader reader);

}
