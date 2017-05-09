package com.form_generator.type.utils;

import com.form_generator.annotation.FormEntity;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Created by david on 5/4/17.
 */
public class DeclaredTypeUtils {

    public static DeclaredType getDeclaredTypeForClass(Class<?> clazz, ProcessingEnvironment env) {
        return getDeclaredTypeForClassName(clazz.getCanonicalName(), env);
    }

    public static DeclaredType getDeclaredTypeForClassName(String className, ProcessingEnvironment env) {
        TypeElement typeElement = env.getElementUtils().getTypeElement(className);
        return env.getTypeUtils().getDeclaredType(typeElement);
    }

    public static FormEntity getEntityAnnotationOnTypeDeclarationElement(DeclaredType declaredType, ProcessingEnvironment env) {
        TypeElement typeDeclarationElement = (TypeElement) declaredType.asElement();
        return typeDeclarationElement.getAnnotation(FormEntity.class);
    }

}
