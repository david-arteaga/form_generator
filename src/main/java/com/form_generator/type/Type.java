package com.form_generator.type;

import com.form_generator.exception.InvalidOperationException;
import com.form_generator.field.Field;
import com.form_generator.type.entity.Entity;

/**
 * Created by david on 4/29/17.
 */
public interface Type {
    default Entity getEntity() {throw new InvalidOperationException("This type does not represent an entity");}

    default Type getListType() {throw new InvalidOperationException("This type does not represent an entity");}
    String renderField(Field field);
}
