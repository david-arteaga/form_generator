package com.form_generator.type.utils;

import com.form_generator.type.FormType;
import com.form_generator.type.base.DateFormType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Arrays;

/**
 * Utilities to process {@link DeclaredType}s that represent a date from {@link java.time}
 * Created by david on 5/4/17.
 */
class DateTypeUtils {

    /**
     * Determines whether or not a {@link TypeMirror} implements one of the types specified by {@link com.form_generator.type.base.DateFormType.DateInputType}
     * @param typeMirror the {@link TypeMirror} to test
     * @param env the {@link ProcessingEnvironment} used to evaluate the typeMirror
     * @return whether the typeMirror represents a date
     */
    static boolean isDate(TypeMirror typeMirror, ProcessingEnvironment env) {
        return Arrays.stream(DateFormType.DateInputType.values())
                .map(DateFormType.DateInputType::getDateClass)
                .map(c -> env.getElementUtils().getTypeElement(c.getCanonicalName()))
                .map(typeElement -> env.getTypeUtils().getDeclaredType(typeElement))
                .anyMatch(dateTypeMirror -> env.getTypeUtils().isAssignable(typeMirror, dateTypeMirror));
    }

    /**
     *
     * @param declaredType
     * @param env
     * @return
     */
    static FormType getDateType(DeclaredType declaredType, ProcessingEnvironment env) {
        for (DateFormType.DateInputType inputType: DateFormType.DateInputType.values()) {
            if (env.getTypeUtils().isAssignable(declaredType, DeclaredTypeUtils.getDeclaredTypeForClass(inputType.getDateClass(), env))) {
                return new DateFormType(inputType);
            }
        }
        env.getMessager().printMessage(Diagnostic.Kind.ERROR, "Criteria for generating inputs of type 'date' and related includes types that have not been implemented in 'com.form_generator.type.base.DateFormType.DateInputType'. A input of type 'date' will be generated for type '" + declaredType.toString() + "'.");
        return new DateFormType(DateFormType.DateInputType.DATE);
    }
}
