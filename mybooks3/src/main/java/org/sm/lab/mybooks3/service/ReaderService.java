package org.sm.lab.mybooks3.service;
import java.util.List;
import java.util.Optional;

import org.sm.lab.mybooks3.domain.Reader;

public interface ReaderService {

	public abstract long countAllReaders();


	public abstract void deleteReader(Long id);


	public abstract Reader findReader(Long id);
	
	
	public abstract Optional<Reader> findByUsername(String username);
	
	
	public abstract Optional<Reader> findByEmail(String email);


	public abstract List<Reader> findAllReaders();


	public abstract List<Reader> findReaderEntries(int firstResult, int maxResults);
	
	
	public abstract List<Reader> search(String keyword, int firstResult, int maxResults);

	
	public abstract Reader saveReader(Reader reader);


	public abstract Reader updateReader(Reader reader);

}
