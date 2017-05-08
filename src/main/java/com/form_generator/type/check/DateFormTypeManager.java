package com.form_generator.type.check;

import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.DateFormType;
import com.form_generator.type.utils.DeclaredTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Arrays;

/**
 * Created by david on 5/8/17.
 */
public class DateFormTypeManager implements FormTypeManager {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return Arrays.stream(DateFormType.DateInputType.values())
                .map(DateFormType.DateInputType::getDateClass)
                .map(c -> env.getElementUtils().getTypeElement(c.getCanonicalName()))
                .map(typeElement -> env.getTypeUtils().getDeclaredType(typeElement))
                .anyMatch(dateTypeMirror -> env.getTypeUtils().isAssignable(typeMirror, dateTypeMirror));
    }

    @Override
    public FormType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        DeclaredType declaredType = (DeclaredType) typeMirror;

        for (DateFormType.DateInputType inputType: DateFormType.DateInputType.values()) {
            if (env.getTypeUtils().isAssignable(declaredType, DeclaredTypeUtils.getDeclaredTypeForClass(inputType.getDateClass(), env))) {
                return new DateFormType(inputType);
            }
        }

        env.getMessager().printMessage(Diagnostic.Kind.ERROR,
                "Oops! This is our mistake. Criteria for generating inputs of type 'date' and related includes types that have not been implemented in 'com.form_generator.type.base.DateFormType.DateInputType'. A input of type 'date' will be generated for type '" + declaredType.toString() + "'.");
        return new DateFormType(DateFormType.DateInputType.DATE);
    }
}
