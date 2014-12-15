package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface NoteChangedEventHandler extends EventHandler{
  void onNoteUpdated(NoteChangedEvent event);
}
