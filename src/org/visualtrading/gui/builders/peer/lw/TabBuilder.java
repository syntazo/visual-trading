/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Tab;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;


public class TabBuilder extends PanelBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public TabBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        Tab tab = (Tab) super.configure(application, obj, xml);
        tab.setTitle(xml.getStringAttribute("TITLE", tab.getTitle()));
        return tab;
    }

    public Class getClass(String className) {
        return Tab.class;
    }

}
