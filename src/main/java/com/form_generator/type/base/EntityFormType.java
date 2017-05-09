package com.form_generator.type.base;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.PredefinedType;
import com.form_generator.field.FormField;
import com.form_generator.type.FormType;
import com.form_generator.type.entity.Entity;
import com.form_generator.type.entity.EntityBean;

/**
 * Created by david on 4/29/17.
 */
public class EntityFormType implements FormType {

    private final Entity entity;

    public EntityFormType(FormEntity formEntityAnnotation) {
        this(new EntityBean(formEntityAnnotation));
    }

    public EntityFormType(PredefinedType predefinedTypeAnnotation) {
        this(new EntityBean(predefinedTypeAnnotation));
    }

    public EntityFormType(Entity entity) {
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
        Entity entity = formField.getFormType().getEntity();
        String idFieldName = entity.getIdFieldName();
        return String.format(template,
                formField.getFieldSingularName(),
                formField.getFieldSingularLabel(),
                formField.getFieldSingularName(), formField.getFieldPluralName(),
                formField.getFieldSingularName(), idFieldName,
                formField.getFieldSingularName(), entity.getNameFieldName());
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
