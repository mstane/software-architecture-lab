package org.sm.lab.mybooks.client.ui.phone.view;

import java.util.Iterator;

import org.sm.lab.mybooks.client.MyBooks;
import org.sm.lab.mybooks.client.view.BookFormView;
import org.sm.lab.mybooks.client.view.BookListView;
import org.sm.lab.mybooks.client.view.NoteFormView;
import org.sm.lab.mybooks.shared.dto.BookDto;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


public class BookListViewImpl extends Composite implements BookListView {

	@UiTemplate("BookListViewImpl.ui.xml")
	interface Binder extends UiBinder<Widget, BookListViewImpl> {
	}
	
	static class BookCell extends AbstractCell<BookDto> {
		private final String imageHtml;

		public BookCell(ImageResource image) {
			this.imageHtml = AbstractImagePrototype.create(image).getHTML();
		}

		@Override
		public void render(Context context, BookDto value, SafeHtmlBuilder sb) {
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<table>");

			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendHtmlConstant(imageHtml);
			sb.appendHtmlConstant("</td>");

			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getTitle());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getAuthor());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

//	@UiField
	CellList<BookDto> cellList;
	
	private SingleSelectionModel<BookDto> selectionModel;
	private Presenter presenter;
	private Widget widget;

	public BookListViewImpl() {
	    Log.debug("BookListViewImpl.BookListViewImpl()");
		widget = onInitialize();
	}
	
	public static final ProvidesKey<BookDto> KEY_PROVIDER = new ProvidesKey<BookDto>() {
		@Override
		public Object getKey(BookDto item) {
			return item == null ? null : item.getId();
		}
	};
	
	public Widget onInitialize() {

		// Create a CellList.
		BookCell bookCell = new BookCell(MyBooks.images.book());

		cellList = new CellList<BookDto>(bookCell, KEY_PROVIDER);
		cellList.setPageSize(30);
		cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		selectionModel = new SingleSelectionModel<BookDto>(KEY_PROVIDER);
		cellList.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						presenter.onItemClicked(selectionModel.getSelectedObject());
					}
				});
		
		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);

		return widget;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	public Widget asWidget() {
	    Log.debug("BookListViewImpl.asWidget()");
		return widget;
	}

	@Override
	public HasData<BookDto> getCellList() {
		return cellList;
	}

    @Override
    public void setSelected(BookDto dto) {
        if (dto == null) {
            selectionModel.clear();
        } else {
            selectionModel.setSelected(dto, true);
        }
        
    }

	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(IsWidget w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(IsWidget w) {
		// TODO Auto-generated method stub
		return false;
	}





}
