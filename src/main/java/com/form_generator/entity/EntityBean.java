package com.form_generator.entity;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.PredefinedType;

/**
 * Created by david on 4/29/17.
 */
public class EntityBean implements Entity {
    private final String idFieldName;
    private final String nameFieldName;

    public EntityBean(String idFieldName, String nameFieldName) {
        this.idFieldName = idFieldName;
        this.nameFieldName = nameFieldName;
    }

    public EntityBean(FormEntity formEntityAnnotation) {
        this(formEntityAnnotation.idFieldName(), formEntityAnnotation.nameFieldName());
    }

    public EntityBean(PredefinedType predefinedTypeAnnotation) {
        this(predefinedTypeAnnotation.idFieldName(), predefinedTypeAnnotation.nameFieldName());
    }

    @Override
    public String getIdFieldName() {
        return idFieldName;
    }

    @Override
    public String getNameFieldName() {
        return nameFieldName;
    }

}
