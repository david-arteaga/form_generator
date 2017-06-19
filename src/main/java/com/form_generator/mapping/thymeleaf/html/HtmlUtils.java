package com.form_generator.mapping.thymeleaf.html;

import com.form_generator.html.HtmlElement;

import java.util.Map;

/**
 * Created by david on 6/19/17.
 */
public class HtmlUtils {
    public static void removeThymeleafFields(HtmlElement element) {
        element.getAttributes().remove(Field.FIELD_ATTRIBUTE_NAME);
        element.getChildren().forEach(HtmlUtils::removeThymeleafFields);
    }
}
