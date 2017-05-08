package com.form_generator.type.check;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.PredefinedType;
import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.EntityFormType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by david on 5/8/17.
 */
public class EntityFormTypeManager implements FormTypeManager {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        TypeKind typeKind = typeMirror.getKind();
        if (typeKind != TypeKind.DECLARED) {
            return false;
        }

        PredefinedType predefinedTypeAnnotation = element.getAnnotation(PredefinedType.class);
        return predefinedTypeAnnotation != null || getEntityAnnotationOnTypeDeclarationElement((DeclaredType) typeMirror, env) != null;
    }

    @Override
    public FormType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        // TODO
        PredefinedType predefinedTypeAnnotation = element.getAnnotation(PredefinedType.class);
        if (predefinedTypeAnnotation != null) {
            return new EntityFormType(predefinedTypeAnnotation);
        }

        FormEntity formEntityAnnotation = getEntityAnnotationOnTypeDeclarationElement((DeclaredType) typeMirror, env);
        return new EntityFormType(formEntityAnnotation);
    }

    private FormEntity getEntityAnnotationOnTypeDeclarationElement(DeclaredType declaredType, ProcessingEnvironment env) {
        TypeElement typeDeclarationElement = getTypeDeclarationElement(declaredType, env);
        return typeDeclarationElement.getAnnotation(FormEntity.class);
    }

    private TypeElement getTypeDeclarationElement (DeclaredType declaredType, ProcessingEnvironment env) {
        return (TypeElement) env.getTypeUtils().asElement(declaredType);
    }

}
