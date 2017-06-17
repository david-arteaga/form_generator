package com.form_generator.manager;

import com.form_generator.type.FormFieldType;
import com.form_generator.type.NumberFormFieldType;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/8/17.
 */
public class BooleanFormTypeManager implements FormTypeManager {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        TypeKind typeKind = typeMirror.getKind();

        switch (typeKind) {
            case BOOLEAN:
                return true;
            case DECLARED:
                return ElementTypeUtils.typeImplementsClass(typeMirror, Boolean.class, env);
            default:
                return false;
        }
    }

    @Override
    public FormFieldType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return new NumberFormFieldType();
    }
}
