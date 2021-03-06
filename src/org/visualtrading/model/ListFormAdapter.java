/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.visualtrading.gui.widgets.Button;
import org.visualtrading.gui.widgets.Label;
import org.visualtrading.gui.widgets.List;
import org.zaval.lw.LwLabel;
import org.zaval.lw.event.LwActionListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;


public abstract class ListFormAdapter extends AdapterImpl implements LwActionListener {

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

    public ListFormAdapter() {
        super();
        for (int i = 0; i < DEFAULTbUTTONS.length; i++) {
            DEFAULTbUTTONS[i] = getKey() + DEFAULTbUTTONS[i];
        }

    }

    public abstract String getKey();

// --------------------- GETTER / SETTER METHODS ---------------------

    public Object getWorkModel() {
        return workModel;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwActionListener ---------------------

    public void actionPerformed(Object src, Object data) {

        if (src == getSelector()) {
            loadPanel(processSelector(data));
        } else if (src == getDialog()) {
            updateTarget(src, processDialog(src, data));
        } else if (isButton(src)) {
            processButton(src, data);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public Object getDialog() {
        return fields.get(getKey() + "dialog");
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
            backup();
            primeSelector();
            primeForm();
            primeButtons();
        } catch (Error e) {
            e.printStackTrace();

        }
    }


    /**
     *
     */
    public void backup() {
        NewModelImp model = (NewModelImp) getModel();
        workModel = model.clone();
    }

    public abstract Object getModel();

    /**
     *
     */
    public void primeSelector() {
        List selector = getSelector();
        Label label = (Label) selector.get(0);
        selector.removeAll();
        Object[] selection = getOptions();
        for (int i = 0; i < selection.length; i++) {
            addOption(selector, selection[i], label);
        }
        if (selection.length > 0) {
            selector.select(0);
        }
        selector.addSelectionListener(this);

    }


    public List getSelector() {
        Object o = fields.get(getKey() + "selector");
        return (List) o;
    }


    public Object[] getOptions() {

        return ((NewModel) getWorkModel()).keySorted();

    }


    /**
     * @param selector
     * @param object
     * @param label2
     */
    public void addOption(List selector, Object object, Label modelLabel) {
        Label label = new Label((String) object);
        label.setForeground(modelLabel.getForeground());
        label.setBackground(modelLabel.getBackground());
        selector.add(label);
    }

    /**
     *
     */
    public void primeForm() {
        loadPanel(processSelector(null));

    }

    public abstract void loadPanel(Object data);

    public Object processSelector(Object data) {
        return ((NewModel) getWorkModel()).get(getSelectedKey());
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

    public Object processDialog(Object src, Object data) {
        return data;
    }

    /**
     * @param src
     * @param object
     */
    public boolean updateTarget(Object src, Object newValue) {
        Object key = getSelectedKey();
        NewModel model = (NewModel) getWorkModel();
        Object old = model.get(key);
        if (!old.equals(newValue)) {
            model.put(key, newValue);
            return true;
        }
        return false;
    }

    public Object getSelectedKey() {
        LwLabel label = (LwLabel) getSelector().getSelected();
        return label != null ? label.getText() : "";
    }


}





