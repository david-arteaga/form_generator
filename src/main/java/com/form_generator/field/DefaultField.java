package com.form_generator.field;

import com.form_generator.type.Type;
import com.form_generator.type.FieldTypeUtils;
import lombok.Data;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

/**
 * Created by david on 4/29/17.
 */

@Data
public class DefaultField implements Field {
    private final String fieldSingularName;
    private final String fieldPluralName;
    private final Type type;

    private final String fieldSingularLabel;
    private final String fieldPluralLabel;

    public DefaultField(String fieldName, Type type) {
        fieldSingularName = fieldName;
        fieldPluralName = getPlural(fieldName);

        this.type = type;
        fieldSingularLabel = separateOnCaps(fieldName);
        fieldPluralLabel = separateOnCaps(fieldPluralName);
    }

    public DefaultField(VariableElement variableElement, ProcessingEnvironment processingEnvironment) {
        this(variableElement.getSimpleName().toString(), FieldTypeUtils.getDefault(variableElement, processingEnvironment));
    }

    private static String getPlural(String singular) {
        return singular.endsWith("s") ? singular : singular + "s";
    }

    private static String separateOnCaps(String string) {
        if (string.isEmpty()) return "";
        StringBuilder label = new StringBuilder();
        for (Character ch: string.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                label.append(' ');
                ch = Character.toLowerCase(ch);
            }
            label.append(ch);
        }
        label.setCharAt(0, Character.toUpperCase(label.charAt(0)));

        return label.toString();
    }
}
