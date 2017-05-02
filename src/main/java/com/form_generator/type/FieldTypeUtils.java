package com.form_generator.type;

import com.form_generator.type.base.*;
import com.form_generator.type.entity.annotation.Entity;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import static javax.tools.Diagnostic.Kind.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilities class to get {@link Type} objects from a {@link VariableElement} object, which should represent a class field.
 * Created by david on 4/29/17.
 */
public class FieldTypeUtils {

    public static Type getDefault(VariableElement fieldElement, ProcessingEnvironment env) {
        return getDefault(fieldElement.asType(), env, fieldElement);
    }

    private static Type getDefault(TypeMirror typeMirror, ProcessingEnvironment env, VariableElement fieldElement) {
        TypeKind typeKind = typeMirror.getKind();

        switch (typeKind) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return new NumberType();
            case BOOLEAN:
                return new BooleanType();
            case DECLARED:
                return getTypeForDeclared((DeclaredType) typeMirror, env, fieldElement);
            default:
                typeNotMappedError(typeMirror, env.getMessager(), fieldElement);
                return new StringType();
        }
    }



    private static Type getTypeForDeclared(DeclaredType type, ProcessingEnvironment env, VariableElement fieldElement) {
        if (implementsType(type, String.class, env)) {
            return new StringType();
        } else if (implementsType(type, List.class, env)) {
            return getListType(type, env, fieldElement);
        } else if (isDate(type, env)) {
            return getDateType(type, env);
        } else {
            return getTypeForEntity(type, env, fieldElement);
        }
    }

    private static ListType getListType(DeclaredType type, ProcessingEnvironment env, VariableElement fieldType) {
        List<? extends TypeMirror> listParams = type.getTypeArguments();
        Type listType;
        if (listParams.isEmpty()) {
            listType = new StringType();
        } else {
            TypeMirror listTypeMirror = listParams.get(0);
            listType = getDefault(listTypeMirror, env, fieldType);
        }
        return new ListType(listType);
    }

    private static Type getTypeForEntity(DeclaredType type, ProcessingEnvironment env, VariableElement fieldElement) {
        TypeElement typeElement = (TypeElement) env.getTypeUtils().asElement(type);

        Entity entityAnnotation = typeElement.getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            return new EntityType(entityAnnotation);
        } else {
            missingAnnotationOnEntityError(env.getMessager(), typeElement, fieldElement);
            return new StringType();
        }
    }

    private static boolean implementsType(TypeMirror typeMirror, Class<?> clazz, ProcessingEnvironment env) {
        javax.lang.model.util.Types typesUtil = env.getTypeUtils();
        DeclaredType type = getDeclaredType(clazz, env);
        return typesUtil.isAssignable(typeMirror, type);
    }

    private static boolean isDate(TypeMirror typeMirror, ProcessingEnvironment env) {
        return Arrays.stream(DateType.DateInputType.values())
                .map(DateType.DateInputType::getClass)
                .map(c -> env.getElementUtils().getTypeElement(c.getCanonicalName()))
                .map(typeElement -> env.getTypeUtils().getDeclaredType(typeElement))
                .anyMatch(dateTypeMirror -> env.getTypeUtils().isAssignable(typeMirror, dateTypeMirror));
    }

    private static Type getDateType(DeclaredType type, ProcessingEnvironment env) {
        for (DateType.DateInputType inputType: DateType.DateInputType.values()) {
            if (env.getTypeUtils().isAssignable(type, getDeclaredType(inputType.getDateClass(), env))) {
                return new DateType(inputType);
            }
        }
        env.getMessager().printMessage(Diagnostic.Kind.ERROR, "Criteria for generating inputs of type 'date' and related includes types that have not been implemented in 'com.form_generator.type.base.DateType.DateInputType'. A input of type 'date' will be generated for type '" + type.toString() + "'.");
        return new DateType(DateType.DateInputType.DATE);
    }

    private static DeclaredType getDeclaredType (Class<?> clazz, ProcessingEnvironment env) {
        TypeElement typeElement = env.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return env.getTypeUtils().getDeclaredType(typeElement);
    }

    private static void missingAnnotationOnEntityError(Messager messager, TypeElement typeElement, VariableElement fieldElement) {
        String enclosingClass = fieldElement.getEnclosingElement().getSimpleName().toString();
        String fieldName = fieldElement.getSimpleName().toString();
        String fieldType = typeElement.getQualifiedName().toString();
        messager.printMessage(ERROR, "Type '" + fieldType + "' used in field '" + fieldName + "' in class '" + enclosingClass + "' is not annotated with @Entity. A input of type text will be generated for fields of this type.");
    }

    private static void typeNotMappedError(TypeMirror typeMirror, Messager messager, VariableElement fieldElement) {
        String enclosingClass = fieldElement.getEnclosingElement().getSimpleName().toString();
        String fieldName = fieldElement.getSimpleName().toString();
        String fieldType = typeMirror.toString();
        messager.printMessage(ERROR, "Type '" + fieldType + "' used in field '" + fieldName + "' in class '" + enclosingClass + "' is not supported. A input of type text will be generated for fields of this type.");
    }
}
