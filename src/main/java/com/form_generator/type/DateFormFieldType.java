package com.form_generator.type;

import com.form_generator.form.field.FormField;
import com.form_generator.html.HtmlElement;
import com.form_generator.mapping.FormFieldMapper;
import com.form_generator.mapping.Mapping;

import java.time.*;

/**
 * Created by david on 5/1/17.
 */
public class DateFormFieldType implements FormFieldType {

    private final DateInputType dateInputType;

    public DateFormFieldType(DateInputType dateInputType) {
        this.dateInputType = dateInputType;
    }

    private static final String template =
            "<div class=\"form-group\">\n" +
            "    <input type=\"%s\" name=\"%s\" class=\"form-control\" placeholder=\"%s\" />\n" +
            "</div>";

    @Override
    public String renderField(FormField formField) {
        return String.format(template,
                dateInputType.getName(), formField.getFieldSingularName(), formField.getFieldSingularLabel());
    }

    @Override
    public HtmlElement map(FormField formField, Mapping mapping) {
        FormFieldMapper<DateFormFieldType> mapper = mapping.getMapper(this);
        return mapper.mapField(formField, this);
    }

    public DateInputType getDateInputType() {
        return dateInputType;
    }

    public enum DateInputType {
        DATE("date", LocalDate.class),
        TIME("time", LocalTime.class),
        DATETIME_LOCAL("datetime-local", LocalDateTime.class),
        MONTH("month", YearMonth.class);

        private final String name;
        private final Class<?> dateClass;

        DateInputType(String name, Class<?> dateClass) {
            this.name = name;
            this.dateClass = dateClass;
        }

        public String getName() {
            return name;
        }

        public Class<?> getDateClass() {
            return dateClass;
        }
    }
}
