package com.form_generator.render;

import com.form_generator.field.Field;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by david on 4/29/17.
 */
public class Render {

    public static String formWithFields(List<Field> fields) {
        List<String> fieldsHtml = fields.stream()
                .map(field -> field.getFormType().renderField(field))
                .collect(Collectors.toList());
        return surroundWithThymeleafForm(String.join("\n", fieldsHtml));
    }

    private static String surroundWithThymeleafForm(String string) {
        return "<form class=\"form\" th:action=\"post\">\n" + string + "\n</form>";
    }

}
