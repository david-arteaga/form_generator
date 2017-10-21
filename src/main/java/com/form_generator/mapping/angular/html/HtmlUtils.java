package com.form_generator.mapping.angular.html;

import com.form_generator.html.HtmlElement;

/**
 * Created by david on 6/19/17.
 */
public class HtmlUtils {
    public static void removeAngularFields(HtmlElement element) {
        element.getAttributes().remove(Field.FIELD_ATTRIBUTE_NAME);
        element.getChildren().forEach(HtmlUtils::removeAngularFields);
    }
}
