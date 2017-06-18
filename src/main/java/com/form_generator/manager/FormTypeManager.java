package com.form_generator.manager;

import com.form_generator.type.FormFieldType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/4/17.
 */
public interface FormTypeManager<T extends FormFieldType> {
    boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element);
    T getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element);
}
