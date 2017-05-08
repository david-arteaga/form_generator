package com.form_generator.type.check;

import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.NumberFormType;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/4/17.
 */
public class NumberFormTypeManager implements FormTypeManager {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        TypeKind typeKind = typeMirror.getKind();

        switch (typeKind) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return true;
            case DECLARED:
                return ElementTypeUtils.typeImplementsClass(typeMirror, Number.class, env);
            default:
                return false;
        }
    }

    @Override
    public FormType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return new NumberFormType();
    }
}
