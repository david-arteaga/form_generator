package com.form_generator.type.utils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * Created by david on 5/4/17.
 */
class ErrorUtils {
    static void missingAnnotationOnEntityError(Messager messager, TypeElement typeElement, Element fieldElement) {
        String enclosingClass = fieldElement.getEnclosingElement().getSimpleName().toString();
        String fieldName = fieldElement.getSimpleName().toString();
        String fieldType = typeElement.getQualifiedName().toString();
        messager.printMessage(ERROR, "FormFieldType '" + fieldType + "' used in field '" + fieldName + "' in class '" + enclosingClass
                + "' is not annotated with @FormEntity. Please annotate it with @EntityForm or specify the name of the 'id' and 'name' fields using @PredefinedType.");
    }

    static void typeNotMappedError(TypeMirror typeMirror, Messager messager, Element fieldElement) {
        String enclosingClass = fieldElement.getEnclosingElement().getSimpleName().toString();
        String fieldName = fieldElement.getSimpleName().toString();
        String fieldType = typeMirror.toString();
        messager.printMessage(ERROR, "Type '" + fieldType + "' used in field '" + fieldName + "' in class '" + enclosingClass + "' is not supported. It is not a @FormEntity.");
    }
}
