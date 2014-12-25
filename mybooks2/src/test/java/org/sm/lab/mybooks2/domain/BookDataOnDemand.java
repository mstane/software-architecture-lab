package org.sm.lab.mybooks2.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.sm.lab.mybooks2.enums.Genre;
import org.sm.lab.mybooks2.repository.BookRepository;
import org.sm.lab.mybooks2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class BookDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Book> data;

	@Autowired
    ReaderDataOnDemand readerDataOnDemand;

	@Autowired
    BookService bookService;

	@Autowired
    BookRepository bookRepository;

	public Book getNewTransientBook(int index) {
        Book obj = new Book();
        setAuthor(obj, index);
        setEndReadingDate(obj, index);
        setGenre(obj, index);
        setRating(obj, index);
        setStartReadingDate(obj, index);
        setTitle(obj, index);
        setUrl(obj, index);
        return obj;
    }

	public void setAuthor(Book obj, int index) {
        String author = "author_" + index;
        obj.setAuthor(author);
    }

	public void setEndReadingDate(Book obj, int index) {
        Date endReadingDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setEndReadingDate(endReadingDate);
    }

	public void setGenre(Book obj, int index) {
        Genre genre = Genre.class.getEnumConstants()[0];
        obj.setGenre(genre);
    }

	public void setRating(Book obj, int index) {
        Integer rating = new Integer(index);
        obj.setRating(rating);
    }

	public void setStartReadingDate(Book obj, int index) {
        Date startReadingDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setStartReadingDate(startReadingDate);
    }

	public void setTitle(Book obj, int index) {
        String title = "title_" + index;
        obj.setTitle(title);
    }

	public void setUrl(Book obj, int index) {
        String url = "url_" + index;
        obj.setUrl(url);
    }

	public Book getSpecificBook(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Book obj = data.get(index);
        String id = obj.getId();
        return bookService.findBook(id);
    }

	public Book getRandomBook() {
        init();
        Book obj = data.get(rnd.nextInt(data.size()));
        String id = obj.getId();
        return bookService.findBook(id);
    }

	public boolean modifyBook(Book obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = bookService.findBookEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Book' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Book>();
        for (int i = 0; i < 10; i++) {
            Book obj = getNewTransientBook(i);
            try {
                bookService.saveBook(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            bookRepository.flush();
            data.add(obj);
        }
    }
}
