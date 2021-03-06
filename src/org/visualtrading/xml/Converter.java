/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;

import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Hashtable;


public interface Converter {

// -------------------------- OTHER METHODS --------------------------

    public void addToMapping(Hashtable mapping);

    public String convertFrom(Object object);

    public Object convertTo(String value);

    public XMLElement fromObject(Object obj);

    public Object fromXml(XMLElement xml);

    public Object getDefault();

    public Class getObjClass();

    public String tagName();
}
