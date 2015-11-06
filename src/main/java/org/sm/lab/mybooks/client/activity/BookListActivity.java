package org.sm.lab.mybooks.client.activity;

import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.ClientFactory;
import org.sm.lab.mybooks.client.event.BookChangedEvent;
import org.sm.lab.mybooks.client.event.BookChangedEvent.Action;
import org.sm.lab.mybooks.client.event.BookChangedEventHandler;
import org.sm.lab.mybooks.client.place.BookFormPlace;
import org.sm.lab.mybooks.client.util.AppAsyncCallback;
import org.sm.lab.mybooks.client.util.IAppDialogBox;
import org.sm.lab.mybooks.client.view.BookFormView;
import org.sm.lab.mybooks.client.view.BookListView;
import org.sm.lab.mybooks.client.view.NoteFormView;
import org.sm.lab.mybooks.shared.action.LoadAllBooksAction;
import org.sm.lab.mybooks.shared.action.LoadAllBooksResult;
import org.sm.lab.mybooks.shared.dto.BookDto;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

public class BookListActivity extends AbstractActivity implements BookListView.Presenter, BookChangedEventHandler {

	private ListDataProvider<BookDto> listDataProvider = new ListDataProvider<BookDto>();
	private final DispatchAsync dispatchRpcService;
	private final EventBus eventBus;
	private final BookListView view;
	private final IAppDialogBox appDialogBox;
	private final PlaceController placeController;

	@Inject
	public BookListActivity(ClientFactory injector) {
	    Log.debug("BookListActivity.BookListActivity()");
	    
	    this.dispatchRpcService = injector.getDispatchAsync();
		this.eventBus = injector.getEventBus();
		this.appDialogBox = injector.getAppDialogBox();
		this.placeController = injector.getPlaceController();
		
        this.view = injector.getBookListView();
        this.view.setPresenter(this);
        
		listDataProvider.addDataDisplay(view.getCellList());
		
		eventBus.addHandler(BookChangedEvent.TYPE, this);
		
	}
	
    public void onAddButtonClicked() {
        Log.debug("BookListActivity.onAddButtonClicked()");
        
        placeController.goTo(new BookFormPlace());
    }

    public void onItemClicked(BookDto dto) {
        Log.debug("BookListActivity.onItemClicked()");
        
        placeController.goTo(new BookFormPlace(dto));
    }


    @Override
    public void start(AcceptsOneWidget container, EventBus eventBus) {
        Log.debug("BookListActivity.start()");
        
        view.setSelected(null);
        container.setWidget(view.asWidget());
        
        fetchBookDetails();    
    }

	private void fetchBookDetails() {
		dispatchRpcService.execute(new LoadAllBooksAction(), new AppAsyncCallback<LoadAllBooksResult>(eventBus, appDialogBox) {
            public void onSuccess(LoadAllBooksResult result) {
            	Log.debug("LoadAllBooksAction -- onSuccess()");
				listDataProvider.setList(result.getDtos());
				listDataProvider.refresh();
            }
        });

	}	
	
	@Override
	public void onBookUpdated(BookChangedEvent event) {
		Action action = event.getAction();
		BookDto dto = event.getChangedBook();

		if (action == Action.CREATED) {
			addItemInTable(dto);
		} else if (action == Action.UPDATED) {
			refreshTable();
		} else if (action == Action.DELETED) {
			removeItemFromTable(dto);
		}
		
	}
	
	private void addItemInTable(BookDto bookDto) {
		List<BookDto> books = listDataProvider.getList();

		books.remove(bookDto);
		books.add(bookDto);

		view.setSelected(bookDto);
		
		refreshTable();
		
	}

	private void removeItemFromTable(BookDto bookDto) {
		List<BookDto> books = listDataProvider.getList();
		books.remove(bookDto);

		refreshTable();
	}

	private void refreshTable() {
		listDataProvider.refresh();
	}
	
	@Override
	public String mayStop() {
		Log.debug("BookListActivity.mayStop()");
		return null;
	}

	@Override
	public void onCancel() {
		Log.debug("BookListActivity.onCancel()");
		
	}

	@Override
	public void onStop() {
		Log.debug("BookListActivity.onStop()");
		
	}
	

}
