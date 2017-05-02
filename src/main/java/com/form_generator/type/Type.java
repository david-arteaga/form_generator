package com.form_generator.type;

import com.form_generator.exception.InvalidOperationException;
import com.form_generator.field.Field;
import com.form_generator.type.entity.Entity;

/**
 * Created by david on 4/29/17.
 */
public interface Type {
//    default boolean isBoolean() {return false;}
//    default boolean isString() {return false;}
//    default boolean isNumber() {return false;}
//    default boolean isOtherEntity() {return false;}
//    default boolean isList() {return false;}
    default Entity getEntity() {throw new InvalidOperationException("This type does not represent an entity");}

    default Type getListType() {throw new InvalidOperationException("This type does not represent an entity");}
    String renderField(Field field);
}
