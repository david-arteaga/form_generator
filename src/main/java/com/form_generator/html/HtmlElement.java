package com.form_generator.html;

import com.form_generator.rendered.RenderedHtmlElement;

import java.util.*;

/**
 * Created by david on 6/17/17.
 */
public class HtmlElement implements Attributeable {
    private final String tag;
    private final Map<String, String> attributes;
    private final List<HtmlElement> children;
    private final Set<String> directives;

    public HtmlElement(String tag) {
        this.tag = tag;
        this.attributes = new LinkedHashMap<>();
        this.children = new ArrayList<>();
        this.directives = new HashSet<>();
    }

    public String getTag() {
        return tag;
    }

    public String getAttribute(String attribute) {
        return attributes.get(attribute);
    }

    @Override
    public HtmlElement addAttribute(String attribute, String value) {
        attributes.put(attribute, value);
        return this;
    }

    public HtmlElement appendChild(HtmlElement child) {
        children.add(child);
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<HtmlElement> getChildren() {
        return children;
    }

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
        sb.append(">\n");

        // children
        getChildren().forEach(child -> {
            sb.append(child.render().getHtml()).append('\n');
        });

        // closing tag
        sb.append("</").append(getTag()).append(">");

        return new RenderedHtmlElement(getTag(), sb.toString());
    }

    public Set<String> getDirectives() {
        return directives;
    }

    public void addDirective(String directive) {
        this.directives.add(directive);
    }
}
