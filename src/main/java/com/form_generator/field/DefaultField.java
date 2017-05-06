package com.form_generator.field;

import com.form_generator.type.FormType;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

/**
 * Created by david on 4/29/17.
 */

public class DefaultField implements Field {
    private final String fieldSingularName;
    private final String fieldPluralName;
    private final FormType formType;

    private final String fieldSingularLabel;
    private final String fieldPluralLabel;

    private DefaultField(String fieldName, FormType formType) {
        fieldSingularName = fieldName;
        fieldPluralName = getPlural(fieldName);

        this.formType = formType;
        fieldSingularLabel = separateOnCaps(fieldName);
        fieldPluralLabel = separateOnCaps(fieldPluralName);
    }

    public DefaultField(VariableElement variableElement, ProcessingEnvironment processingEnvironment) {
        this(variableElement.getSimpleName().toString(), ElementTypeUtils.getDefault(variableElement, processingEnvironment));
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

    @Override
    public String getFieldSingularName() {
        return fieldSingularName;
    }

    @Override
    public String getFieldPluralName() {
        return fieldPluralName;
    }

    @Override
    public FormType getFormType() {
        return formType;
    }

    @Override
    public String getFieldSingularLabel() {
        return fieldSingularLabel;
    }

    @Override
    public String getFieldPluralLabel() {
        return fieldPluralLabel;
    }
}
