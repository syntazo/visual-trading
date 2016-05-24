/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.model;

import org.visualtrading.xml.ConverterEngine;
import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Enumeration;


public class CompoundKeyCode extends KeyCode {

// ------------------------------ FIELDS ------------------------------

    /**
     * 
     */
    Object[] actions;

// --------------------------- CONSTRUCTORS ---------------------------

    public CompoundKeyCode() {
        super();
    }

    public CompoundKeyCode(Object[] actions) {
        super();
        this.actions = actions;
    }

// -------------------------- OTHER METHODS --------------------------

    public void fromXML(XMLElement xml) {
        super.fromXML(xml);
        actions = new Object[xml.countChildren()];
        Enumeration e = xml.enumerateChildren();
        for (int i = 0; e.hasMoreElements(); i++) {
            XMLElement child = (XMLElement) e.nextElement();
            actions[i] = ConverterEngine.fromXML(child);
        }
    }

    public String getTag() {
        return "CompoundKeyCode";
    }


    public void process(KeyMapper map, Object key) {
        for (int i = 0; i < actions.length; i++) {
            map.updateDependants(actions[i]);
        }
    }

    public XMLElement toXML() {
        XMLElement xml = super.toXML();
        if (xml != null) {
            xml.setAttribute("class", this.getClass().getName());
            for (int i = 0; i < actions.length; i++) {
                XMLElement child = ConverterEngine.toXML(actions[i]);

                xml.addChild(child);
            }
        }
        return xml;
    }
}
