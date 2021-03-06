/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwFontPanel;


public class FontPanelBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public FontPanelBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        LwFontPanel panel = (LwFontPanel) super.configure(application, obj, xml);
        return panel;
    }

    public Class getClass(String className) {
        return LwFontPanel.class;
    }
}
