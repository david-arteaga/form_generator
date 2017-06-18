package com.form_generator.form;

import com.form_generator.form.field.FormField;
import com.form_generator.html.FormHtmlElement;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by david on 6/17/17.
 */
public class Form {
    private final String name;
    private final List<FormField> fields;

    public Form(String name, List<FormField> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public List<FormField> getFields() {
        return fields;
    }

    public FormHtmlElement map(Mapping mapping) {
        List<HtmlElement> children = fields.stream()
                .map(f -> f.map(mapping))
                .collect(Collectors.toList());

        FormHtmlElement formHtmlElement = new FormHtmlElement(name, children);
        mapping.completeFormElement(formHtmlElement);

        return formHtmlElement;
    }
}
