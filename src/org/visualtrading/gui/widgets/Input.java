/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.data.SingleLineTxt;
import org.zaval.lw.LwTextField;
import org.zaval.lw.event.LwKeyEvent;

import java.awt.event.KeyEvent;


public class Input extends LwTextField {

// ------------------------------ FIELDS ------------------------------

    protected Object value;

// --------------------------- CONSTRUCTORS ---------------------------

    public Input() {
        this("");
    }

    public Input(Object initValue) {
        this(initValue, "");
        value = initValue;
        super.setText(toString(initValue));

    }

    public String toString(Object object) {
        return object == null ? "":object.toString();
    }

    public Input(Object initValue, String toshow) {
        super(new SingleLineTxt(toshow));

        value = initValue;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Object getValue() {
        return value;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwKeyListener ---------------------

    public void keyPressed(LwKeyEvent e) {
        super.keyPressed(e);
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE) {
            String text = getText();
            int pos = getPosController().getOffset();
            prepare(pos, text);
            rework(pos, text);
        }
    }

    public void keyTyped(LwKeyEvent e) {
        char ch = e.getKeyChar();
        String text = getText();
        int pos = getPosController().getOffset();
        prepare(pos, text);
        if (isValidCh(ch)) {
            super.keyTyped(e);
        }
        rework(pos, text);
    }

// -------------------------- OTHER METHODS --------------------------

    protected boolean isValidCh(char ch) {
        return true;
    }


    protected void prepare(int prevPos, String prevText) {

        prevText.trim();
        setText(prevText);
        getPosController().seek(prevPos);

    }

    protected void rework(int prevPos, String prevText) {
        try {
            value = fromString(getText());
        } catch (NumberFormatException exc) {

        }
    }

    public Object fromString(String string) {
        return value.toString();
    }

    public void setValue(Object object) {
        value = object;
        if (value != null) {
            setText(toString(object));
        } else {
            setText("");
        }
    }

    public void setText(String text) {
        super.setText(text.trim());
    }


}
