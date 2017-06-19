package com.form_generator.manager;

import com.form_generator.annotation.FormEntity;
import com.form_generator.annotation.ListTypeReferencesFormEntity;
import com.form_generator.type.EntityFormFieldType;
import com.form_generator.type.FormFieldType;
import com.form_generator.type.ListFormFieldType;
import com.form_generator.type.StringFormFieldType;
import com.form_generator.type.utils.AnnotationUtils;
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
import java.util.Optional;

/**
 * Created by david on 5/8/17.
 */
public class ListFormTypeManager implements FormTypeManager<ListFormFieldType> {
    @Override
    public boolean check(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        return ElementTypeUtils.typeImplementsClass(typeMirror, List.class, env);
    }

    @Override
    public ListFormFieldType getFormType(TypeMirror typeMirror, ProcessingEnvironment env, Element element) {
        DeclaredType listType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> listParams = listType.getTypeArguments();
        FormFieldType listFormFieldType;
        if (listParams.isEmpty()) {
            listFormFieldType = new StringFormFieldType();
        } else {
            TypeMirror listTypeMirror = listParams.get(0);

            Optional<? extends AnnotationMirror> typeReferenceAnnotation = AnnotationUtils.getAnnotation(element, ListTypeReferencesFormEntity.class, env);

            if (typeReferenceAnnotation.isPresent()) {
                TypeElement referencedTypeElement = AnnotationUtils.getAnnotationTypeElementValue(typeReferenceAnnotation.get(), "value", env);

                FormEntity formEntityAnnotation = referencedTypeElement.getAnnotation(FormEntity.class);
                if (formEntityAnnotation != null) {
                    listFormFieldType = new EntityFormFieldType(formEntityAnnotation);
                } else {
                    invalidEntityError(env.getMessager(), element, referencedTypeElement);
                    listFormFieldType = ElementTypeUtils.getDefault(listTypeMirror, env, element);
                }
            } else {
                listFormFieldType = ElementTypeUtils.getDefault(listTypeMirror, env, element);
            }
        }
        return new ListFormFieldType(listFormFieldType);
    }

    private void invalidEntityError(Messager messager, Element element, TypeElement referencedType) {
        messager.printMessage(Diagnostic.Kind.ERROR, "The type '" + referencedType.toString() + "' referenced from the @ListTypeReferencesFormEntity annotation in element '" + element.getSimpleName() + "' in class '" + element.getEnclosingElement().asType().toString() + "' is not annotated with a @FormEntity annotation. The annotation @ListTypeReferencesFormEntity will be ignored.");
    }

}
