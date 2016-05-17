/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.builders.Builder;
import org.visualtrading.gui.widgets.WindowProducer;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwDesktop;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.LwWinLayer;
import org.zaval.lw.LwWindow;


public class DialogBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public DialogBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        LwWindow window = (LwWindow) super.configure(application, obj, xml);
        WindowProducer wp = new WindowProducer();
        boolean modal = Builder.getBoolean(xml, "modal", false);
        wp.setTitle(xml.getStringAttribute("title", ""));
        window.setWinProducer(wp);
        LwDesktop desktop = LwToolkit.getDesktop(application.getRoot());
        LwWinLayer wl = (LwWinLayer) desktop.getLayer(LwWinLayer.ID);
        wl.add(modal ? LwWinLayer.MODAL_WIN : LwWinLayer.MDI_WIN, window);
        window.getRoot().setLwLayout(getLayout(xml));
        wl.activate(window);
        return window;
    }

    public Class getClass(String className) {
        return LwWindow.class;
    }
}
