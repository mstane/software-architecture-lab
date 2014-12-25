package org.sm.lab.mybooks2.service;
import java.util.List;

import org.sm.lab.mybooks2.domain.Reader;

public interface ReaderService {

	public abstract long countAllReaders();


	public abstract void deleteReader(Reader reader);


	public abstract Reader findReader(String id);


	public abstract List<Reader> findAllReaders();


	public abstract List<Reader> findReaderEntries(int firstResult, int maxResults);


	public abstract void saveReader(Reader reader);


	public abstract Reader updateReader(Reader reader);

}
