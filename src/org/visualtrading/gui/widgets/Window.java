/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.model.Application;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwFrame;
import org.zaval.lw.LwLayer;


public abstract class Window extends LwFrame {

// ------------------------------ FIELDS ------------------------------

    boolean closed = false;

// -------------------------- STATIC METHODS --------------------------

    public static Window build(Class windowClass, Application applet) {
        Window w = null;
        try {
            w = (Window) windowClass.newInstance();
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
        int winwidth = getWinWidth();
        setSize(winwidth, getHeight());
    }

    abstract protected int getWinWidth();


    public int getHeight() {
        return 0;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public Window() {
        super();
    }


    public Window(String title) {
        super();
        setTitle(title);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public boolean isClosed() {
        return closed;
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract void resize();

    public void winClosed(LwLayer winLayer, LwComponent target) {
        closed = true;
    }

}
