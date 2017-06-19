package com.form_generator.html;

import com.form_generator.rendered.RenderedHtmlElement;

import java.util.List;
import java.util.Map;

/**
 * Created by david on 6/17/17.
 */
public class TextNode extends HtmlElement {

    private String content;

    public TextNode(String content) {
        super("text");
        this.content = content;
    }

    @Override
    public String getTag() {
        return "text";
    }

    @Override
    public HtmlElement addAttribute(String attribute, String value) {
        throw new UnsupportedOperationException("This is a text node: " + content);
    }

    @Override
    public HtmlElement appendChild(HtmlElement child) {
        throw new UnsupportedOperationException("This is a text node: " + content);
    }

    @Override
    public RenderedHtmlElement render() {
        return new RenderedHtmlElement("", content);
    }
}
