package com.form_generator.processor;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.FormIgnore;
import com.form_generator.entity.Entity;
import com.form_generator.form.Form;
import com.form_generator.form.field.FormField;
import com.form_generator.html.FormHtmlElement;
import com.form_generator.mapping.Mapping;
import com.form_generator.mapping.angular.AngularMapping;
import com.form_generator.rendered.RenderedForm;
import com.form_generator.type.utils.ElementTypeUtils;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Process annotations of type {@link FormEntity}
 * Created by david on 4/30/17.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {

    // this is to enable static access to the methods declared in this class
    // otherwise if would be necessary to pass along an instance of this class (or an instance of processingEnvironment) to every method that needs
    // to use methods from here
    private static EntityProcessor instance;

    public EntityProcessor() {
        instance = this;
    }

    private static EntityProcessor getInstance() {
        return instance;
    }

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
                    .collect(Collectors.toSet());

            Mapping mapping = new AngularMapping();

            List<RenderedForm> renderedForms = getRenderedFormsForElements(annotatedClasses, mapping);

            generateFilesForForms(renderedForms);

        }

        return false;
    }

    private static void generateFilesForForms(List<RenderedForm> renderedForms) {
        Filer filer = getInstance().processingEnv.getFiler();
        renderedForms.forEach(renderedForm -> {
            try {
                FileObject file = filer.createResource(StandardLocation.SOURCE_OUTPUT, "resources.main.templates",
                        renderedForm.getName() + ".html");
                try (Writer out = file.openWriter()) {
                    out.write(renderedForm.getRenderedHtmlElement().getHtml());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static List<RenderedForm> getRenderedFormsForElements(Collection<? extends TypeElement> typeElements, Mapping mapping) {
        List<FormHtmlElement> formHtmlElements = getFormHtmlElementsForTypeElements(typeElements, mapping);

        return formHtmlElements.stream()
                .map(FormHtmlElement::renderFormHtmlElement)
                .collect(Collectors.toList());
    }

    public static List<FormHtmlElement> getFormHtmlElementsForTypeElements(Collection<? extends TypeElement> typeElements, Mapping mapping) {
        typeElements = typeElements.stream()
                .filter(EntityProcessor::shouldGenerateForm)
                .collect(Collectors.toList());

        return typeElements.stream()
                .map(te -> getFormHtmlElement(te, mapping) )
                .collect(Collectors.toList());
    }

    public static FormHtmlElement getFormHtmlElement(TypeElement typeElement, Mapping mapping) {

        Form form =  new Form(typeElement.getSimpleName().toString() + "Form", getFormFieldsForTypeElement(typeElement));

        return form.map(mapping);
    }

    private static List<FormField> getFormFieldsForTypeElement(TypeElement typeElement) {
        List<Element> elements = getElementsForClass(typeElement);

        return ElementTypeUtils.getFormFieldsForElements(elements, EntityProcessor.getInstance().processingEnv);
    }

    private static boolean shouldGenerateForm(TypeElement e) {
        return e.getAnnotation(FormEntity.class).generateForm();
    }

    private static List<Element> getElementsForClass(TypeElement clazz) {
        return clazz.getEnclosedElements().stream()
                .filter(e -> e.getKind().isField())
                .filter(EntityProcessor::notIgnored)
                .map(VariableElement.class::cast)
                .collect(Collectors.toList());
    }

    private static boolean notIgnored(Element element) {
       return element.getAnnotation(FormIgnore.class) == null;
    }
}
