/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.List;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;


public class ListBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public ListBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {

        List composite = (List) super.configure(application, obj, xml);
        return composite;
    }


    public Class getClass(String className) {
        return List.class;
    }

}
