/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwGridLayout;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwPanel;

import java.awt.*;
import java.util.Vector;


public class Panel extends LwPanel implements Widget {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public Panel() {
        super();
        setOpaque(false);
        //init(new LwGridLayout(1, 1));
    }


    public Panel(LwLayout layout) {
        super();
        init(layout);
    }

    public void init(LwLayout layout) {

        setOpaque(false);
        setLwLayout(layout);
    }

    public Panel(int rows, int cols) {
        super();
        init(new LwGridLayout(rows, cols));
    }

    public Panel(int rows, int cols, int mask) {
        super();
        init(new LwGridLayout(rows, cols, mask));
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface Widget ---------------------

    public void cleanup() {
    }

// -------------------------- OTHER METHODS --------------------------

    public /*C#override*/ void setBackground(Color c) {
        setBackground(c, false);
    }

    public /*C#override*/ void setBackground(Color c, boolean deep) {

        Vector kids = children;

        if (!deep) {
            children = new Vector();
            super.setBackground(c);
            children = kids;
            invalidate();
        } else {
            super.setBackground(c);
        }
    }
}
