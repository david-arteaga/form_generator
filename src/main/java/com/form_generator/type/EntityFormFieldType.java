package com.form_generator.type;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.PredefinedType;
import com.form_generator.form.field.FormField;
import com.form_generator.entity.Entity;
import com.form_generator.entity.EntityBean;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

/**
 * Created by david on 4/29/17.
 */
public class EntityFormFieldType implements FormFieldType {

    private final Entity entity;

    public EntityFormFieldType(FormEntity formEntityAnnotation) {
        this(new EntityBean(formEntityAnnotation));
    }

    public EntityFormFieldType(PredefinedType predefinedTypeAnnotation) {
        this(new EntityBean(predefinedTypeAnnotation));
    }

    public EntityFormFieldType(Entity entity) {
        this.entity = entity;
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

    @Override
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

    @Override
    public HtmlElement map(FormField formField, Mapping mapping) {
        FormFieldMapper<EntityFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this);
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
