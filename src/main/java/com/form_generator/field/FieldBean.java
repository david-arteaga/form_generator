package com.form_generator.field;

import com.form_generator.type.Type;
import lombok.Data;

/**
 * Created by david on 4/29/17.
 */
@Data
public class FieldBean implements Field {
    private final String fieldSingularName;
    private final String fieldPluralName;
    private final Type type;

    private final String fieldSingularLabel;
    private final String fieldPluralLabel;
}
