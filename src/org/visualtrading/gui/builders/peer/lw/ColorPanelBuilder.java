/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.ColorPanel;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;


public class ColorPanelBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public ColorPanelBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        ColorPanel panel = (ColorPanel) super.configure(application, obj, xml);
        return panel;
    }

    public Class getClass(String className) {
        return ColorPanel.class;
    }
}
