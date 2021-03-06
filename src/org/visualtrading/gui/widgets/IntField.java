/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


class IntField extends Input {

// --------------------------- CONSTRUCTORS ---------------------------
    public static final Integer ZERO = new Integer(0);

    public IntField() {
        super(new Integer(0), "");
    }

// -------------------------- OTHER METHODS --------------------------

    public Object fromString(String string) {
        try {
            return new Integer(string);
        } catch (NumberFormatException exc) {
            return null;
        }
    }

    protected boolean isValidCh(char ch) {
        if (Character.isDigit(ch)) {
            String text = getText();
            return !(text.length() == 0 && ch == '0');
        }
        return false;
    }

    protected void rework() {

    }

    protected void rework(int prevPos, String prevText) {
        super.rework(prevPos, prevText);
        if (value == null) {
            value = ZERO;
        }
    }


}