package org.sm.lab.mybooks.service;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.repository.data.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReaderServiceImpl implements ReaderService {

	@Autowired
    ReaderRepository readerRepository;

	@Override
	public Reader findReader(Long id) {
		return readerRepository.findOne(id);
	}

	@Override
	public Iterable<Long> findReaderIds() {
		return readerRepository.findAllId();
	}
    
		
}
