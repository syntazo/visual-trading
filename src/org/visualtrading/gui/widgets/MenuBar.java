/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


import org.visualtrading.gui.Config;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwDesktop;
import org.zaval.lw.LwFlowLayout;
import org.zaval.lw.LwImage;
import org.zaval.lw.LwImgRender;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwMenuBar;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwToolkit;


public class MenuBar extends LwMenuBar {

// ------------------------------ FIELDS ------------------------------

    protected String[][] menus;

// -------------------------- STATIC METHODS --------------------------

    public static MenuBar build(Application gui, String classPath, String[][] _menus) {
        MenuBar bar = new MenuBar(gui, classPath, _menus);
        bar.loadConfig(null);

        return bar;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public MenuBar(Application gui, String classPath, String[][] _menus) {
        super();
        menus = _menus;
        LwContainer panel = gui.getRoot();
        setLocation(1, panel.getY() + 1);
        setInsets(1, 4, 1, 4);
        setView(LwMenuBar.OFF_BR_VIEW, LwToolkit.getView("br.raised"));
        setView(LwMenuBar.ON_BR_VIEW, LwToolkit.getView("br.sunken"));
        loadConfig(null);
        setLwLayout(new LwFlowLayout());
        addMenus(classPath);
    }

    public void loadConfig(XMLElement xml) {
        setBackground(Config.menuBarColor);
    }



    protected void addMenus(String classPath) {
        for (int mi = 0; mi < menus.length; mi++) {
            Menu menu = Menu.build(classPath, menus[mi]);
            add(makeMenuItem(menu.getTitle(), null), menu);
        }

    }


    private static LwComponent makeMenuItem(String lab, String img) {
        LwLabel item = new LwLabel(lab);
        item.setOpaque(false);
        if (img == null) {
            return item;
        }
        LwPanel pan = new LwPanel();
        pan.setLwLayout(new LwFlowLayout());
        pan.add(new LwImage((LwImgRender) LwToolkit.getView(img)));
        pan.add(item);
        pan.setOpaque(false);
        return pan;
    }

// -------------------------- OTHER METHODS --------------------------

    /* (non-Javadoc)
     * @see org.visualtrading.xml.XmlObject#configure(nanoxml.XMLElement)
     */
    public void configure(XMLElement xml) {


    }


    public void configure(LwDesktop desktop) {
        for (int mi = 0; mi < menus.length; mi++) {
            ((Menu) getMenu((LwComponent) get(mi))).configure(desktop);
        }
    }

    public int getMenuIndex(String title) {
        for (int mi = 0; mi < menus.length; mi++) {
            if (menus[mi][0].equals(title)) {
                return mi;
            }
        }
        return -1;
    }


    public XMLElement saveConfig() {
        return null;

    }


}
