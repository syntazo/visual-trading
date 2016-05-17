/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.visualtrading.xml.nanoxml.XMLElement;


public interface Configurable {

// -------------------------- OTHER METHODS --------------------------

    public void loadConfig(XMLElement xml);

    public XMLElement saveConfig();

}
