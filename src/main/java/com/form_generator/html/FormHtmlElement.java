package com.form_generator.html;

import com.form_generator.rendered.RenderedForm;

import java.util.List;

/**
 * Created by david on 6/17/17.
 */
public class FormHtmlElement extends HtmlElement {

    private final String name;

    public FormHtmlElement(String name, List<HtmlElement> children) {
        super("form");
        this.name = !name.isEmpty() ? name : "_____";
        children.forEach(super::appendChild);
    }

    public String varName() {
        return name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
    }

    public String getName() {
        return name;
    }

    public RenderedForm renderFormHtmlElement() {
        return new RenderedForm(this.getName(), render());
    }
}
