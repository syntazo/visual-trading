/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;

import org.visualtrading.xml.nanoxml.XMLElement;


public interface XMLObj {

// -------------------------- OTHER METHODS --------------------------

    void fromXML(XMLElement xml);

    XMLElement toXML();
}
