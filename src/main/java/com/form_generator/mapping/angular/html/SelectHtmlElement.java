package com.form_generator.mapping.angular.html;

import com.form_generator.html.HtmlElement;

/**
 * Created by david on 6/18/17.
 */
public class SelectHtmlElement extends HtmlElement implements Field<SelectHtmlElement> {
    public SelectHtmlElement() {
        super("select");
    }

    @Override
    public SelectHtmlElement addAttribute(String attribute, String value) {
        return (SelectHtmlElement) super.addAttribute(attribute, value);
    }

    @Override
    public SelectHtmlElement appendChild(HtmlElement child) {
        return (SelectHtmlElement) super.appendChild(child);
    }

    @Override
    public SelectHtmlElement get() {
        return this;
    }

}
