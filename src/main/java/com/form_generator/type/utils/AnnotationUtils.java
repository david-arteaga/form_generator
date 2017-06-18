package com.form_generator.type.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import java.util.Map;
import java.util.Optional;

/**
 * Created by david on 5/9/17.
 */
public class AnnotationUtils {

    public static TypeElement getAnnotationTypeElementValue(AnnotationMirror annotationMirror, String key, ProcessingEnvironment env) {
        AnnotationValue annotationValue = getAnnotationValue(annotationMirror, key).get();
        return env.getElementUtils().getTypeElement(annotationValue.getValue().toString());
    }

    public static DeclaredType getAnnotationDeclaredTypeValue(AnnotationMirror annotationMirror, String key, ProcessingEnvironment env) {
        AnnotationValue annotationValue = getAnnotationValue(annotationMirror, key).get();
        return env.getTypeUtils().getDeclaredType(env.getElementUtils().getTypeElement(annotationValue.getValue().toString()));
    }

    public static Optional<AnnotationValue> getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        return annotationMirror.getElementValues().entrySet().stream()
                .filter(e -> e.getKey().getSimpleName().toString().equals(key))
                .findFirst()
                .map(Map.Entry::getValue);

    }

    public static Optional<? extends AnnotationMirror> getAnnotation(AnnotatedConstruct annotatedConstruct, Class<?> annotationType, ProcessingEnvironment env) {
        return annotatedConstruct.getAnnotationMirrors().stream()
                .filter(am -> env.getTypeUtils().isSameType(am.getAnnotationType(), DeclaredTypeUtils.getDeclaredTypeForClass(annotationType, env)))
                .findFirst();
    }

}
