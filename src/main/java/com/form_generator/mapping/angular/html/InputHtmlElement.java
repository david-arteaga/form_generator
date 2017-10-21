package com.form_generator.mapping.angular.html;

import com.form_generator.html.HtmlElement;
import com.form_generator.rendered.RenderedHtmlElement;

/**
 * Created by david on 6/18/17.
 */
public class InputHtmlElement extends HtmlElement implements Field<InputHtmlElement> {
    public InputHtmlElement() {
        super("input");
        addDirective("matInput");
    }

    @Override
    public RenderedHtmlElement render() {
        StringBuilder sb = new StringBuilder();

        // tagname
        sb.append('<').append(getTag()).append(' ');

        // directives
        getDirectives().forEach((directive) -> sb.append(directive).append(' '));

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
    public InputHtmlElement addAttribute(String attribute, String value) {
        return (InputHtmlElement) super.addAttribute(attribute, value);
    }

    @Override
    public HtmlElement appendChild(HtmlElement e) {
        throw new UnsupportedOperationException("Input tags cannot have children");
    }
}
