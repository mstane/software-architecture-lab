package org.sm.lab.mybooks.service;
import org.sm.lab.mybooks.domain.Reader;

public interface ReaderService {
	
	public abstract Reader findReader(Long id);
	
	public abstract Iterable<Long> findReaderIds();

}
