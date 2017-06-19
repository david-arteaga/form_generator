package com.form_generator.html.thymeleaf;

import com.form_generator.html.HtmlElement;

/**
 * Created by david on 6/18/17.
 */
public interface Field extends Attributeable {
    HtmlElement get();
    default HtmlElement setFieldName(String name) {
        addAttribute("th:field", "*{" + name + "}");
        return get();
    }
}
