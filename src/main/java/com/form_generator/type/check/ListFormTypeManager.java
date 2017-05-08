package com.form_generator.type.check;

import com.form_generator.type.FormType;
import com.form_generator.type.FormTypeManager;
import com.form_generator.type.base.ListFormType;
import com.form_generator.type.base.StringFormType;
import com.form_generator.type.utils.ElementTypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
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
            listFormType = ElementTypeUtils.getDefault(listTypeMirror, env, element);
        }
        return new ListFormType(listFormType);
    }
}
