package org.sm.lab.mybooks2.web;

import org.sm.lab.mybooks2.domain.Book;
import org.sm.lab.mybooks2.domain.Note;
import org.sm.lab.mybooks2.domain.Reader;
import org.sm.lab.mybooks2.service.BookService;
import org.sm.lab.mybooks2.service.NoteService;
import org.sm.lab.mybooks2.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    BookService bookService;

	@Autowired
    NoteService noteService;

	@Autowired
    ReaderService readerService;

	public Converter<Book, String> getBookToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.sm.lab.mybooks2.domain.Book, java.lang.String>() {
            public String convert(Book book) {
                return new StringBuilder().append(book.getTitle()).append(' ').append(book.getAuthor()).append(' ').append(book.getUrl()).append(' ').append(book.getStartReadingDate()).toString();
            }
        };
    }

	public Converter<String, Book> getIdToBookConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.sm.lab.mybooks2.domain.Book>() {
            public org.sm.lab.mybooks2.domain.Book convert(java.lang.String id) {
                return bookService.findBook(id);
            }
        };
    }

	public Converter<Note, String> getNoteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.sm.lab.mybooks2.domain.Note, java.lang.String>() {
            public String convert(Note note) {
                return new StringBuilder().append(note.getTitle()).append(' ').append(note.getContent()).append(' ').append(note.getCreatedTime()).append(' ').append(note.getModifiedTime()).toString();
            }
        };
    }

	public Converter<String, Note> getIdToNoteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.sm.lab.mybooks2.domain.Note>() {
            public org.sm.lab.mybooks2.domain.Note convert(java.lang.String id) {
                return noteService.findNote(id);
            }
        };
    }

	public Converter<Reader, String> getReaderToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.sm.lab.mybooks2.domain.Reader, java.lang.String>() {
            public String convert(Reader reader) {
                return new StringBuilder().append(reader.getUsername()).append(' ').append(reader.getPassword()).append(' ').append(reader.getFirstName()).append(' ').append(reader.getLastName()).toString();
            }
        };
    }

	public Converter<String, Reader> getIdToReaderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.sm.lab.mybooks2.domain.Reader>() {
            public org.sm.lab.mybooks2.domain.Reader convert(java.lang.String id) {
                return readerService.findReader(id);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getBookToStringConverter());
        registry.addConverter(getIdToBookConverter());
        registry.addConverter(getNoteToStringConverter());
        registry.addConverter(getIdToNoteConverter());
        registry.addConverter(getReaderToStringConverter());
        registry.addConverter(getIdToReaderConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
