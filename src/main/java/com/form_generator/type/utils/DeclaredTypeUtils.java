package com.form_generator.type.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Created by david on 5/4/17.
 */
public class DeclaredTypeUtils {

    public static DeclaredType getDeclaredTypeForClass(Class<?> clazz, ProcessingEnvironment env) {
        TypeElement typeElement = env.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return env.getTypeUtils().getDeclaredType(typeElement);
    }

}
