/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;


public class IntegerConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static Integer DEFAULT = new Integer(1);

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new IntegerConverter());
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public IntegerConverter() {
        super();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public Object convertTo(String value) {

        return new Integer(value);
    }

    public Object getDefault() {
        return DEFAULT;
    }

    public Class getObjClass() {
        return Integer.class;
    }
    public String tagName() {
        return "Integer";
    }

}
