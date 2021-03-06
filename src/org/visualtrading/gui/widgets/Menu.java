/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LwBorder;
import org.zaval.lw.LwLine;
import org.zaval.lw.LwMenu;


public abstract class Menu extends LwMenu implements MenuItem {

// ------------------------------ FIELDS ------------------------------

    private String title;

// -------------------------- STATIC METHODS --------------------------

    static public Menu build(String classPath, String[] items) {
        Menu menu;
        try {
            menu = (Menu) Class.forName(classPath + "." + items[0] + "Menu").newInstance();
        } catch (Exception e) {

            menu = new SimpleMenu();
        }
        menu.setTitle(items[0]);
        for (int mii = 1; mii < items.length; mii++) {
            if (items[mii].length() > 0) {
                menu.add(SimpleMenuItem.build(classPath, items[mii]));
            } else {
                menu.add(new LwLine(LwBorder.PLAIN), true);
            }
        }
        menu.addSelectionListener(menu);
        return menu;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Menu() {
        super();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getTitle() {
        return title;
    }


    public void setTitle(String label) {
        title = label;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwActionListener ---------------------

    public void actionPerformed(Object src, Object data) {
        Object o = fetchContentComp(((Integer) data).intValue());
        if (o instanceof MenuItem) {
            ((MenuItem) o).actionPerformed(src, data);
        }
    }

}
