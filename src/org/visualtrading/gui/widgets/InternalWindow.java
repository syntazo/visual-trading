/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.model.Application;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwDesktop;
import org.zaval.lw.LwLayer;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.LwWinLayer;
import org.zaval.lw.LwWindow;
import org.zaval.lw.event.LwMouseEvent;

import java.awt.*;
import java.util.Vector;


public abstract class InternalWindow extends LwWindow implements Widget {

// ------------------------------ FIELDS ------------------------------

    LwDesktop desktop;

    boolean closed = false;

// -------------------------- STATIC METHODS --------------------------

    public static InternalWindow build(Class windowClass, Application applet) {
        InternalWindow w = null;
        try {
            w = (InternalWindow) windowClass.newInstance();
            w.construct(applet);
            w.activate(applet);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return w;
    }

    abstract protected void construct(Application applet);

    protected void activate(Application applet) {
        desktop = LwToolkit.getDesktop(applet.getRoot());
        LwWinLayer wl = (LwWinLayer) desktop.getLayer(LwWinLayer.ID);
        wl.add(LwWinLayer.MDI_WIN, this);
        wl.activate(this);
        int winwidth = getWinWidth();
        setSize(winwidth, getHeight());
    }

    abstract protected int getWinWidth();

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public InternalWindow() {
        this("");
    }

    /**
     * @param arg0
     */
    public InternalWindow(String title) {
        super(title);
        WindowProducer wp = new WindowProducer();
        wp.setTitle(title);
        setWinProducer(wp);
        loadConfig(null);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public boolean isClosed() {
        return closed;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwMouseMotionListener ---------------------

    public void endDragged(LwMouseEvent e) {
        super.endDragged(e);
        resize();
        desktop.invalidate();
    }

// --------------------- Interface LwWinListener ---------------------


    public void winClosed(LwLayer winLayer, LwComponent target) {
        super.winClosed(winLayer, target);
        cleanup();
        closed = true;
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract void resize();


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