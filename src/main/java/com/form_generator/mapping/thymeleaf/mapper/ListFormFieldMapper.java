package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.entity.Entity;
import com.form_generator.entity.EntityBean;
import com.form_generator.exception.InvalidOperationException;
import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.TextNode;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.EntityFormFieldType;
import com.form_generator.type.FormFieldType;
import com.form_generator.type.ListFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class ListFormFieldMapper implements FormFieldMapper<ListFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, ListFormFieldType formFieldType) {
        //TODO

        HtmlElement panel = new HtmlElement("panel")
                .addAttribute("class", "panel panel-default");

        // panel heading
        HtmlElement panelHeading = new HtmlElement("div")
                .addAttribute("class", "panel-heading");
        panel.appendChild(panelHeading);

        // panel heading
        HtmlElement title = new HtmlElement("h3")
                .addAttribute("class", "panel-title")
                .appendChild(new TextNode(formField.getFieldSingularLabel()));
        panelHeading.appendChild(title);

        // panel body
        HtmlElement panelBody = new HtmlElement("div")
                .addAttribute("class", "panel-body");
        panel.appendChild(panelBody);

        // panel body
        // table
        HtmlElement table = new HtmlElement("table")
                .addAttribute("class", "table");

        // table head
        HtmlElement tableHead = new HtmlElement("thead");
        HtmlElement headRow = new HtmlElement("tr");
        HtmlElement th = new HtmlElement("th").appendChild(new TextNode(formField.getFieldSingularLabel()));
        headRow.appendChild(th).appendChild(new HtmlElement("th"));
        tableHead.appendChild(headRow);
        table.appendChild(tableHead);

        // table body
        table.appendChild(new HtmlElement("tbody"));

        panelBody.appendChild(new HtmlElement("div").addAttribute("class", "table-responsive").appendChild(table));

        // form
        Entity fieldListEntity;
        try {
            fieldListEntity = formFieldType.getListFormFieldType().getEntity();
        } catch (InvalidOperationException ex) {
            fieldListEntity = new EntityBean("id", "name");
        }
        HtmlElement form = new HtmlElement("form")
                .appendChild(new EntityFormFieldMapper().mapField(formField, new EntityFormFieldType(fieldListEntity), false));

        panelBody.appendChild(form);

        return panel;
    }

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
                                        "th:value=\"${%s.%s}\" " + // ${[field singular name].[list entity id field name]} (5)
                                        "th:text=\"${%s.%s}\">option label " + // ${[field singular name].[list entity name field name]} (6)
                                    "</option>\n" +
            "                </select>\n" +
            "            </div>\n" +
            "            <div class=\"form-group\">\n" +
            "                <button class=\"btn btn-default\" type=\"button\">Add</button>\n" +
            "            </div>\n" +
            "        </form>\n" +
            "    </div>\n" +
            "</div>";

    public String renderField(FormField formField) {
        // TODO rendered correctly fields that are not entities
        Entity fieldListEntity;
        try {
            fieldListEntity = formField.getFormFieldType().getListFormFieldType().getEntity();
        } catch (InvalidOperationException ex) {
            fieldListEntity = new EntityBean("id", "name");
        }

        return String.format(template,
                formField.getFieldSingularLabel(), // (1)
                formField.getFieldSingularLabel(), // (2)
                formField.getFieldSingularLabel(), // (3)
                formField.getFieldSingularName(), formField.getFieldPluralName(), // (4)
                formField.getFieldSingularName(), fieldListEntity.getIdFieldName(), // (5)
                formField.getFieldSingularName(), fieldListEntity.getNameFieldName()
        );
    }

}
