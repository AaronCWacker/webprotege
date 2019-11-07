package edu.stanford.bmir.protege.web.shared.form.field;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Objects;
import edu.stanford.bmir.protege.web.shared.annotations.GwtSerializationConstructor;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 25 Jun 2017
 */
@JsonTypeName(NumberFieldDescriptor.TYPE)
public class NumberFieldDescriptor implements FormFieldDescriptor {

    protected static final String TYPE = "NUMBER";

    @Nonnull
    private String format = "#.#";

    private int length = 6;

    private String placeholder = "";

    @Nonnull
    private NumberFieldRange range = NumberFieldRange.range(Double.MIN_VALUE,
                                                            NumberFieldRange.BoundType.INCLUSIVE,
                                                            Double.MAX_VALUE,
                                                            NumberFieldRange.BoundType.INCLUSIVE);

    @Nonnull
    private NumberFieldType widgetType = NumberFieldType.PLAIN;

    @GwtSerializationConstructor
    private NumberFieldDescriptor() {
    }

    public NumberFieldDescriptor(@Nonnull String format,
                                 @Nonnull NumberFieldRange range,
                                 @Nonnull NumberFieldType widgetType,
                                 int length,
                                 @Nonnull String placeholder) {
        this.format = checkNotNull(format);
        this.range = checkNotNull(range);
        this.widgetType = checkNotNull(widgetType);
        this.length = length;
        this.placeholder = checkNotNull(placeholder);
    }

    public static String getTypeId() {
        return TYPE;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof NumberFieldDescriptor)) {
            return false;
        }
        NumberFieldDescriptor other = (NumberFieldDescriptor) obj;
        return this.format.equals(other.format)
                && this.range.equals(other.range)
                && this.widgetType.equals(other.widgetType)
                && this.length == other.length
                && this.placeholder.equals(other.placeholder);
    }

    @Nonnull
    @Override
    public String getAssociatedType() {
        return TYPE;
    }

    @Nonnull
    public String getFormat() {
        return format;
    }

    public int getLength() {
        return length;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Nonnull
    public NumberFieldRange getRange() {
        return range;
    }

    @Nonnull
    public NumberFieldType getWidgetType() {
        return widgetType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(format, range, widgetType, length, placeholder);
    }
}
