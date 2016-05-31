/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;

import org.visualtrading.model.NewModel;
import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Enumeration;
import java.util.Hashtable;


public class ModelConverter implements Converter {

// --------------------------- CONSTRUCTORS ---------------------------

    public ModelConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------



    public void addToMapping(Hashtable mappings) {
        mappings.put(tagName(), this);
        mappings.put(getObjClass(), this);
        mappings.put(tagName(), this);
    }


    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#convertFrom(java.lang.Object)
     */
    public String convertFrom(Object object) {
        return object.toString();
    }


    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#convertTo(java.lang.String)
     */
    public Object convertTo(String value) {
        return value;
    }

    public XMLElement fromObject(Object obj) {
        XMLElement xml = new XMLElement();
        if (obj != null) {
            NewModel model = (NewModel) obj;
            xml.setName(tagName());
            for (Enumeration e = model.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                xml.addChild(ConverterEngine.toXML("key", key, model.get(key)));
            }
        }
        return xml;
    }

    public Object fromXml(XMLElement xml) {

        NewModel table = null;
        try {
            table = (NewModel) ConverterEngine.getClassFrom(xml).newInstance();
            for (Enumeration e = xml.enumerateChildren(); e.hasMoreElements();) {
                XMLElement element = (XMLElement) e.nextElement();
                table.put(element.getAttribute("key"), ConverterEngine.fromXML(element));

            }
        } catch (InstantiationException e1) {
            // TODO 
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO 
            e1.printStackTrace();
        }

        return table;
    }


    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#getDefault()
     */
    public Object getDefault() {
        return new Hashtable();
    }

    public Class getObjClass() {
        return NewModel.class;
    }
    public String tagName() {
        return "Model1";
    }


}
