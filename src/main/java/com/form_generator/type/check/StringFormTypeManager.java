package com.form_generator.type.check;

import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.StringFormType;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/4/17.
 */
public class StringFormTypeManager implements FormTypeManager {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return ElementTypeUtils.typeImplementsClass(typeMirror, String.class, env);
    }

    @Override
    public FormType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return new StringFormType();
    }
}
