package com.form_generator.type.utils;

import com.form_generator.type.FormType;
import com.form_generator.type.base.ListFormType;
import com.form_generator.type.base.StringFormType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Utilities to process {@link DeclaredType}s that represent a {@link List}
 * Created by david on 5/4/17.
 */
class ListTypeUtils {

    /**
     *
     * @param listType
     * @param env
     * @param fieldType
     * @return
     */
    static ListFormType getListFormType(DeclaredType listType, ProcessingEnvironment env, Element fieldType) {
        List<? extends TypeMirror> listParams = listType.getTypeArguments();
        FormType listFormType;
        if (listParams.isEmpty()) {
            listFormType = new StringFormType();
        } else {
            TypeMirror listTypeMirror = listParams.get(0);
            listFormType = ElementTypeUtils.getDefault(listTypeMirror, env, fieldType);
        }
        return new ListFormType(listFormType);
    }
}
