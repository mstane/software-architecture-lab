package org.sm.lab.mybooks2.web;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.sm.lab.mybooks2.domain.Book;
import org.sm.lab.mybooks2.enums.Genre;
import org.sm.lab.mybooks2.service.BookService;
import org.sm.lab.mybooks2.service.NoteService;
import org.sm.lab.mybooks2.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/books")
@Controller
public class BookController {

	@Autowired
    BookService bookService;

	@Autowired
    NoteService noteService;

	@Autowired
    ReaderService readerService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Book book, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, book);
            return "books/create";
        }
        uiModel.asMap().clear();
        bookService.saveBook(book);
        return "redirect:/books/" + encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Book());
        return "books/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("book", bookService.findBook(id));
        uiModel.addAttribute("itemId", id);
        return "books/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("books", Book.findBookEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) bookService.countAllBooks() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("books", Book.findAllBooks(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "books/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Book book, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, book);
            return "books/update";
        }
        uiModel.asMap().clear();
        bookService.updateBook(book);
        return "redirect:/books/" + encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") String id, Model uiModel) {
        populateEditForm(uiModel, bookService.findBook(id));
        return "books/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") String id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Book book = bookService.findBook(id);
        bookService.deleteBook(book);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/books";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("book_startreadingdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("book_endreadingdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Book book) {
        uiModel.addAttribute("book", book);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("notes", noteService.findAllNotes());
        uiModel.addAttribute("readers", readerService.findAllReaders());
        uiModel.addAttribute("genres", Arrays.asList(Genre.values()));
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(params = { "find=ByTitle", "form" }, method = RequestMethod.GET)
    public String findBooksByTitleForm(Model uiModel) {
        return "books/findBooksByTitle";
    }

	@RequestMapping(params = "find=ByTitle", method = RequestMethod.GET)
    public String findBooksByTitle(@RequestParam("title") String title, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("books", Book.findBooksByTitle(title, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
            float nrOfPages = (float) Book.countFindBooksByTitle(title) / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("books", Book.findBooksByTitle(title, sortFieldName, sortOrder).getResultList());
        }
        addDateTimeFormatPatterns(uiModel);
        return "books/list";
    }
}
