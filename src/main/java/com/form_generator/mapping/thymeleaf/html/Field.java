package com.form_generator.mapping.thymeleaf.html;

import com.form_generator.html.Attributeable;
import com.form_generator.html.HtmlElement;

/**
 * Created by david on 6/18/17.
 */
public interface Field<T extends HtmlElement> extends Attributeable {
    public static final String FIELD_ATTRIBUTE_NAME = "th:field";
    T get();
    default T setFieldName(String name) {
        addAttribute(FIELD_ATTRIBUTE_NAME, "*{" + name + "}");
        return get();
    }
}
