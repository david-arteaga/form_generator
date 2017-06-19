package com.form_generator.html.thymeleaf;

import com.form_generator.html.HtmlElement;

/**
 * Created by david on 6/18/17.
 */
public interface Attributeable {
    HtmlElement addAttribute(String attribute, String value);
}
