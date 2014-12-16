package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ReaderChangedEventHandler extends EventHandler{
  void onReaderUpdated(ReaderChangedEvent event);
}
