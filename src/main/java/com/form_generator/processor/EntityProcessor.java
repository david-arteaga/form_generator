package com.form_generator.processor;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.FormIgnore;
import com.form_generator.form.Form;
import com.form_generator.form.field.FormField;
import com.form_generator.html.FormHtmlElement;
import com.form_generator.mapping.Mapping;
import com.form_generator.mapping.thymeleaf.ThymeleafMapping;
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

            Mapping mapping = new ThymeleafMapping();

            List<RenderedForm> renderedForms = getFormsForElements(annotatedClasses, mapping);

            generateFilesForForms(renderedForms);

        }

        return false;
    }

    private void generateFilesForForms(List<RenderedForm> renderedForms) {
        Filer filer = processingEnv.getFiler();
        renderedForms.forEach(renderedForm -> {
            try {
                FileObject file = filer.createResource(StandardLocation.SOURCE_OUTPUT, "resources.main.templates",
                        renderedForm.getName() + "Form.html");
                try (Writer out = file.openWriter()) {
                    out.write(renderedForm.getRenderedHtmlElement().getHtml());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private List<RenderedForm> getFormsForElements(Collection<? extends TypeElement> _typeElements, Mapping mapping) {
        List<? extends TypeElement> typeElements = _typeElements.stream()
                .filter(this::shouldGenerateForm)
                .collect(Collectors.toList());

        List<Form> forms = typeElements.stream()
                .map(te -> new Form(te.getSimpleName().toString(), getFormFieldsForTypeElement(te)) )
                .collect(Collectors.toList());

        List<FormHtmlElement> formHtmlElements = forms.stream()
                .map(f -> f.map(mapping))
                .collect(Collectors.toList());

        List<RenderedForm> renderedForms = formHtmlElements.stream()
                .map(FormHtmlElement::renderFormHtmlElement)
                .collect(Collectors.toList());

        return renderedForms;
    }

    private List<FormField> getFormFieldsForTypeElement(TypeElement typeElement) {
        List<Element> elements = getElementsForClass(typeElement);

        return ElementTypeUtils.getFormFieldsForElements(elements, processingEnv);
    }

    private  boolean shouldGenerateForm(TypeElement e) {
        return e.getAnnotation(FormEntity.class).generateForm();
    }

    private List<Element> getElementsForClass(TypeElement clazz) {
        return clazz.getEnclosedElements().stream()
                .filter(e -> e.getKind().isField())
                .filter(this::notIgnored)
                .map(VariableElement.class::cast)
                .collect(Collectors.toList());
    }



    private boolean notIgnored(Element element) {
       return element.getAnnotation(FormIgnore.class) == null;
    }
}
