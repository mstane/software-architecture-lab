
package org.sm.lab.mybooks.client.activity;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.ClientFactory;
import org.sm.lab.mybooks.client.event.NoteChangedEvent;
import org.sm.lab.mybooks.client.event.NoteChangedEvent.Action;
import org.sm.lab.mybooks.client.place.NoteFormPlace;
import org.sm.lab.mybooks.client.util.AppAsyncCallback;
import org.sm.lab.mybooks.client.util.IAppDialogBox;
import org.sm.lab.mybooks.client.view.NoteFormView;
import org.sm.lab.mybooks.shared.action.CreateNoteAction;
import org.sm.lab.mybooks.shared.action.CreateNoteResult;
import org.sm.lab.mybooks.shared.action.DeleteNoteAction;
import org.sm.lab.mybooks.shared.action.DeleteNoteResult;
import org.sm.lab.mybooks.shared.action.GetNoteAction;
import org.sm.lab.mybooks.shared.action.GetNoteResult;
import org.sm.lab.mybooks.shared.action.UpdateNoteAction;
import org.sm.lab.mybooks.shared.action.UpdateNoteResult;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.validation.ClientGroup;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class NoteFormActivity extends AbstractActivity implements NoteFormView.Presenter {

    private NoteDto dto;

    private final DispatchAsync dispatchRpcService;
    private final EventBus eventBus;
    private final NoteFormView view;
    private final IAppDialogBox appDialogBox;
    private final PlaceController placeController;

    @Inject
    public NoteFormActivity(ClientFactory injector) {
        Log.debug("NoteFormActivity.NoteFormActivity()");

        this.dispatchRpcService = injector.getDispatchAsync();
        this.eventBus = injector.getEventBus();
        this.appDialogBox = injector.getAppDialogBox();
        this.placeController = injector.getPlaceController();

        this.view = injector.getNoteFormView();
        this.view.setPresenter(this);

    }

    public NoteDto getDto() {
        return this.dto;
    }

    public void bind() {
        if (dto != null) {
            setValues();
        }

    }

    @Override
    public void start(AcceptsOneWidget container, EventBus eventBus) {
        Log.debug("NoteFormActivity.start()");

        NoteFormPlace place = (NoteFormPlace) placeController.getWhere();

        dto = place.getNoteDto();

        view.setVisible(true);
        view.clear();
        if (dto.getId() != null && dto.getId() > 0) {
            fetchNoteDetails();
        }

        container.setWidget(view.asWidget());

    }

    @Override
    public void onUpdateButtonClicked() {
        doSave();
    }

    @Override
    public void onDeleteButtonClicked() {
        doDelete();
    }

    private void doSave() {
        
        getValues();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<NoteDto>> violations = validator.validate(dto, Default.class, ClientGroup.class);

        view.getErrorLabel().setText("");

        if (!violations.isEmpty()) {
            StringBuffer errorMessage = new StringBuffer();
            for (ConstraintViolation<NoteDto> constraintViolation : violations) {
                if (errorMessage.length() == 0) {
                    errorMessage.append('\n');
                }
                errorMessage.append(constraintViolation.getMessage());
            }
            view.getErrorLabel().setText(errorMessage.toString());
            return;
        }

        if (dto.getId() == null) {
            dispatchRpcService.execute(new CreateNoteAction(dto), new AppAsyncCallback<CreateNoteResult>(eventBus, appDialogBox) {
                public void onSuccess(CreateNoteResult result) {
                    Log.debug("CreateNoteAction -- onSuccess()");
                    dto = result.getDto();
                    eventBus.fireEvent(new NoteChangedEvent(Action.CREATED, dto));
                }
            });
        } else {
            dispatchRpcService.execute(new UpdateNoteAction(dto), new AppAsyncCallback<UpdateNoteResult>(eventBus, appDialogBox) {
                public void onSuccess(UpdateNoteResult result) {
                    Log.debug("UpdateNoteAction -- onSuccess()");
                    dto = result.getDto();
                    eventBus.fireEvent(new NoteChangedEvent(Action.UPDATED, dto));
                }
            });
        }

    }

    private void doDelete() {
        dispatchRpcService.execute(new DeleteNoteAction(dto), new AppAsyncCallback<DeleteNoteResult>(eventBus, appDialogBox) {
            public void onSuccess(DeleteNoteResult result) {
                Log.debug("DeleteNoteAction -- onSuccess()");
                eventBus.fireEvent(new NoteChangedEvent(Action.DELETED, dto));
                view.setVisible(false);
            }
        });
    }

    private void fetchNoteDetails() {
        dispatchRpcService.execute(new GetNoteAction(dto), new AppAsyncCallback<GetNoteResult>(eventBus, appDialogBox) {
            public void onSuccess(GetNoteResult result) {
                Log.debug("GetNoteAction -- onSuccess()");
                dto = result.getDto();
                bind();
            }
        });
    }
    
    public void setValues() {
        view.getNoteTitle().setValue(dto.getTitle());
        view.getContent().setValue(dto.getContent());
    }
    
    public void getValues() {
        dto.setTitle(view.getNoteTitle().getValue());
        dto.setContent(view.getContent().getValue());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

}
