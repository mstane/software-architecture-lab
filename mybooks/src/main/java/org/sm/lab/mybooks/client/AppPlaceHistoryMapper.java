package org.sm.lab.mybooks.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import org.sm.lab.mybooks.client.place.BookFormPlace;
import org.sm.lab.mybooks.client.place.BookListPlace;
import org.sm.lab.mybooks.client.place.LoginPlace;
import org.sm.lab.mybooks.client.place.NoteFormPlace;
import org.sm.lab.mybooks.client.place.ProfileFormPlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({ BookFormPlace.Tokenizer.class, BookListPlace.Tokenizer.class,
		NoteFormPlace.Tokenizer.class, LoginPlace.Tokenizer.class,
		ProfileFormPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
