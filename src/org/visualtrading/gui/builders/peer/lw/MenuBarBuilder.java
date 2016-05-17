/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;


import org.visualtrading.gui.widgets.Menu;
import org.visualtrading.gui.widgets.SimpleMenuItem;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwFlowLayout;
import org.zaval.lw.LwMenuBar;
import org.zaval.lw.LwToolkit;


public class MenuBarBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public MenuBarBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public void add(LwContainer container, Object o) {
        LwMenuBar menubar = (LwMenuBar) container;
        Menu menu = (Menu) o;
        menubar.add(new SimpleMenuItem(menu.getTitle()), menu);
    }

    public Object configure(Application application, Object obj, XMLElement xml) {
        LwMenuBar menubar = (LwMenuBar) obj;
        menubar.setInsets(1, 4, 1, 4);
        menubar.setView(LwMenuBar.OFF_BR_VIEW, LwToolkit.getView("br.raised"));
        menubar.setView(LwMenuBar.ON_BR_VIEW, LwToolkit.getView("br.sunken"));
        menubar.setLwLayout(new LwFlowLayout());
        return menubar;
    }

    public Class getClass(String className) {
        return LwMenuBar.class;
    }
}
