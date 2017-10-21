package com.form_generator.mapping.angular.mapper;

import com.form_generator.entity.Entity;
import com.form_generator.entity.EntityBean;
import com.form_generator.exception.InvalidOperationException;
import com.form_generator.form.Form;
import com.form_generator.form.field.FormField;
import com.form_generator.html.FormHtmlElement;
import com.form_generator.html.HtmlElement;
import com.form_generator.html.TextNode;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.angular.html.HtmlUtils;
import com.form_generator.mapping.angular.AngularMapping;
import com.form_generator.processor.EntityProcessor;
import com.form_generator.type.FormFieldType;
import com.form_generator.type.ListFormFieldType;

import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by david on 6/17/17.
 */
public class ListFormFieldMapper implements FormFieldMapper<ListFormFieldType> {

    @Override
    public HtmlElement mapField(FormField formField, ListFormFieldType formFieldType, String formGroupName) {
        HtmlElement enclosingDiv = new HtmlElement("div")
                .addAttribute("class", "innerFormContainer");

        enclosingDiv.appendChild(
            new HtmlElement("h5")
                .appendChild(new TextNode(formField.getFieldPluralLabel()))
        );

        HtmlElement div = new HtmlElement("div")
                .addAttribute("formArrayName", formField.getFieldPluralName())
                .addAttribute("*ngFor", "let item of " + formGroupName + ".get('" + formField.getFieldPluralName() + "').controls; let i = index;");

        enclosingDiv.appendChild(div);

        HtmlElement subForm = new HtmlElement("div")
                .addAttribute("[formGroupName]", "i");

        TypeElement referencedTypeElement = formFieldType.getReferencedTypeElement();
        FormHtmlElement innerForm = EntityProcessor.getFormHtmlElement(referencedTypeElement, new AngularMapping());

        subForm.getChildren().addAll(innerForm.getChildren());

        div.appendChild(subForm);

        return enclosingDiv;
    }

}
