package com.form_generator.type.utils;

import com.form_generator.annotation.PredefinedType;
import com.form_generator.annotation.FormEntity;
import com.form_generator.type.FormType;
import com.form_generator.type.base.EntityFormType;
import com.form_generator.type.base.StringFormType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Created by david on 5/4/17.
 */
class DeclaredTypeUtils {

    static FormType getTypeForPredefinedType(Element element) {
        PredefinedType predefinedType = element.getAnnotation(PredefinedType.class);
        return new EntityFormType(predefinedType);
    }

    static boolean isPredefinedType(Element element, ProcessingEnvironment env) {
        return element.getAnnotation(PredefinedType.class) != null;
    }

    static DeclaredType getDeclaredTypeForClass(Class<?> clazz, ProcessingEnvironment env) {
        TypeElement typeElement = env.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return env.getTypeUtils().getDeclaredType(typeElement);
    }

    static FormType getTypeForEntity(DeclaredType declaredType, ProcessingEnvironment env, Element element) {
        TypeElement typeElement = (TypeElement) env.getTypeUtils().asElement(declaredType);

        FormEntity formEntityAnnotation = typeElement.getAnnotation(FormEntity.class);
        if (formEntityAnnotation != null) {
            return new EntityFormType(formEntityAnnotation);
        } else {
            ErrorUtils.missingAnnotationOnEntityError(env.getMessager(), typeElement, element);
            return new StringFormType();
        }
    }
}
