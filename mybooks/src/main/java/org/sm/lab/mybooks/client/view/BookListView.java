package org.sm.lab.mybooks.client.view;

import org.sm.lab.mybooks.shared.dto.BookDto;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

public interface BookListView extends IsWidget, HasWidgets.ForIsWidget {

  public interface Presenter {
    void onAddButtonClicked();
    void onItemClicked(BookDto bookDto);
  }
  
  HasData<BookDto> getCellList();
  
  void setPresenter(Presenter presenter);
  void setSelected(BookDto dto);

}
