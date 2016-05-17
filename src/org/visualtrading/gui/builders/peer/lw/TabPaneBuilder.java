/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Tab;
import org.visualtrading.gui.widgets.TabPane;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwContainer;


public class TabPaneBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public TabPaneBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public void add(LwContainer container, Object o) {
        Tab tab = (Tab) o;
        TabPane tabpane = (TabPane) container;
        tabpane.addPage(tab.getTitle(), tab);
    }

    public Object configure(Application application, Object obj, XMLElement xml) {
        TabPane tabpane = (TabPane) super.configure(application, obj, xml);
        return tabpane;
    }


    public Class getClass(String className) {
        return TabPane.class;
    }
}
