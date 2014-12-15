
package org.sm.lab.mybooks.client.validation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.validation.ClientGroup;

import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * {@link AbstractGwtValidatorFactory} that creates the specified {@link GwtValidator}.
 */
public final class AppValidationFactory extends AbstractGwtValidatorFactory {

    /**
     * Validator marker for the project. Only the classes listed in the {@link GwtValidation} annotation can be validated.
     */
    @GwtValidation(value = {NoteDto.class, BookDto.class,}, 
            groups = {Default.class, ClientGroup.class}
    )
    public interface GwtValidator extends Validator {
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(GwtValidator.class);
    }

}
