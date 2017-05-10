//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.11 at 12:12:43 AM CEST 
//


package com.osa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchBy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="searchBy">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cities"/>
 *     &lt;enumeration value="stations"/>
 *     &lt;enumeration value="mixed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "searchBy")
@XmlEnum
public enum SearchBy {

    @XmlEnumValue("cities")
    CITIES("cities"),
    @XmlEnumValue("stations")
    STATIONS("stations"),
    @XmlEnumValue("mixed")
    MIXED("mixed");
    private final String value;

    SearchBy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchBy fromValue(String v) {
        for (SearchBy c: SearchBy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
