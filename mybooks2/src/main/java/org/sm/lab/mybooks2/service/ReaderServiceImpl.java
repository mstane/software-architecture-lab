package org.sm.lab.mybooks2.service;

import java.util.List;
import org.sm.lab.mybooks2.domain.Reader;
import org.sm.lab.mybooks2.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {

	@Autowired
    ReaderRepository readerRepository;

	public long countAllReaders() {
        return readerRepository.count();
    }

	public void deleteReader(Reader reader) {
        readerRepository.delete(reader);
    }

	public Reader findReader(Long id) {
        return readerRepository.findOne(id);
    }

	public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

	public List<Reader> findReaderEntries(int firstResult, int maxResults) {
        return readerRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }

	public Reader updateReader(Reader reader) {
        return readerRepository.save(reader);
    }
}
