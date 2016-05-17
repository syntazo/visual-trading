/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;


public abstract class AdapterBuilder extends org.visualtrading.gui.builders.AdpaterBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public AdapterBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        obj = super.configure(application, obj, xml);

        return obj;
    }
}
