package org.sm.lab.mybooks.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.SystemRole;
import org.sm.lab.mybooks.repository.ReaderRepository;
import org.sm.lab.mybooks.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class ReaderDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Reader> data;

	@Autowired
    ReaderService readerService;

	@Autowired
    ReaderRepository readerRepository;

	public Reader getNewTransientReader(int index) {
        Reader obj = new Reader();
        setEmail(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setPassword(obj, index);
        setSystemRole(obj, index);
        setUsername(obj, index);
        return obj;
    }

	public void setEmail(Reader obj, int index) {
        String email = "foo" + index + "@bar.com";
        obj.setEmail(email);
    }

	public void setFirstName(Reader obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }

	public void setLastName(Reader obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }

	public void setPassword(Reader obj, int index) {
        String password = "password_" + index;
        obj.setPassword(password);
    }

	public void setSystemRole(Reader obj, int index) {
        SystemRole systemRole = SystemRole.class.getEnumConstants()[0];
        obj.setSystemRole(systemRole);
    }

	public void setUsername(Reader obj, int index) {
        String username = "username_" + index;
        obj.setUsername(username);
    }

	public Reader getSpecificReader(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Reader obj = data.get(index);
        Long id = obj.getId();
        return readerService.findReader(id);
    }

	public Reader getRandomReader() {
        init();
        Reader obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return readerService.findReader(id);
    }

	public boolean modifyReader(Reader obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = readerService.findReaderEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Reader' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Reader>();
        for (int i = 0; i < 10; i++) {
            Reader obj = getNewTransientReader(i);
            try {
                readerService.saveReader(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            readerRepository.flush();
            data.add(obj);
        }
    }
}
