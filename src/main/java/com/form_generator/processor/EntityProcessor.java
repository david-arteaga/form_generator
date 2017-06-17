package com.form_generator.processor;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.FormIgnore;
import com.form_generator.form.field.FormField;
import com.form_generator.render.Render;
import com.form_generator.type.utils.ElementTypeUtils;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.*;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Process annotations of type {@link FormEntity}
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
        return Collections.singleton(FormEntity.class.getCanonicalName());
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

            // get classes annotated with @FormEntity
            Set<? extends TypeElement> annotatedClasses = roundEnv.getElementsAnnotatedWith(annotationType).stream()
                    .map(e -> (TypeElement) e)
                    .filter(this::shouldGenerateForm)
                    .collect(Collectors.toSet());



            for (TypeElement typeElement: annotatedClasses) {

                List<Element> elements = getElementsForClass(typeElement);

                List<FormField> formFields = ElementTypeUtils.getFormFieldsForElements(elements, processingEnv);

                String formHtml = Render.formWithFields(formFields);

                try {
                    Filer filer = processingEnv.getFiler();
                    FileObject file = filer.createResource(StandardLocation.SOURCE_OUTPUT, "resources.main.templates", typeElement.getSimpleName() + "Form.html");
                    try (Writer out = file.openWriter()) {
                        out.write(formHtml);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return false;
    }

    private  boolean shouldGenerateForm(TypeElement e) {
        return !e.getAnnotation(FormEntity.class).generateForm();
    }

    private List<Element> getElementsForClass(TypeElement clazz) {
        return clazz.getEnclosedElements().stream()
                //.peek(e -> processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, e.getSimpleName()))
                .filter(e -> e.getKind().isField())
                .filter(this::notIgnored)
                .map(VariableElement.class::cast)
                .collect(Collectors.toList());
    }



    private boolean notIgnored(Element element) {
       return element.getAnnotation(FormIgnore.class) == null;
    }
}
