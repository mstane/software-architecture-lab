package org.sm.lab.mybooks.domain;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.repository.NoteRepository;
import org.sm.lab.mybooks.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class NoteIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    NoteDataOnDemand dod;

	@Autowired
    NoteService noteService;

	@Autowired
    NoteRepository noteRepository;

	@Test
    public void testCountAllNotes() {
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", dod.getRandomNote());
        long count = noteService.countAllNotes();
        Assert.assertTrue("Counter for 'Note' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindNote() {
        Note obj = dod.getRandomNote();
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Note' failed to provide an identifier", id);
        obj = noteService.findNote(id);
        Assert.assertNotNull("Find method for 'Note' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Note' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllNotes() {
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", dod.getRandomNote());
        long count = noteService.countAllNotes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Note', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Note> result = noteService.findAllNotes();
        Assert.assertNotNull("Find all method for 'Note' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Note' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindNoteEntries() {
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", dod.getRandomNote());
        long count = noteService.countAllNotes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Note> result = noteService.findNoteEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Note' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Note' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Note obj = dod.getRandomNote();
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Note' failed to provide an identifier", id);
        obj = noteService.findNote(id);
        Assert.assertNotNull("Find method for 'Note' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyNote(obj);
        Integer currentVersion = obj.getVersion();
        noteRepository.flush();
        Assert.assertTrue("Version for 'Note' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateNoteUpdate() {
        Note obj = dod.getRandomNote();
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Note' failed to provide an identifier", id);
        obj = noteService.findNote(id);
        boolean modified =  dod.modifyNote(obj);
        Integer currentVersion = obj.getVersion();
        Note merged = noteService.updateNote(obj);
        noteRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Note' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveNote() {
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", dod.getRandomNote());
        Note obj = dod.getNewTransientNote(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Note' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Note' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Note' identifier to no longer be null", obj.getId());
    }

	@Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void testDeleteNote() {
        Note obj = dod.getRandomNote();
        Assert.assertNotNull("Data on demand for 'Note' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Note' failed to provide an identifier", id);
        obj = noteService.findNote(id);
        noteService.deleteNote(obj);
        noteRepository.flush();
        Assert.assertNull("Failed to remove 'Note' with identifier '" + id + "'", noteService.findNote(id));
    }
}
