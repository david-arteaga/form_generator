package com.form_generator.type.base;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.PredefinedType;
import com.form_generator.field.Field;
import com.form_generator.type.FormType;
import com.form_generator.type.entity.Entity;
import com.form_generator.type.entity.EntityBean;

/**
 * Created by david on 4/29/17.
 */
public class EntityFormType implements FormType {

    private final Entity entity;

    public EntityFormType(FormEntity formEntityAnnotation) {
        // TODO refactor unnecessary double constructors
        this(new EntityBean(formEntityAnnotation));
    }

    public EntityFormType(PredefinedType predefinedTypeAnnotation) {
        // TODO refactor unnecessary double constructors
        this(new EntityBean(predefinedTypeAnnotation));
    }

    public EntityFormType(Entity entity) {
        this.entity = entity;
    }

    private final static String template =
            "<div class=\"form-group\">\n" +
            "    <select name=\"%s%s\" class=\"form-control\" >\n" +
            "        <option selected disabled>%s </option>\n" +
            "        <option " +
                        "th:each=\"%s: ${%s}\" " +
                        "th:value=\"${%s.%s}\" " +
                        "th:text=\"${%s.%s}\">option label" +
                    "</option>\n" +
            "    </select>\n" +
            "</div>";

    @Override
    public String renderField(Field field) {
        Entity entity = field.getFormType().getEntity();
        String idFieldName = entity.getIdFieldName();
        String capsIdFieldName = idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
        return String.format(template,
                field.getFieldSingularName(), capsIdFieldName,
                field.getFieldSingularLabel(),
                field.getFieldSingularName(), field.getFieldPluralName(),
                field.getFieldSingularName(), idFieldName,
                field.getFieldSingularName(), entity.getNameFieldName());
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
