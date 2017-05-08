package com.form_generator.type.utils;

import com.form_generator.annotation.FormHidden;
import com.form_generator.annotation.ReferencesFormEntity;
import com.form_generator.field.DefaultFormField;
import com.form_generator.field.FormField;
import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.*;
import com.form_generator.type.check.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utilities class to get {@link FormType} objects from a {@link Element} object, which should represent a class field.
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

        List<FormField> hiddenFields = elements.stream()
                .filter(ElementTypeUtils::isHidden)
                .map(e -> new DefaultFormField(e, new HiddenFormType()))
                .collect(Collectors.toList());

        List<FormField> nonHiddenFields = elements.stream()
                .filter(ElementTypeUtils::isNotHidden)
                .filter(ElementTypeUtils::doesNotReferenceEntity)
                .map(e -> new DefaultFormField(e, getDefault(e.asType(), env, e)))
                .collect(Collectors.toList());

        // TODO for elements marked with ReferencesFormEntity

        return Stream.concat(hiddenFields.stream(), nonHiddenFields.stream()).collect(Collectors.toList());

    }

    /**
     * Get the {@link FormType} for a {@link Element} of a type represented by a {@link TypeMirror}
     * @param typeMirror the {@link TypeMirror} of the {@link Element}
     * @param env the {@link ProcessingEnvironment} used to evaluate the typeMirror
     * @param element the element an input will be generated for
     * @return the {@link FormType} that represents the element
     */
    public static FormType getDefault(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        for (FormTypeManager manager: getManagers()) {
            if (manager.check(typeMirror, env, element)) {
                return manager.getFormType(typeMirror, env, element);
            }
        }

        ErrorUtils.typeNotMappedError(typeMirror, env.getMessager(), element);
        return new StringFormType();
    }

    /**
     * Determines if an element is marked with the annotation {@link ReferencesFormEntity}
     * @param element the element to test
     * @return whether the element is annotated with {@link ReferencesFormEntity}
     */
    private static boolean referencesEntity(Element element) {
        return element.getAnnotation(ReferencesFormEntity.class) != null;
    }

    /**
     * Determines if an element is not marked with the annotation {@link ReferencesFormEntity}
     * @param element the element to test
     * @return whether the element is not annotated with {@link ReferencesFormEntity}
     */
    private static boolean doesNotReferenceEntity(Element element) {
        return !referencesEntity(element);
    }

    /**
     * Determines if an element is marked with the annotation {@link FormHidden}
     * @param element the element to test
     * @return whether the element is marked as {@link FormHidden}
     */
    private static boolean isHidden(Element element) {
        return element.getAnnotation(FormHidden.class) != null;
    }

    /**
     * Determines if an element is not marked with the annotation {@link FormHidden}
     * @param element the element to test
     * @return whether the element is not marked as {@link FormHidden}
     */
    private static boolean isNotHidden(Element element) {
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
