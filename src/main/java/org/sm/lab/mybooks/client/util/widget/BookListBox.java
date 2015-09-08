package org.sm.lab.mybooks.client.util.widget;

import com.google.gwt.user.client.ui.Composite;

import org.sm.lab.mybooks.client.util.widget.SelectOneListBox.OptionFormatter;
import org.sm.lab.mybooks.shared.dto.BookDto;

public class BookListBox extends Composite {

	SelectOneListBox<BookDto> selectOneListBox = null;
		
	public BookListBox() {
		OptionFormatter<BookDto> versionFormatter =  new OptionFormatter<BookDto>()
				{
		    @Override
		    public String getLabel(BookDto option)
		    {
		        return option.getTitle();
		    }
		 
		    @Override
		    public String getValue(BookDto option)
		    {
		        return option.getId().toString();
		    }
		};
		
		
		selectOneListBox = new SelectOneListBox<>(versionFormatter);
		
		initWidget(selectOneListBox);

	}
	
	public SelectOneListBox<BookDto> getListBox() {
		return selectOneListBox;
	}

}
