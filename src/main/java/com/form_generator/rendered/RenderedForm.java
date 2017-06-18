package com.form_generator.rendered;

/**
 * Created by david on 6/17/17.
 */
public class RenderedForm {
    private final String name;
    private final RenderedHtmlElement renderedHtmlElement;

    public RenderedForm(String name, RenderedHtmlElement renderedHtmlElement) {
        this.name = name;
        this.renderedHtmlElement = renderedHtmlElement;
    }

    public String getName() {
        return name;
    }

    public RenderedHtmlElement getRenderedHtmlElement() {
        return renderedHtmlElement;
    }
}
