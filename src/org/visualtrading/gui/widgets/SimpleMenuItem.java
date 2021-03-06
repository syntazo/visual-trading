/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LwDesktop;
import org.zaval.lw.LwLabel;


public class SimpleMenuItem extends LwLabel implements MenuItem {

// -------------------------- STATIC METHODS --------------------------

    static public MenuItem build(String classPath, String text) {
        MenuItem item;
        try {
            item = (MenuItem) Class.forName(classPath + "." + text).newInstance();
            item.setTitle(item.getTitle());
        } catch (Exception e) {
            item = new SimpleMenuItem(text);
        }
        return item;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public SimpleMenuItem() {
        super();

    }

    public SimpleMenuItem(String t) {
        super(t);

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwActionListener ---------------------


    /* (non-Javadoc)
     * @see org.zaval.lw.event.LwActionListener#actionPerformed(java.lang.Object, java.lang.Object)
     */
    public void actionPerformed(Object src, Object data) {

    }

// --------------------- Interface MenuItem ---------------------

    public void configure(LwDesktop desktop) {
        setText(getTitle());

    }

    public String getTitle() {
        return getText();
    }


    public void setTitle(String label) {
        setText(label);
    }

// -------------------------- OTHER METHODS --------------------------

    public void configure(String label) {
        setText(label);

    }


}
