package com.form_generator.type.utils;

import com.form_generator.annotation.FormHidden;
import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeChecker;
import com.form_generator.type.base.*;
import com.form_generator.type.check.NumberFormTypeChecker;
import com.form_generator.type.check.StringFormTypeChecker;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import java.util.*;

/**
 * Utilities class to get {@link FormType} objects from a {@link Element} object, which should represent a class field.
 * Created by david on 4/29/17.
 */
public class ElementTypeUtils {

    private final static List<FormTypeChecker> checkers = Arrays.asList(new StringFormTypeChecker(), new NumberFormTypeChecker());
    private static List<FormTypeChecker> getCheckers() {
        return Collections.unmodifiableList(checkers);
    }

    /**
     * Get the {@link FormType} for a {@link Element}
     * @param element the element an input will be generated for
     * @param env the {@link ProcessingEnvironment} used to evaluate the typeMirror
     * @return the {@link FormType} that represents the element
     */
    public static FormType getDefault(Element element, ProcessingEnvironment env) {
        TypeMirror typeMirror = element.asType();
        for (FormTypeChecker checker: getCheckers()) {
            if (checker.check(typeMirror, env, element)) {
                return checker.getFormType(typeMirror, env, element);
            }
        }
        ErrorUtils.typeNotMappedError(typeMirror, env.getMessager(), element);
        return new StringFormType();
        //return getDefault(element.asType(), env, element);
    }

    /**
     * Get the {@link FormType} for a {@link Element} of a type represented by a {@link TypeMirror}
     * @param typeMirror the {@link TypeMirror} of the {@link Element}
     * @param env the {@link ProcessingEnvironment} used to evaluate the typeMirror
     * @param element the element an input will be generated for
     * @return the {@link FormType} that represents the element
     */
    static FormType getDefault(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        TypeKind typeKind = typeMirror.getKind();

        if (isHidden(element)) {
            return new HiddenFormType();
        }

        switch (typeKind) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return new NumberFormType();
            case BOOLEAN:
                return new BooleanFormType();
            case DECLARED:
                return getFormTypeForDeclaredType((DeclaredType) typeMirror, env, element);
            default:
                ErrorUtils.typeNotMappedError(typeMirror, env.getMessager(), element);
                return new StringFormType();
        }
    }

    /**
     * Determines if an element is marked with the annotation {@link FormHidden}
     * @param fieldElement the element to test
     * @return whether the element is marked as {@link FormHidden}
     */
    public static boolean isHidden(Element fieldElement) {
        return fieldElement.getAnnotation(FormHidden.class) != null;
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

    /**
     * Get the {@link FormType} for a {@link DeclaredType}
     * @param declaredType the {@link DeclaredType}
     * @param env the {@link ProcessingEnvironment} used to evaluate the declaredType
     * @param element the {@link Element} to which the declaredType belongs
     * @return the {@link FormType} that corresponds to the element of type declaredType
     */
    private static FormType getFormTypeForDeclaredType(DeclaredType declaredType, ProcessingEnvironment env, Element element) {
        if (typeImplementsClass(declaredType, String.class, env)) {
            return new StringFormType();
        } else if (typeImplementsClass(declaredType, Number.class, env)) {
            return new NumberFormType();
        } else if (typeImplementsClass(declaredType, List.class, env)) {
            return ListTypeUtils.getListFormType(declaredType, env, element);
        } else if (DateTypeUtils.isDate(declaredType, env)) {
            return DateTypeUtils.getDateType(declaredType, env);
        } else if (DeclaredTypeUtils.isPredefinedType(element, env)) {
            return DeclaredTypeUtils.getTypeForPredefinedType(element);
        } else {
            return DeclaredTypeUtils.getTypeForEntity(declaredType, env, element);
        }
    }
}
