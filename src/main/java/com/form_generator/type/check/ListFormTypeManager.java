package com.form_generator.type.check;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.ListTypeReferencesFormEntity;
import com.form_generator.annotation.ReferencesFormEntity;
import com.form_generator.exception.InvalidOperationException;
import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.EntityFormType;
import com.form_generator.type.base.ListFormType;
import com.form_generator.type.base.StringFormType;
import com.form_generator.type.utils.DeclaredTypeUtils;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;

/**
 * Created by david on 5/8/17.
 */
public class ListFormTypeManager implements FormTypeManager {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return ElementTypeUtils.typeImplementsClass(typeMirror, List.class, env);
    }

    @Override
    public FormType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        DeclaredType listType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> listParams = listType.getTypeArguments();
        FormType listFormType;
        if (listParams.isEmpty()) {
            listFormType = new StringFormType();
        } else {
            TypeMirror listTypeMirror = listParams.get(0);

            ListTypeReferencesFormEntity typeReferenceAnnotation = element.getAnnotation(ListTypeReferencesFormEntity.class);
            if (typeReferenceAnnotation != null) {
                TypeElement referencedTypeElement = env.getElementUtils().getTypeElement(typeReferenceAnnotation.value());

                if (referencedTypeElement != null) { // if type name is correct and element can be retrieved
                    FormEntity formEntityAnnotation = referencedTypeElement.getAnnotation(FormEntity.class);
                    if (formEntityAnnotation != null) {
                        listFormType = new EntityFormType(formEntityAnnotation);
                    } else {
                        invalidEntityError(env.getMessager(), element, referencedTypeElement);
                        listFormType = ElementTypeUtils.getDefault(listTypeMirror, env, element);
                    }
                } else {
                    invalidClassNameError(env.getMessager(), element, typeReferenceAnnotation);
                    listFormType = ElementTypeUtils.getDefault(listTypeMirror, env, element);
                }
            } else {
                listFormType = ElementTypeUtils.getDefault(listTypeMirror, env, element);
            }

            try {
                listFormType.getEntity();
            } catch (InvalidOperationException ex) {
                env.getMessager().printMessage(Diagnostic.Kind.NOTE,
                        "Error in element " + element.getSimpleName() + " in class " + element.getEnclosingElement().asType().toString() +
                                ". This element uses type " + listTypeMirror.toString() + " which is not an entity." +
                                " It is not yet supported to include lists of elements that are not entities");
            }
        }
        return new ListFormType(listFormType);
    }

    private void invalidEntityError(Messager messager, Element element, TypeElement referencedType) {
        messager.printMessage(Diagnostic.Kind.ERROR, "The type '" + referencedType.toString() + "' referenced from the @ListTypeReferencesFormEntity annotation in element '" + element.getSimpleName() + "' in class '" + element.getEnclosingElement().asType().toString() + "' is not annotated with a @FormEntity annotation. The annotation @ListTypeReferencesFormEntity will be ignored.");
    }

    private void invalidClassNameError(Messager messager, Element element, ListTypeReferencesFormEntity typeReferenceAnnotation) {
        messager.printMessage(Diagnostic.Kind.ERROR, "The element that represents class name '" + typeReferenceAnnotation.value() + "' specified in the @ListTypeReferencesFormEntity annotation in element '" + element.getSimpleName() + "' in class '" + element.getEnclosingElement().asType().toString() + "' could not be retrieved. The annotation @ListTypeReferencesFormEntity will be ignored.");
    }
}
