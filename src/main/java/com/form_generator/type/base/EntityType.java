package com.form_generator.type.base;

import com.form_generator.field.Field;
import com.form_generator.type.entity.Entity;
import com.form_generator.type.Type;
import com.form_generator.type.entity.EntityBean;
import lombok.Data;

/**
 * Created by david on 4/29/17.
 */
@Data
public class EntityType implements Type {

    private final Entity entity;

    public EntityType(com.form_generator.annotation.Entity entityAnnotation) {
        // TODO refactor unnecessary double constructors
        this(new EntityBean(entityAnnotation));
    }

    public EntityType(com.form_generator.annotation.DefinedType definedTypeAnnotation) {
        // TODO refactor unnecessary double constructors
        this(new EntityBean(definedTypeAnnotation));
    }

    public EntityType(Entity entity) {
        this.entity = entity;
    }

    private final static String template =
            "<div class=\"form-group\">\n" +
            "    <select name=\"%s%s\" class=\"form-control\">\n" +
            "        <option selected disabled>Select a %s</option>\n" +
            "        <option " +
                        "th:each=\"%s: ${%s}\" " +
                        "th:value=\"${%s.%s}\" " +
                        "th:text=\"${%s.%s}\">option label" +
                    "</option>\n" +
            "    </select>\n" +
            "</div>";

    @Override
    public String renderField(Field field) {
        Entity entity = field.getType().getEntity();
        String idFieldName = entity.getIdFieldName();
        String capsIdFieldName = idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
        return String.format(template,
                field.getFieldSingularName(), capsIdFieldName,
                field.getFieldSingularLabel(),
                field.getFieldSingularName(), field.getFieldPluralName(),
                field.getFieldSingularName(), idFieldName,
                field.getFieldSingularName(), entity.getNameFieldName());
    }
}
