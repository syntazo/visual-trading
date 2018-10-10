/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.gui.widgets;

import com.ibm.math.BigDecimal;


class DoubleField extends Input {
     public static final BigDecimal ZERO = new BigDecimal(0);
// --------------------------- CONSTRUCTORS ---------------------------

    public DoubleField() {
        this("0.0");
    }

    public DoubleField(String text) {
        super(ZERO, text);
    }

// -------------------------- OTHER METHODS --------------------------

    public Object fromString(String string) {
        return new BigDecimal(string);
    }

    protected boolean isValidCh(char ch) {
        String text = getText();
        int pos = getPosController().getOffset();
        boolean hasPoint = (text.indexOf('.') > -1);
        boolean hasPlus = (text.indexOf('+') > -1);
        if (Character.isDigit(ch) && ch != '0' && !hasPlus) {
            return true;
        }
        if (ch == '.' && !hasPoint && !hasPlus) {
            return true;
        }
        if (ch == '0' && text.length() > 0 && !hasPlus) {
            return true;
        }
        if (pos == text.length() && ch == '+' && !hasPlus) {
            return true;
        }
        return false;
    }

    protected void rework(int prevPos, String prevText) {
        super.rework(prevPos, prevText);
        if (value == null) {
            value = ZERO;
        }
    }


/* (non-Javadoc)
 * @see org.visualtrading.gui.widgets.Input#setValue(java.lang.Object)
 */
    public void setValue(Object object) {
        if (object instanceof BigDecimal) {
            super.setValue(object);
        }
    }

  
/* (non-Javadoc)
 * @see org.visualtrading.gui.widgets.Input#toString(java.lang.Object)
 */
    public String toString(Object object) {
        return object != null ? ((BigDecimal) object).format(-1, 2) : "";
    }

}