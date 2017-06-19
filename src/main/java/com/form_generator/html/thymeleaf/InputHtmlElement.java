package com.form_generator.html.thymeleaf;

import com.form_generator.html.HtmlElement;
import com.form_generator.rendered.RenderedHtmlElement;

/**
 * Created by david on 6/18/17.
 */
public class InputHtmlElement extends HtmlElement implements Field {
    public InputHtmlElement() {
        super("input");
    }

    @Override
    public RenderedHtmlElement render() {
        StringBuilder sb = new StringBuilder();

        // tagname
        sb.append('<').append(getTag()).append(' ');

        // attributes
        getAttributes().forEach((k, v) -> {
            sb.append(k).append("=\"").append(v).append("\" ");
        });

        // closing tag
        sb.append("/>");

        return new RenderedHtmlElement(getTag(), sb.toString());
    }

    @Override
    public InputHtmlElement get() {
        return this;
    }

    @Override
    public InputHtmlElement setFieldName(String name) {
        return addAttribute("th:field", "*{" + name + "}");
    }

    @Override
    public InputHtmlElement addAttribute(String attribute, String value) {
        return (InputHtmlElement) super.addAttribute(attribute, value);
    }

    @Override
    public HtmlElement appendChild(HtmlElement e) {
        throw new UnsupportedOperationException("Input tags cannot have children");
    }
}