package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class CreateNoteAction implements Action<CreateNoteResult> {

	private NoteDto dto;
	
    CreateNoteAction() {
    }
    
    public CreateNoteAction(NoteDto dto) {
    	this.dto = dto;
    }

    public NoteDto getDto() {
    	return this.dto;
    }
    
}