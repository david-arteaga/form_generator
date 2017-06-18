package com.form_generator.manager;

import com.form_generator.type.FormFieldType;
import com.form_generator.type.StringFormFieldType;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/4/17.
 */
public class StringFormTypeManager implements FormTypeManager<StringFormFieldType> {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return ElementTypeUtils.typeImplementsClass(typeMirror, String.class, env);
    }

    @Override
    public StringFormFieldType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return new StringFormFieldType();
    }
}
