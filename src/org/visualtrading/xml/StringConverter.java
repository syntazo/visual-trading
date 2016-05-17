/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;


public class StringConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static String DEFAULT = "";

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new StringConverter());
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public StringConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public Object convertTo(String value) {
        return value;
    }

    public Object getDefault() {
        return DEFAULT;
    }

    public Class getObjClass() {
        return String.class;
    }
    public String tagName() {
        return "String";
    }

}
