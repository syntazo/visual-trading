/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwPanel;

import java.awt.*;


public class PanelBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public PanelBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        LwPanel composite = (LwPanel) super.configure(application, obj, xml);
        composite.setLwLayout(getLayout(xml));
        return composite;
    }

    public Class getClass(String className) {
        return LwPanel.class;
    }

    public void setSize(LwComponent component, XMLElement xml) {
        LwPanel panel = (LwPanel) component;
        Dimension size = getSize(xml, null);
        if (size != null) {
            panel.setPSSize(size.width, size.height);
        }
    }

}
