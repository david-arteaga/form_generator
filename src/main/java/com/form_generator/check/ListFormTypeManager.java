package com.form_generator.check;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.ListTypeReferencesFormEntity;
import com.form_generator.exception.InvalidOperationException;
import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.EntityFormType;
import com.form_generator.type.ListFormType;
import com.form_generator.type.StringFormType;
import com.form_generator.type.utils.AnnotationUtils;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Optional;

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

            Optional<? extends AnnotationMirror> typeReferenceAnnotation = AnnotationUtils.getAnnotation(element, ListTypeReferencesFormEntity.class, env);

            if (typeReferenceAnnotation.isPresent()) {
                TypeElement referencedTypeElement = AnnotationUtils.getAnnotationTypeElementValue(typeReferenceAnnotation.get(), "value", env);

                FormEntity formEntityAnnotation = referencedTypeElement.getAnnotation(FormEntity.class);
                if (formEntityAnnotation != null) {
                    listFormType = new EntityFormType(formEntityAnnotation);
                } else {
                    invalidEntityError(env.getMessager(), element, referencedTypeElement);
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

}
