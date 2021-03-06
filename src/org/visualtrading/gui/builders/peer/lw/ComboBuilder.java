/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwCombo;


public class ComboBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public ComboBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object addChildren(Application application, Object obj, XMLElement xml) {
        LwCombo combo = (LwCombo) obj;
        super.addChildren(application, combo.getList(), xml);
        return combo;
    }


/* (non-Javadoc)
 * @see org.visualtrading.gui.builders.Builder#getClass(java.lang.String)
 */
    public Class getClass(String className) {
        return LwCombo.class;
    }
}
