package com.form_generator.manager;

import com.form_generator.type.FormType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/4/17.
 */
public interface FormTypeManager {
    boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element);
    FormType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element);
}
