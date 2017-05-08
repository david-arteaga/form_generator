package com.form_generator.type.base;

import com.form_generator.field.FormField;
import com.form_generator.type.FormType;
import com.form_generator.type.entity.Entity;

/**
 * Created by david on 4/29/17.
 */
public class ListFormType implements FormType {

    private final FormType listFormType;

    private static final String template =
            "<div class=\"panel panel-default\">\n" +
            "    <div class=\"panel-heading\">\n" +
            "        <h3 class=\"panel-title\">%s </h3></div>\n" + // [field singular label] (1)
            "    <div class=\"panel-body\">\n" +
            "        <div class=\"table-responsive\">\n" +
            "            <table class=\"table\">\n" +
            "                <thead>\n" +
            "                    <tr>\n" +
            "                        <th>%s </th>\n" + // [field singular label] (2)
            "                        <th> </th>\n" +
            "                    </tr>\n" +
            "                </thead>\n" +
            "                <tbody>\n" +
            "                    <!--<tr>\n" +
            "                        <td>Example added element </td>\n" +
            "                        <td>\n" +
            "                            <button class=\"close\"><span aria-hidden=\"true\">×</span></button>\n" +
            "                        </td>\n" +
            "                    </tr>-->\n" +
            "                </tbody>\n" +
            "            </table>\n" +
            "        </div>\n" +
            "        <form>\n" +
            "            <div class=\"form-group\">\n" +
            "                <select class=\"form-control\">\n" +
            "                        <option disabled selected>%s </option>\n" + // [field singular label] (3)
            "                        <option " +
                                        "th:each=\"%s: ${%s}\" " + // [field singular name]: ${[field plural name]} (4)
                                        "th:value=\"${%s.%s}\" " + // ${[field singular name].[list entity name field name]} (5)
                                        "th:text=\"\">option label " +
                                    "</option>\n" +
            "                </select>\n" +
            "            </div>\n" +
            "            <div class=\"form-group\">\n" +
            "                <button class=\"btn btn-default\" type=\"button\">Add</button>\n" +
            "            </div>\n" +
            "        </form>\n" +
            "    </div>\n" +
            "</div>";

    @Override
    public String renderField(FormField formField) {
        // TODO
        Entity fieldListEntity = formField.getFormType().getListFormType().getEntity();
        return String.format(template,
                formField.getFieldSingularLabel(), // (1)
                formField.getFieldSingularLabel(), // (2)
                formField.getFieldSingularLabel(), // (3)
                formField.getFieldSingularName(), formField.getFieldPluralName(), // (4)
                formField.getFieldSingularName(), fieldListEntity.getNameFieldName() // (5)
                );
    }

    public ListFormType(FormType listFormType) {
        this.listFormType = listFormType;
    }

    @Override
    public FormType getListFormType() {
        return listFormType;
    }
}
