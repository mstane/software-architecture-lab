package org.sm.lab.mybooks.client.activity;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.ClientFactory;
import org.sm.lab.mybooks.client.event.ReaderChangedEvent;
import org.sm.lab.mybooks.client.event.ReaderChangedEvent.Action;
import org.sm.lab.mybooks.client.util.AppAsyncCallback;
import org.sm.lab.mybooks.client.util.IAppDialogBox;
import org.sm.lab.mybooks.client.view.ProfileFormView;
import org.sm.lab.mybooks.shared.action.CreateReaderAction;
import org.sm.lab.mybooks.shared.action.CreateReaderResult;
import org.sm.lab.mybooks.shared.action.DeleteReaderAction;
import org.sm.lab.mybooks.shared.action.DeleteReaderResult;
import org.sm.lab.mybooks.shared.action.GetReaderAction;
import org.sm.lab.mybooks.shared.action.GetReaderResult;
import org.sm.lab.mybooks.shared.action.UpdateReaderAction;
import org.sm.lab.mybooks.shared.action.UpdateReaderResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;
import org.sm.lab.mybooks.shared.validation.ClientGroup;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class ProfileFormActivity extends AbstractActivity implements ProfileFormView.Presenter {
	
	private ReaderDto dto;

	private final DispatchAsync dispatchRpcService;
	private final EventBus eventBus;

	private final ProfileFormView view;
	private final IAppDialogBox appDialogBox;
	private final PlaceController placeController;
	
	@Inject
	public ProfileFormActivity(ClientFactory injector) {
	    Log.debug("ProfileFormActivity.ProfileFormActivity()");
	    
        this.dispatchRpcService = injector.getDispatchAsync();
        this.eventBus = injector.getEventBus();
        this.appDialogBox = injector.getAppDialogBox();
        this.placeController = injector.getPlaceController();

        this.view = injector.getProfileFormView();
        this.view.setPresenter(this);

	}
	
	public ReaderDto getDto() {
		return this.dto;
	}
	
	public void bind() {
		if (dto != null) {
			view.getUsername().setValue(dto.getUsername());			
			view.getPassword().setValue(dto.getPassword());
			view.getFirstName().setValue(dto.getFirstName());
			view.getLastName().setValue(dto.getLastName());
			view.getEmail().setValue(dto.getEmail());
		}

	}
	
	


    @Override
    public void start(AcceptsOneWidget container, EventBus eventBus) {
        Log.debug("ProfileFormActivity.start()");
        
		view.clear();
		
		fetchProfileDetails();
		
		container.setWidget(view.asWidget());
		
	}
	
	@Override
	public void onSaveButtonClicked() {
		doSave();
	}

	@Override
	public void onDeleteButtonClicked() {
		doDelete();
	}

	private void doSave() {
		dto.setUsername(view.getUsername().getValue());
		dto.setPassword(view.getPassword().getValue());
		dto.setFirstName(view.getFirstName().getValue());
		dto.setLastName(view.getLastName().getValue());
		dto.setEmail(view.getEmail().getValue());
		
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ReaderDto>> violations = validator.validate(dto, Default.class, ClientGroup.class);
        
        view.getErrorLabel().setText("");
        
        if (!violations.isEmpty()) {
            StringBuffer errorMessage = new StringBuffer();
            for (ConstraintViolation<ReaderDto> constraintViolation : violations) {
                if (errorMessage.length() == 0) {
                    errorMessage.append('\n');
                }
                errorMessage.append(constraintViolation.getMessage());
            }
            view.getErrorLabel().setText(errorMessage.toString());
            return;
        }
		
		if (dto.getId() == null) {
			dispatchRpcService.execute(new CreateReaderAction(dto), new AppAsyncCallback<CreateReaderResult>(eventBus, appDialogBox) {
	            public void onSuccess(CreateReaderResult result) {
	            	Log.debug("CreateReaderAction -- onSuccess()");
	            	dto = result.getDto();
					eventBus.fireEvent(new ReaderChangedEvent(Action.CREATED, dto));
	            }
	        });
		} else {
			dispatchRpcService.execute(new UpdateReaderAction(dto), new AppAsyncCallback<UpdateReaderResult>(eventBus, appDialogBox) {
	            public void onSuccess(UpdateReaderResult result) {
	            	Log.debug("UpdateReaderAction -- onSuccess()");
	            	dto = result.getDto();
	            	eventBus.fireEvent(new ReaderChangedEvent(Action.UPDATED, dto));
	            }
	        });
		}
		
	}

	private void doDelete() {		
		dispatchRpcService.execute(new DeleteReaderAction(dto), new AppAsyncCallback<DeleteReaderResult>(eventBus, appDialogBox) {
            public void onSuccess(DeleteReaderResult result) {
            	Log.debug("DeleteReaderAction -- onSuccess()");
            	eventBus.fireEvent(new ReaderChangedEvent(Action.DELETED, dto));
            }
        });
	}
	
	private void fetchProfileDetails() {
		dispatchRpcService.execute(new GetReaderAction(dto) , new AppAsyncCallback<GetReaderResult>(eventBus, appDialogBox) {
            public void onSuccess(GetReaderResult result) {
            	Log.debug("GetReaderAction -- onSuccess()");
            	dto = result.getDto();
            	bind();
            }
        });
	}


}
