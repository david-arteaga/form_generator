package com.form_generator.type.utils;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.FormHidden;
import com.form_generator.annotation.ReferencesFormEntity;
import com.form_generator.exception.InvalidEntityReferenceException;
import com.form_generator.manager.*;
import com.form_generator.form.field.DefaultFormField;
import com.form_generator.form.field.FormField;
import com.form_generator.type.EntityFormFieldType;
import com.form_generator.type.FormFieldType;
import com.form_generator.manager.FormTypeManager;
import com.form_generator.type.HiddenFormFieldType;
import com.form_generator.type.StringFormFieldType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utilities class to get {@link FormFieldType} objects from a {@link Element} object, which should represent a class field.
 * Created by david on 4/29/17.
 */
public class ElementTypeUtils {

    private final static List<FormTypeManager> managers = Arrays.asList(
            new StringFormTypeManager(),
            new NumberFormTypeManager(),
            new BooleanFormTypeManager(),
            new DateFormTypeManager(),
            new ListFormTypeManager(),
            new EntityFormTypeManager()
    );

    private static List<FormTypeManager> getManagers() {
        return Collections.unmodifiableList(managers);
    }

    public static List<FormField> getFormFieldsForElements(List<Element> elements, ProcessingEnvironment env) {

        // hidden
        List<FormField> hiddenFields = elements.stream()
                .filter(ElementTypeUtils::isHidden)
                .map(e -> new DefaultFormField(e, new HiddenFormFieldType()))
                .collect(Collectors.toList());

        // non hidden that don't have the @ReferencesFormEntity annotation
        List<FormField> nonHiddenFields = elements.stream()
                .filter(ElementTypeUtils::isNotHidden)
                .filter(ElementTypeUtils::doesNotReferenceEntity)
                .map(e -> new DefaultFormField(e, getDefault(e.asType(), env, e)))
                .collect(Collectors.toList());

        EntityFormTypeManager manager = new EntityFormTypeManager();
        List<FormField> referencingFields = elements.stream()
                .filter(ElementTypeUtils::isNotHidden)
                .filter(ElementTypeUtils::referencesEntity)
                .map(e -> new DefaultFormField(e, getReferencedFormType(e, env, manager)))
                .collect(Collectors.toList());

        return concat(hiddenFields.stream(), nonHiddenFields.stream(), referencingFields.stream()).collect(Collectors.toList());

    }

    /**
     * Get the {@link FormFieldType} for a {@link Element} of a type represented by a {@link TypeMirror}
     * @param typeMirror the {@link TypeMirror} of the {@link Element}
     * @param env the {@link ProcessingEnvironment} used to evaluate the typeMirror
     * @param element the element an input will be generated for
     * @return the {@link FormFieldType} that represents the element
     */
    public static FormFieldType getDefault(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        if (referencesEntity(typeMirror)) {
            env.getMessager().printMessage(Diagnostic.Kind.NOTE, "type " + typeMirror.toString() + " in element " + element.getSimpleName() + " in " + element.getEnclosingElement().getSimpleName() + " is annotated with ReferencesFormEntity");
        }

        for (FormTypeManager manager: getManagers()) {
            if (manager.check(typeMirror, env, element)) {
                return manager.getFormType(typeMirror, env, element);
            }
        }

        ErrorUtils.typeNotMappedError(typeMirror, env.getMessager(), element);
        return new StringFormFieldType();
    }

    /**
     * Returns the {@link FormFieldType} that corresponds to an element, considering the entity referenced according to the {@link ReferencesFormEntity} annotation.
     * If the element does not have this annotation, a {@link NullPointerException} will be thrown
     * @param element the element to be evaluated
     * @return the {@link FormFieldType} that corresponds to this element
     */
    private static FormFieldType getReferencedFormType(Element element, ProcessingEnvironment env, EntityFormTypeManager manager) {
        AnnotationMirror referencesFormEntityAnnotation = AnnotationUtils.getAnnotation(element, ReferencesFormEntity.class, env).get();
        DeclaredType referencedType = AnnotationUtils.getAnnotationDeclaredTypeValue(referencesFormEntityAnnotation, "value", env);

        Optional<EntityFormFieldType> formFieldType = manager.tryGetFormType(referencedType, env, element);

        // verify that entity referenced actually is annotated as @FormEntity
        if (!formFieldType.isPresent()) {
            ErrorUtils.missingAnnotationOnEntityError(env.getMessager(), (TypeElement) referencedType.asElement(), element);
            throw new InvalidEntityReferenceException("Type " + referencedType.toString() + " is not a @FormEntity");
        }

        return formFieldType.get();
    }

    @SafeVarargs
    private static <T> Stream<T> concat(Stream<T>... streams) {
        if (streams.length == 0) return Stream.empty();
        if (streams.length == 1) return streams[0];

        Stream<T> stream = streams[0];
        for (int i = 1; i < streams.length; i++) {
            stream = Stream.concat(stream, streams[i]);
        }

        return stream;
    }

    /**
     * Determines if an element is marked with the annotation {@link ReferencesFormEntity}
     * @param element the element to test
     * @return whether the element is annotated with {@link ReferencesFormEntity}
     */
    private static boolean referencesEntity(AnnotatedConstruct element) {
        return element.getAnnotation(ReferencesFormEntity.class) != null;
    }

    /**
     * Determines if an element is not marked with the annotation {@link ReferencesFormEntity}
     * @param element the element to test
     * @return whether the element is not annotated with {@link ReferencesFormEntity}
     */
    private static boolean doesNotReferenceEntity(AnnotatedConstruct element) {
        return !referencesEntity(element);
    }

    /**
     * Determines if an element is marked with the annotation {@link FormHidden}
     * @param element the element to test
     * @return whether the element is marked as {@link FormHidden}
     */
    private static boolean isHidden(AnnotatedConstruct element) {
        return element.getAnnotation(FormHidden.class) != null;
    }

    /**
     * Determines if an element is not marked with the annotation {@link FormHidden}
     * @param element the element to test
     * @return whether the element is not marked as {@link FormHidden}
     */
    private static boolean isNotHidden(AnnotatedConstruct element) {
        return !isHidden(element);
    }

    /**
     * Determines if a {@link TypeMirror} can be assigned to a variable of the type specified
     * @param typeMirror the type to be tested
     * @param clazz the class to be tested
     * @param env the {@link ProcessingEnvironment} used to evaluate the typeMirror
     * @return whether the typeMirror can be assigned to the class
     */
    public static boolean typeImplementsClass(TypeMirror typeMirror, Class<?> clazz, ProcessingEnvironment env) {
        javax.lang.model.util.Types typesUtil = env.getTypeUtils();
        DeclaredType type = DeclaredTypeUtils.getDeclaredTypeForClass(clazz, env);
        return typesUtil.isAssignable(typeMirror, type);
    }

}
