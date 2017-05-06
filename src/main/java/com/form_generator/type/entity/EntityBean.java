package com.form_generator.type.entity;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.PredefinedType;

/**
 * Created by david on 4/29/17.
 */
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

    public EntityBean(PredefinedType predefinedTypeAnnotation) {
        this(predefinedTypeAnnotation.idFieldName(), predefinedTypeAnnotation.nameFieldName());
    }

    @Override
    public String getNameFieldName() {
        return nameFieldName;
    }

    @Override
    public String getIdFieldName() {
        return idFieldName;
    }
}
