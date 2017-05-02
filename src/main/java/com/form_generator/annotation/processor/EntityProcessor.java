package com.form_generator.annotation.processor;

import com.form_generator.field.DefaultField;
import com.form_generator.field.Field;
import com.form_generator.render.Render;
import com.form_generator.type.entity.annotation.Entity;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Process annotations of type {@link Entity}
 * Created by david on 4/30/17.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {
    /**
     * @return the names of the annotation types supported by this
     * processor, or an empty set if none
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Entity.class.getCanonicalName());
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotationType: annotations) {
            print("Processing " + annotationType.getQualifiedName().toString());
            Set<? extends TypeElement> annotatedElements = roundEnv.getElementsAnnotatedWith(annotationType).stream().map(e -> (TypeElement) e).collect(Collectors.toSet());

            for (TypeElement typeElement: annotatedElements) {
                if (!typeElement.getAnnotation(Entity.class).generateForm()) {
                    continue;
                }
                print("Processing class " + typeElement.getQualifiedName().toString());
                List<Field> typeFields = getFieldsForType(typeElement);

                String form = Render.formWithFields(typeFields);

                // TODO write form to file
                print(form + "\n.\n.");
            }


        }
        return false;
    }

    private List<Field> getFieldsForType(TypeElement clazz) {
        return clazz.getEnclosedElements().stream()
                .filter(e -> e.getKind().isField())
                .map(VariableElement.class::cast)
                .map(e -> new DefaultField(e, processingEnv))
                .collect(Collectors.toList());
    }

    private void print(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, message);
    }
}
