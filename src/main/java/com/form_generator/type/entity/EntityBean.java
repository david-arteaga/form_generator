package com.form_generator.type.entity;

import com.form_generator.annotation.FormEntity;
import lombok.Data;

/**
 * Created by david on 4/29/17.
 */
@Data
public class EntityBean implements Entity {
    private final String nameFieldName;
    private final String idFieldName;

    public EntityBean(String nameFieldName, String idFieldName) {
        this.nameFieldName = nameFieldName;
        this.idFieldName = idFieldName;
    }

    public EntityBean(FormEntity formEntityAnnotation) {
        this(formEntityAnnotation.idFieldName(), formEntityAnnotation.nameFieldName());
    }

    public EntityBean(com.form_generator.annotation.DefinedType definedTypeAnnotation) {
        this(definedTypeAnnotation.idFieldName(), definedTypeAnnotation.nameFieldName());
    }
}
