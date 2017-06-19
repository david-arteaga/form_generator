package com.form_generator.html.thymeleaf;

import com.form_generator.html.HtmlElement;

/**
 * Created by david on 6/18/17.
 */
public class SelectHtmlElement extends HtmlElement implements Field {
    public SelectHtmlElement() {
        super("select");
    }

    @Override
    public HtmlElement get() {
        return this;
    }

}
