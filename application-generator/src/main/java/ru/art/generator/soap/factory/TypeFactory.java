package ru.art.generator.soap.factory;

import com.predic8.schema.*;
import com.predic8.schema.restriction.facet.*;
import groovy.xml.*;
import lombok.*;
import lombok.experimental.*;
import ru.art.generator.exception.*;
import ru.art.generator.soap.model.Restriction;
import ru.art.generator.soap.model.Restriction.*;
import ru.art.generator.soap.model.*;
import static ru.art.generator.soap.constants.Constants.SupportJavaType.*;
import java.util.*;

@UtilityClass
public class TypeFactory {

    public static Class<? extends Object> getTypeByString(String type) {
        switch (type) {
            case STRING:
                return String.class;
            case BYTE:
                return Byte.class;
            case BOOLEAN:
                return Boolean.class;
            case FLOAT:
                return Float.class;
            case DOUBLE:
                return Double.class;
            case DECIMAL:
                return Double.class;
            case LONG:
                return Long.class;
            case INT:
                return Integer.class;
            case INTEGER:
                return Integer.class;
            case DATE_TIME:
                return Date.class;
            case TIME:
                return Date.class;
            case DATE:
                return Date.class;
            default:
                return Object.class;
        }
    }

    public static String getTypeByElement(Element element) {
        if (element.getType() == null) {
            if (element.getEmbeddedType() != null) {
                if (element.getEmbeddedType().getQname() != null) {
                    return element.getEmbeddedType().getQname().getLocalPart();
                }
            } else if (element.getRef() != null) {
                return element.getRef().getLocalPart();
            }
        } else {
            return element.getType().getLocalPart();
        }
        return "Object";
    }

    @SneakyThrows
    public static TypeDefinition getTypeDefinition(Element element) {
        if (element.getType() == null) {
            if (element.getEmbeddedType() != null) {
                return element.getEmbeddedType();
            } else if (element.getRef() != null) {
                QName ref = element.getRef();
                if (ref.getNamespaceURI() != null) {
                    Element refElement = element.getSchema().getElement(ref);
                    element.setName(refElement.getName());
                    return getTypeDefinition(refElement);
                }
                if (ref.getPrefix() == null || ref.getPrefix().isEmpty()) {
                    throw new NotFoundPrefixException("Not fount prefix for ref about elememt "
                            + element.getName());
                }
                String namespace = element.getSchema().getNamespace(ref.getPrefix()).toString();
                QName qName = new QName(namespace, ref.getLocalPart(), ref.getPrefix());
                element.setName(ref.getLocalPart());
                return element.getSchema().getType(qName);
            }
        }
        String localPart = getTypeByElement(element);
        return element.getSchema().getType(localPart);
    }

    public static String getNamespaceByPrefix(Element element, String prefix) {
        return element.getSchema().getNamespace(prefix).toString();
    }

    public static Restriction getRestrictionByFacet(Facet facet) {
        RestrictionBuilder builder = Restriction.builder();
        builder.value(facet.getValue());

        if (facet instanceof MinLengthFacet) {
            builder.operation(RestrictionOperation.MIN_LENGTH);
        } else if (facet instanceof FractionDigits) {
            builder.operation(RestrictionOperation.FRACTION_DIGITS);
        } else if (facet instanceof TotalDigitsFacet) {
            builder.operation(RestrictionOperation.TOTAL_DIGITS);
        } else if (facet instanceof EnumerationFacet) {
            builder.operation(RestrictionOperation.ENUMERATION);
        } else if (facet instanceof LengthFacet) {
            builder.operation(RestrictionOperation.LENGTH);
        } else if (facet instanceof MaxExclusiveFacet) {
            builder.operation(RestrictionOperation.MAX_EXCLUSIVE);
        } else if (facet instanceof PatternFacet) {
            builder.operation(RestrictionOperation.PATTERN);
        } else if (facet instanceof MaxLengthFacet) {
            builder.operation(RestrictionOperation.MAX_LENGTH);
        } else if (facet instanceof MinExclusiveFacet) {
            builder.operation(RestrictionOperation.MIN_EXCLUSIVE);
        } else if (facet instanceof MaxInclusiveFacet) {
            builder.operation(RestrictionOperation.MAX_INCLUSIVE);
        } else if (facet instanceof MinInclusiveFacet) {
            builder.operation(RestrictionOperation.MIN_INCLUSIVE);
        } else if (facet instanceof WhiteSpaceFacet) {
            builder.operation(RestrictionOperation.WHITESPACE);
        }
        return builder.build();
    }


    public static boolean isObject(Class classString) {
        return classString.getTypeName().equals(Object.class.getName());
    }
}