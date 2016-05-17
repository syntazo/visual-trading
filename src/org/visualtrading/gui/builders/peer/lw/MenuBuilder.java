/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Menu;
import org.visualtrading.gui.widgets.SimpleMenu;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwMenu;


public class MenuBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public MenuBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public void add(LwContainer container, Object o) {
        LwComponent item = (LwComponent) o;
        LwMenu menu = (LwMenu) container;
        menu.add(item, !item.isEnabled());
    }

    public Object configure(Application application, Object obj, XMLElement xml) {
        Menu menu = (Menu) obj;
        menu.setTitle(xml.getStringAttribute("title"));
        return menu;
    }

    public Class getClass(String className) {
        return SimpleMenu.class;
    }

}
