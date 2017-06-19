package com.form_generator.type;

import com.form_generator.exception.InvalidOperationException;
import com.form_generator.form.field.FormField;
import com.form_generator.entity.Entity;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 4/29/17.
 */
public interface FormFieldType {
    default Entity getEntity() {
        throw new InvalidOperationException("This type, '" + this.getClass().getCanonicalName() + "', does not represent an entity");
    }

    default FormFieldType getListFormFieldType() {
        throw new InvalidOperationException("This type, '" + this.getClass().getCanonicalName() + "', does not represent a list");
    }

    HtmlElement map(FormField formField, Mapping mapping);
}
