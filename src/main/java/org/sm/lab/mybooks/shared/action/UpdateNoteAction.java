package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class UpdateNoteAction implements Action<UpdateNoteResult> {

	private NoteDto dto;
	
    UpdateNoteAction() {
    }
    
    public UpdateNoteAction(NoteDto dto) {
    	this.dto = dto;
    }

    public NoteDto getDto() {
    	return this.dto;
    }
    
}