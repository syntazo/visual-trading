/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.data.SingleLineTxt;
import org.zaval.lw.LwTextField;
import org.zaval.lw.event.LwKeyEvent;


public class TextField extends LwTextField implements Widget, FocusRequester {

// ------------------------------ FIELDS ------------------------------

    private FocusRequester helper = new FocusRequesterHelper();

// --------------------------- CONSTRUCTORS ---------------------------

    public TextField() {

        super(new SingleLineTxt(""));

    }

    public TextField(String s, int i) {

        super(s, i);

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface FocusRequester ---------------------


    public FocusRequester getNextFocusRequester() {

        return helper.getNextFocusRequester();

    }

    public void setNextFocusRequester(FocusRequester requester) {

        helper.setNextFocusRequester(requester);

    }

    public void setTabCharacters(char[] chars) {

        helper.setTabCharacters(chars);

    }

    public void transferFocus() {

        helper.transferFocus();

    }

    public boolean validateTransfer(char key) {

        return helper.validateTransfer(key);

    }

// --------------------- Interface LwKeyListener ---------------------


    public void keyPressed(LwKeyEvent e) {
        super.keyPressed(e);
        if (validateTransfer(e.getKeyChar())) {
            transferFocus();
        }
    }

// --------------------- Interface Widget ---------------------

    public void cleanup() {
    }

// -------------------------- OTHER METHODS --------------------------

    public void setText(String s) {
        if (s == null) {
            s = "";
        }
        super.setText(s);
    }

}

