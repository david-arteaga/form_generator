package com.form_generator.form.field;

import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.Mapping;
import com.form_generator.type.FormFieldType;

import javax.lang.model.element.Element;

/**
 * Created by david on 4/29/17.
 */

public class DefaultFormField implements FormField {
    private final String fieldSingularName;
    private final String fieldPluralName;
    private final FormFieldType formFieldType;

    private final String fieldSingularLabel;
    private final String fieldPluralLabel;

    private DefaultFormField(String fieldName, FormFieldType formFieldType) {
        if (fieldName.endsWith("s")) {
            fieldName = fieldName.substring(0, fieldName.length() - 1);
            if (fieldName.endsWith("ie")) {
                fieldName = fieldName.substring(0, fieldName.length() - 2) + "y";
            }
        }
        fieldSingularName = fieldName;
        fieldPluralName = getPlural(fieldName);
        fieldPluralLabel = separateOnCaps(fieldPluralName);

        this.formFieldType = formFieldType;
        fieldSingularLabel = separateOnCaps(fieldName);
    }

    public DefaultFormField(Element element, FormFieldType formFieldType) {
        this(element.getSimpleName().toString(), formFieldType);
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
    public FormFieldType getFormFieldType() {
        return formFieldType;
    }

    @Override
    public String getFieldSingularLabel() {
        return fieldSingularLabel;
    }

    @Override
    public String getFieldPluralLabel() {
        return fieldPluralLabel;
    }

    @Override
    public HtmlElement map(Mapping mapping, String formName) {
        return formFieldType.map(this, mapping, formName);
    }
}
