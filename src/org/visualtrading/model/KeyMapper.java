/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.visualtrading.gui.events.KeyWatcher;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.event.LwKeyEvent;

import java.util.Enumeration;
import java.util.Vector;


public class KeyMapper extends Mapper implements KeyWatcher {

// --------------------------- CONSTRUCTORS ---------------------------

    public KeyMapper() {
        super("keymap");
        LwToolkit.getEventManager().addKeyListener(this);
    }

    public KeyMapper(Object[] map) {
        super("keymap", map);
        LwToolkit.getEventManager().addKeyListener(this);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwKeyListener ---------------------

    public void keyPressed(LwKeyEvent e) {
//	    System.out.println("PRESSED: "+ new KeyCode(e.getKeyChar(), e.getKeyCode(), e.getMask()));
        Object group = lookupGroup(e.getKeyChar(), e.getKeyCode(), e.getMask());
        if (group != null) {
            KeyCode keyCode = (KeyCode) get(group);
            if (keyCode != null) {
                keyCode.process(this, group);
            }
        }
    }

    public void keyReleased(LwKeyEvent e) {
    }


    public void keyTyped(LwKeyEvent e) {
        //KeyCode  k = new KeyCode(e.getKeyChar(), e.getKeyCode(), e.getMask());
        //System.out.println("TYPED: "+ k);  
        //updateDependants(k);
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @param keyChar
     * @param keyCode
     * @param mask
     *
     * @return
     */
    private Object lookupGroup(char keyChar, int keyCode, int mask) {
        KeyCode k = new KeyCode(keyChar, keyCode, mask);
        for (Enumeration ev = keys(); ev.hasMoreElements();) {
            Object key = ev.nextElement();
            Object keycode = get(key);
            if (keycode.equals(k)) {
                return key;
            }
        }
        return null;
    }


    public void updateDependants(Object group) {
        Vector tonotify = (Vector) dependants.get(group);
        if (tonotify != null) {
            Object keycode = get(group);
            for (Enumeration e = tonotify.elements(); e.hasMoreElements();) {
                Mappable mappable = (Mappable) e.nextElement();
//            	    System.out.println("UPDATING:"+mappable+"<-"+ keycode);
                updateValue(mappable, keycode, group);
            }
        }
    }


    /* (non-Javadoc)
     * @see org.visualtrading.model.Mapper#updateValue(org.visualtrading.model.Mappable, java.lang.Object, java.lang.Object)
     */
    public void updateValue(Mappable mappable, Object keyCode, Object key) {
        ((KeyUser) mappable).keyTyped(keyCode, key);

    }


}
