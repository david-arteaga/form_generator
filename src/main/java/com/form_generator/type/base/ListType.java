package com.form_generator.type.base;

import com.form_generator.field.Field;
import com.form_generator.type.Type;
import lombok.Data;

/**
 * Created by david on 4/29/17.
 */
@Data
public class ListType implements Type {

    private final Type listType;

    @Override
    public String renderField(Field field) {
        // TODO
        return "";
    }
}
