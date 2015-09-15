package org.sm.lab.mybooks.service;
import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.domain.Page;

public interface ReaderService {
	
	public abstract Page<Reader> findAllReaders(int pageNumber, int pageSize);
	
	public abstract Reader findReader(Long id);
	
	public abstract Page<Reader> search(String keyword, int pageNumber, int pageSize);
	
	public abstract Reader saveReader(Reader reader);
	
	public abstract void deleteReader(Long id);
	

	
	public abstract Reader findByEmail(String email);
	
	public abstract Reader registerReader(Reader reader);
	
	public abstract Reader changePassword(String generatedPassword, Reader reader);
	

}
