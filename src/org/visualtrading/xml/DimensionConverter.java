/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;

import java.awt.*;
import java.util.StringTokenizer;


public class DimensionConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static String COMMA = ",";

    final static Dimension DEFAULT = new Dimension(0, 0);

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new DimensionConverter());
    }


    public static Dimension toDimension(String string) {
        StringTokenizer st = new StringTokenizer(string, ",");
        int w = Integer.parseInt((String) st.nextElement());
        int h = Integer.parseInt((String) st.nextElement());
        return new Dimension(w, h);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public DimensionConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public Object convertTo(String value) {
        return toDimension(value);
    }

    public Object getDefault() {
        return DEFAULT;
    }
    public Class getObjClass() {
        return Dimension.class;
    }

    public String tagName() {
        return "Dimension";
    }

// -------------------------- OTHER METHODS --------------------------

/* (non-Javadoc)
 * @see org.visualtrading.xml.Converter#convertFrom(java.lang.Object)
 */
    public String convertFrom(Object object) {
        return fromDimension((Dimension) object);
    }

    public static String fromDimension(Dimension dimension) {
        StringBuffer buff = new StringBuffer();
        buff.append(dimension.width);
        buff.append(COMMA);
        buff.append(dimension.height);
        return buff.toString();
    }

}
