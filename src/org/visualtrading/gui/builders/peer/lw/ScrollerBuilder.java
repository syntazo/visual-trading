/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwScrollPan;


public class ScrollerBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public ScrollerBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        LwPanel composite = (LwPanel) super.configure(application, obj, xml);
        return composite;
    }


    public Class getClass(String className) {
        return LwScrollPan.class;
    }

}
