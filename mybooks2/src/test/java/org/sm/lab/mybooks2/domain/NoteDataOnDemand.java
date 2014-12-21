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
import org.sm.lab.mybooks2.repository.NoteRepository;
import org.sm.lab.mybooks2.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class NoteDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Note> data;

	@Autowired
    BookDataOnDemand bookDataOnDemand;

	@Autowired
    NoteService noteService;

	@Autowired
    NoteRepository noteRepository;

	public Note getNewTransientNote(int index) {
        Note obj = new Note();
        setContent(obj, index);
        setCreatedTime(obj, index);
        setModifiedTime(obj, index);
        setTitle(obj, index);
        return obj;
    }

	public void setContent(Note obj, int index) {
        String content = "content_" + index;
        obj.setContent(content);
    }

	public void setCreatedTime(Note obj, int index) {
        Date createdTime = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setCreatedTime(createdTime);
    }

	public void setModifiedTime(Note obj, int index) {
        Date modifiedTime = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setModifiedTime(modifiedTime);
    }

	public void setTitle(Note obj, int index) {
        String title = "title_" + index;
        obj.setTitle(title);
    }

	public Note getSpecificNote(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Note obj = data.get(index);
        Long id = obj.getId();
        return noteService.findNote(id);
    }

	public Note getRandomNote() {
        init();
        Note obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return noteService.findNote(id);
    }

	public boolean modifyNote(Note obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = noteService.findNoteEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Note' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Note>();
        for (int i = 0; i < 10; i++) {
            Note obj = getNewTransientNote(i);
            try {
                noteService.saveNote(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            noteRepository.flush();
            data.add(obj);
        }
    }
}
