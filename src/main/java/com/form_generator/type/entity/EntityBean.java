package com.form_generator.type.entity;

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

    public EntityBean(com.form_generator.type.entity.annotation.Entity entityAnnotation) {
        this(entityAnnotation.idFieldName(), entityAnnotation.nameFieldName());
    }
}
