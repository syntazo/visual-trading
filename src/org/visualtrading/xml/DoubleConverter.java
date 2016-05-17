/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;

import com.ibm.math.BigDecimal;
import org.visualtrading.xml.nanoxml.XMLElement;


public class DoubleConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static BigDecimal DEFAULT = new BigDecimal(0);

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new DoubleConverter());
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public DoubleConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public Object convertTo(String value) {

        return new BigDecimal(value);
    }

    public Object getDefault() {
        return DEFAULT;
    }
    public Class getObjClass() {
        return BigDecimal.class;
    }

    public String tagName() {
        return "Double";
    }

// -------------------------- OTHER METHODS --------------------------

    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#convertFrom(java.lang.Object)
     */
    public String convertFrom(Object object) {
        return ((BigDecimal) object).toString();
    }

    public Object fromXml1(XMLElement xml) {

        double value = xml.getDoubleAttribute("value", 0);
        return new BigDecimal(value);
    }
}
