/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;

import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Hashtable;


public abstract class ConverterImpl implements Converter {

// --------------------------- CONSTRUCTORS ---------------------------

    public ConverterImpl() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------



    public void addToMapping(Hashtable mappings) {
        mappings.put(tagName(), this);
        mappings.put(getObjClass(), this);
    }

    public String convertFrom(Object object) {
        return object.toString();
    }
    public XMLElement fromObject(Object obj) {
        Object value = obj != null ? obj : getDefault();
        XMLElement xml = new XMLElement();
        xml.setName(tagName());
        xml.setAttribute("value", convertFrom(value));
        return xml;
    }

    public Object fromXml(XMLElement xml) {
        Object value = xml.getAttribute("value");
        if (value == null) {
            return getDefault();
        }
        return convertTo((String) value);
    }


}
