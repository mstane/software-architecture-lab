package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class DeleteNoteAction implements Action<DeleteNoteResult> {

	private NoteDto dto;
	
    DeleteNoteAction() {
    }
    
    public DeleteNoteAction(NoteDto dto) {
    	this.dto = dto;
    }

    public NoteDto getDto() {
    	return this.dto;
    }
    
}