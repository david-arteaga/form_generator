package com.form_generator.mapping.thymeleaf.mapper;

import com.form_generator.entity.Entity;
import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.TextNode;
import com.form_generator.html.thymeleaf.SelectHtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.type.EntityFormFieldType;

/**
 * Created by david on 6/17/17.
 */
public class EntityFormFieldMapper implements FormFieldMapper<EntityFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, EntityFormFieldType formFieldType) {
        Entity entity = formField.getFormFieldType().getEntity();
        String idFieldName = entity.getIdFieldName();

        HtmlElement formGroup = new HtmlElement("div")
                .addAttribute("class", "form-group");

        HtmlElement select = new SelectHtmlElement()
                .setFieldName(formField.getFieldSingularName())
                .addAttribute("class", "form-control");
        formGroup.appendChild(select);

        HtmlElement defaultOption = new HtmlElement("option")
                .addAttribute("selected", "")
                .addAttribute("disabled", "");
        defaultOption.appendChild(new TextNode(formField.getFieldSingularLabel()));
        select.appendChild(defaultOption);

        HtmlElement option = new HtmlElement("option")
                .addAttribute("th:each", String.format("%s: ${%s}", formField.getFieldSingularName(), formField.getFieldPluralName()))
                .addAttribute("th:value", String.format("${%s.%s}", formField.getFieldSingularName(), idFieldName))
                .addAttribute("th:text", String.format("${%s.%s}", formField.getFieldSingularName(), entity.getNameFieldName()));
        select.appendChild(option);

        return formGroup;
    }

    private final static String template =
            "<div class=\"form-group\">\n" +
            "    <select name=\"%s\" class=\"form-control\" >\n" +
            "        <option selected disabled>%s </option>\n" +
            "        <option " +
                        "th:each=\"%s: ${%s}\" " +
                        "th:value=\"${%s.%s}\" " +
                        "th:text=\"${%s.%s}\">option label" +
                        "</option>\n" +
            "    </select>\n" +
            "</div>";

    public String renderField(FormField formField) {
        Entity entity = formField.getFormFieldType().getEntity();
        String idFieldName = entity.getIdFieldName();
        return String.format(template,
                formField.getFieldSingularName(),
                formField.getFieldSingularLabel(),

                formField.getFieldSingularName(), formField.getFieldPluralName(),
                formField.getFieldSingularName(), idFieldName,
                formField.getFieldSingularName(), entity.getNameFieldName());
    }

}
