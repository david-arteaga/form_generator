package com.form_generator.type.base;

import com.form_generator.field.Field;
import com.form_generator.type.FormType;

import java.time.*;

/**
 * Created by david on 5/1/17.
 */
public class DateFormType implements FormType {

    private final DateInputType dateInputType;

    public DateFormType(DateInputType dateInputType) {
        this.dateInputType = dateInputType;
    }

    private static final String template =
            "<div class=\"form-group\">\n" +
            "    <input type=\"%s\" name=\"%s\" class=\"form-control\" placeholder=\"%s\" />\n" +
            "</div>";

    @Override
    public String renderField(Field field) {
        return String.format(template,
                dateInputType.getName(), field.getFieldSingularName(), field.getFieldSingularLabel());
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
