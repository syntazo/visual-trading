/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LwComponent;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwDesktop;
import org.zaval.lw.LwImage;
import org.zaval.lw.LwLayer;
import org.zaval.lw.LwToolkit;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Cadre
        extends Frame
        implements FocusListener, WindowListener {

// ------------------------------ FIELDS ------------------------------

    private LwDesktop root;

// --------------------------- CONSTRUCTORS ---------------------------

    public Cadre(String title) {
        super(title);
        setLayout(new BorderLayout());
        root = LwToolkit.createDesktop();
        add(BorderLayout.CENTER, (Component) root.getNCanvas());
        addFocusListener(this);
        addWindowListener(this);
        LwImage image = new LwImage("/btecwin.gif");
        setIconImage(image.getImage());

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface FocusListener ---------------------

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the specified focus event.
     */
    public void focusGained(FocusEvent e) {
        ((Component) root.getNCanvas()).requestFocus();
    }

/* (non-Javadoc)
 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
 */
    public void focusLost(FocusEvent e) {

    }

// --------------------- Interface WindowListener ---------------------


/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
 */
    public void windowOpened(WindowEvent e) {
        System.out.println("windowOpened()");

    }

/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
 */
    public void windowClosing(WindowEvent e) {
        System.out.println("windowClosing()");
        dispose();

    }

/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
 */
    public void windowClosed(WindowEvent e) {
        System.out.println("windowClosed");
    }

/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
 */
    public void windowIconified(WindowEvent e) {
        System.out.println("windowIconified()");

    }

/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
 */
    public void windowDeiconified(WindowEvent e) {
        System.out.println("windowDeiconified()");

    }

/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 */
    public void windowActivated(WindowEvent e) {
        System.out.println("windowActivated()");

    }

/* (non-Javadoc)
 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
 */
    public void windowDeactivated(WindowEvent e) {
        System.out.println("windowDeactivated()");

    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Gets the root light weight component that has to be used as a top-level container for other light weight
     * components.
     *
     * @return a root light weight component.
     */
    public LwContainer getRoot() {
        return root.getRootLayer();
    }

    public boolean handleEvent(Event event) {
        if (event.id == Event.WINDOW_DESTROY) {
            dispose();
        }
        return super.handleEvent(event);
    }

    /**
     * Ensures that a component has a valid layout. This method is primarily intended to operate on Container
     * instances.
     */
    public void validate() {
        super.validate();
        //root.validate();
    }

/* (non-Javadoc)
 * @see org.zaval.lw.event.LwWinListener#winActivated(org.zaval.lw.LwLayer, org.zaval.lw.LwComponent)
 */
    public void winActivated(LwLayer winLayer, LwComponent target) {
        System.out.println("winActivated()");

    }

/* (non-Javadoc)
 * @see org.zaval.lw.event.LwWinListener#winClosed(org.zaval.lw.LwLayer, org.zaval.lw.LwComponent)
 */
    public void winClosed(LwLayer winLayer, LwComponent target) {
        System.out.println("winClosed()");

    }

/* (non-Javadoc)
 * @see org.zaval.lw.event.LwWinListener#winDeactivated(org.zaval.lw.LwLayer, org.zaval.lw.LwComponent)
 */
    public void winDeactivated(LwLayer winLayer, LwComponent target) {
        System.out.println("winDeactivated()");

    }

/* (non-Javadoc)
 * @see org.zaval.lw.event.LwWinListener#winOpened(org.zaval.lw.LwLayer, org.zaval.lw.LwComponent)
 */
    public void winOpened(LwLayer winLayer, LwComponent target) {
        System.out.println("winOpened()");

    }

}



