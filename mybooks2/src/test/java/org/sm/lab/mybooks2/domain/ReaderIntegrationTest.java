package org.sm.lab.mybooks2.domain;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sm.lab.mybooks2.repository.ReaderRepository;
import org.sm.lab.mybooks2.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
public class ReaderIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ReaderDataOnDemand dod;

	@Autowired
    ReaderService readerService;

	@Autowired
    ReaderRepository readerRepository;

	@Test
    public void testCountAllReaders() {
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", dod.getRandomReader());
        long count = readerService.countAllReaders();
        Assert.assertTrue("Counter for 'Reader' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindReader() {
        Reader obj = dod.getRandomReader();
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Reader' failed to provide an identifier", id);
        obj = readerService.findReader(id);
        Assert.assertNotNull("Find method for 'Reader' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Reader' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllReaders() {
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", dod.getRandomReader());
        long count = readerService.countAllReaders();
        Assert.assertTrue("Too expensive to perform a find all test for 'Reader', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Reader> result = readerService.findAllReaders();
        Assert.assertNotNull("Find all method for 'Reader' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Reader' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindReaderEntries() {
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", dod.getRandomReader());
        long count = readerService.countAllReaders();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Reader> result = readerService.findReaderEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Reader' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Reader' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Reader obj = dod.getRandomReader();
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Reader' failed to provide an identifier", id);
        obj = readerService.findReader(id);
        Assert.assertNotNull("Find method for 'Reader' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyReader(obj);
        Integer currentVersion = obj.getVersion();
        readerRepository.flush();
        Assert.assertTrue("Version for 'Reader' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateReaderUpdate() {
        Reader obj = dod.getRandomReader();
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Reader' failed to provide an identifier", id);
        obj = readerService.findReader(id);
        boolean modified =  dod.modifyReader(obj);
        Integer currentVersion = obj.getVersion();
        Reader merged = readerService.updateReader(obj);
        readerRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Reader' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveReader() {
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", dod.getRandomReader());
        Reader obj = dod.getNewTransientReader(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Reader' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Reader' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Reader' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteReader() {
        Reader obj = dod.getRandomReader();
        Assert.assertNotNull("Data on demand for 'Reader' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Reader' failed to provide an identifier", id);
        obj = readerService.findReader(id);
        readerService.deleteReader(obj);
        readerRepository.flush();
        Assert.assertNull("Failed to remove 'Reader' with identifier '" + id + "'", readerService.findReader(id));
    }
}
