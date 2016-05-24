/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.model;

import org.visualtrading.gui.widgets.Button;
import org.visualtrading.gui.widgets.Label;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwLayer;
import org.zaval.lw.LwWindow;
import org.zaval.lw.event.LwActionListener;
import org.zaval.lw.event.LwWinListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;


public abstract class ConfirmAdapter extends AdapterImpl implements LwActionListener, LwWinListener {

// ------------------------------ FIELDS ------------------------------

    /**
     * @param src
     * @param data
     */

    public final static Object[] EMPTY_ARGS = {};
    public final static Class[] EMPTY_ARGS_CLASS = {};
    public final static String PREFIX = "do";


    String[] DEFAULTbUTTONS = {"apply", "save", "cancel"};

    protected Object workModel;

// --------------------------- CONSTRUCTORS ---------------------------

    public ConfirmAdapter() {
        super();
        for (int i = 0; i < DEFAULTbUTTONS.length; i++) {
            DEFAULTbUTTONS[i] = getKey() + DEFAULTbUTTONS[i];
        }

    }

    public abstract String getKey();

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwActionListener ---------------------

    public void actionPerformed(Object src, Object data) {
        if (isButton(src)) {
            processButton(src, data);
        }
    }

// --------------------- Interface LwWinListener ---------------------


    public void winOpened(LwLayer winLayer, LwComponent target) {
    }

    public void winClosed(LwLayer winLayer, LwComponent target) {
        cancel();
    }

    public void winActivated(LwLayer winLayer, LwComponent target) {
    }

    public void winDeactivated(LwLayer winLayer, LwComponent target) {
    }

// -------------------------- OTHER METHODS --------------------------

    public void cancel() {
    }


    public void doCancel() {
        close();
    }

    private boolean isButton(Object obj) {
        return getButtonKey(obj) != null;
    }

    protected Object getButtonKey(Object obj) {
        Object[] keys = getButtonKeys();
        for (int k = 0; k < keys.length; k++) {
            if (fields.get(keys[k]) == obj) {
                return keys[k];
            }
        }
        return null;
    }

    public void prime(Application theApplication, Object obj, Hashtable theFields) {
        try {
            super.prime(theApplication, obj, theFields);
            primeMessage();
            primeButtons();
        } catch (Error e) {
            e.printStackTrace();

        }
    }


    public void primeMessage() {
        if (getMessageField() != null) {
            getMessageField().setText(getMessage());
        }

    }

    public Label getMessageField() {
        return ((Label) fields.get("message"));
    }


    public String getMessage() {
        return "Your Message Goes Here";
    }

    private void primeButtons() {
        Object[] keys = getButtonKeys();
        for (int k = 0; k < keys.length; k++) {
            ((Button) fields.get(keys[k])).addActionListener(this);
        }
    }

    public Object[] getButtonKeys() {
        return DEFAULTbUTTONS;
    }

    public void processButton(Object button, Object data) {
        try {
            Method method = this.getClass().getDeclaredMethod(getMethodName(button), EMPTY_ARGS_CLASS);
            method.invoke(this, EMPTY_ARGS);
            close();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public String getMethodName(Object button) {
        return PREFIX + ((String) getButtonKey(button)).substring(getKey().length());
    }

    public void close() {
        LwWindow dialog = (LwWindow) target;
        ((LwContainer) dialog.getLwParent()).remove(((LwContainer) dialog.getLwParent()).indexOf(dialog));
    }

}





