package com.osa.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType
public enum SearchBy {
    cities, stations, mixed;
}
