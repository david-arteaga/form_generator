package com.form_generator.rendered;

/**
 * Created by david on 6/17/17.
 */
public class RenderedHtmlElement {
    private final String tag;
    private final String html;

    public String getTag() {
        return tag;
    }

    public String getHtml() {
        return html;
    }

    public RenderedHtmlElement(String tag, String html) {
        assert tag != null;
        assert html != null;
        this.tag = tag;
        this.html = html;
    }
}
