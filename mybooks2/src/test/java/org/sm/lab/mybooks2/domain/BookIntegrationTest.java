package org.sm.lab.mybooks2.domain;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sm.lab.mybooks2.repository.BookRepository;
import org.sm.lab.mybooks2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class BookIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    BookDataOnDemand dod;

	private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(new com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig());

	@Autowired
    BookService bookService;

	@Autowired
    BookRepository bookRepository;

	@BeforeClass
    public static void setUp() {
        helper.setUp();
    }

	@AfterClass
    public static void tearDown() {
        helper.tearDown();
    }

	@Test
    public void testCountAllBooks() {
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", dod.getRandomBook());
        long count = bookService.countAllBooks();
        Assert.assertTrue("Counter for 'Book' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindBook() {
        Book obj = dod.getRandomBook();
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", obj);
        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Book' failed to provide an identifier", id);
        obj = bookService.findBook(id);
        Assert.assertNotNull("Find method for 'Book' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Book' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllBooks() {
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", dod.getRandomBook());
        long count = bookService.countAllBooks();
        Assert.assertTrue("Too expensive to perform a find all test for 'Book', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Book> result = bookService.findAllBooks();
        Assert.assertNotNull("Find all method for 'Book' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Book' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindBookEntries() {
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", dod.getRandomBook());
        long count = bookService.countAllBooks();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Book> result = bookService.findBookEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Book' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Book' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Book obj = dod.getRandomBook();
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", obj);
        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Book' failed to provide an identifier", id);
        obj = bookService.findBook(id);
        Assert.assertNotNull("Find method for 'Book' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBook(obj);
        Long currentVersion = obj.getVersion();
        bookRepository.flush();
        Assert.assertTrue("Version for 'Book' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateBookUpdate() {
        Book obj = dod.getRandomBook();
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", obj);
        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Book' failed to provide an identifier", id);
        obj = bookService.findBook(id);
        boolean modified =  dod.modifyBook(obj);
        Long currentVersion = obj.getVersion();
        Book merged = bookService.updateBook(obj);
        bookRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Book' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveBook() {
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", dod.getRandomBook());
        Book obj = dod.getNewTransientBook(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Book' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Book' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Book' identifier to no longer be null", obj.getId());
    }

	@Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void testDeleteBook() {
        Book obj = dod.getRandomBook();
        Assert.assertNotNull("Data on demand for 'Book' failed to initialize correctly", obj);
        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Book' failed to provide an identifier", id);
        obj = bookService.findBook(id);
        bookService.deleteBook(obj);
        bookRepository.flush();
        Assert.assertNull("Failed to remove 'Book' with identifier '" + id + "'", bookService.findBook(id));
    }
}
