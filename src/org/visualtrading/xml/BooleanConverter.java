/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;


public class BooleanConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static Boolean DEFAULT = Boolean.FALSE;

    static final String[] truevalues = {"TRUE", "ON", "YES"};
    static final String[] falsevalues = {"FALSE", "OFF", "NO"};

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new BooleanConverter());
    }


    static public Boolean toBoolean(String string) {
        if (string != null) {
            for (int i = 0; i < truevalues.length; i++) {
                if (string.equalsIgnoreCase(truevalues[i])) {
                    return Boolean.TRUE;
                }
            }
            for (int i = 0; i < falsevalues.length; i++) {
                if (string.equalsIgnoreCase(falsevalues[i])) {
                    return Boolean.FALSE;
                }
            }
        }
        return null;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public BooleanConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public String tagName() {
        return "Boolean";
    }
    public Class getObjClass() {
        return Boolean.class;
    }

    public Object convertTo(String value) {
        Boolean result = toBoolean(value);
        return result != null ? result : DEFAULT;
    }

    public Object getDefault() {
        return DEFAULT;
    }


}
