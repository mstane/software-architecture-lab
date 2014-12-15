package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookChangedEventHandler extends EventHandler{
  void onBookUpdated(BookChangedEvent event);
}
