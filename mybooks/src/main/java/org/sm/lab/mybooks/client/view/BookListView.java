package org.sm.lab.mybooks.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

import org.sm.lab.mybooks.shared.dto.BookDto;

public interface BookListView extends IsWidget {

  public interface Presenter {
    void onAddButtonClicked();
    void onItemClicked(BookDto bookDto);
  }
  
  HasData<BookDto> getCellList();
  
  BookFormView getBookFormView();
  NoteFormView getNoteForm();
  void setPresenter(Presenter presenter);
  void setSelected(BookDto dto);

}
