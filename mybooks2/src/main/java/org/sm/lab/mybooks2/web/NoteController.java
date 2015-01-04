package org.sm.lab.mybooks2.web;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.sm.lab.mybooks2.domain.Note;
import org.sm.lab.mybooks2.service.BookService;
import org.sm.lab.mybooks2.service.NoteService;
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

@RequestMapping("/notes")
@Controller
public class NoteController {

	@Autowired
    NoteService noteService;

	@Autowired
    BookService bookService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Note note, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, note);
            return "notes/create";
        }
        uiModel.asMap().clear();
        noteService.saveNote(note);
        return "redirect:/notes/" + encodeUrlPathSegment(note.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Note());
        return "notes/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("note", noteService.findNote(id));
        uiModel.addAttribute("itemId", id);
        return "notes/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("notes", noteService.findNoteEntries(firstResult, sizeNo));
            float nrOfPages = (float) noteService.countAllNotes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("notes", noteService.findAllNotes());
        }
        addDateTimeFormatPatterns(uiModel);
        return "notes/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Note note, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, note);
            return "notes/update";
        }
        uiModel.asMap().clear();
        noteService.updateNote(note);
        return "redirect:/notes/" + encodeUrlPathSegment(note.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") String id, Model uiModel) {
        populateEditForm(uiModel, noteService.findNote(id));
        return "notes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") String id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Note note = noteService.findNote(id);
        noteService.deleteNote(note);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/notes";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("note_createdtime_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("note_modifiedtime_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Note note) {
        uiModel.addAttribute("note", note);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("books", bookService.findAllBooks());
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
}
