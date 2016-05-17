/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;

import org.visualtrading.model.KeyCode;
import org.visualtrading.xml.nanoxml.XMLElement;


public class KeyConverter extends ConverterImpl {

// --------------------------- CONSTRUCTORS ---------------------------

    public KeyConverter() {
        super();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------



    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#convertTo(java.lang.String)
     */
    public Object convertTo(String value) {
        return null;
    }


    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#getDefault()
     */
    public Object getDefault() {
        return null;
    }
    public Class getObjClass() {
        return KeyCode.class;
    }

// -------------------------- OTHER METHODS --------------------------

    public XMLElement fromObject(Object obj) {
        KeyCode keyCode = (KeyCode) obj;
        XMLElement xml = new XMLElement();
        xml.setAttribute("ch", "" + keyCode.ch);
        xml.setIntAttribute("code", keyCode.code);
        xml.setIntAttribute("mask", keyCode.mask);
        xml.setName(tagName());
        return xml;
    }

    public String tagName() {
        return "KeyCode";
    }

    public Object fromXml(XMLElement xml) {
        char ch = xml.getStringAttribute("ch").charAt(0);
        int code = xml.getIntAttribute("code");
        int mask = xml.getIntAttribute("mask");
        return new KeyCode(ch, code, mask);
    }
}
