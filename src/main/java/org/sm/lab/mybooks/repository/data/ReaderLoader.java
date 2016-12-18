package org.sm.lab.mybooks.repository.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.sm.lab.mybooks.domain.Reader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hazelcast.core.MapLoader;

@Component
public class ReaderLoader implements ApplicationContextAware, MapLoader<Long, Reader> {

	private static ReaderRepository readerRepository;

	@Override
	public Reader load(Long key) {
		return readerRepository.findOne(key);
	}

	@Override
	public Map<Long, Reader> loadAll(Collection<Long> keys) {
		Map<Long, Reader> result = new HashMap<>();
        for(Long key : keys) {
        	Reader noun = this.load(key);
                if (noun!=null) {
                	result.put(key, noun);
                }
        }
        return result;
    }

	@Override
	public Iterable<Long> loadAllKeys() {
		return readerRepository.findAllId();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		readerRepository = applicationContext.getBean(ReaderRepository.class);
	}

}
