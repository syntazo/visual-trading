/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

public class FocusRequesterHelper implements FocusRequester {

// ------------------------------ FIELDS ------------------------------

    private FocusRequester requester;
    private char[] transferChars = new char[3];

// --------------------------- CONSTRUCTORS ---------------------------

    public FocusRequesterHelper() {
        transferChars[0] = 10;
        transferChars[1] = 11;
        transferChars[2] = 13;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface FocusRequester ---------------------


    public FocusRequester getNextFocusRequester() {
        return requester;
    }
    /**
     * This method is not implemented. It is left for the interface implementer
     */
    public void requestFocus() {
    }

    public void setNextFocusRequester(FocusRequester _requester) {
        requester = _requester;
    }

    public void setTabCharacters(char[] chars) {
        if (chars != null) {
            transferChars = chars;
        }
    }

    public void transferFocus() {
        if (requester != null) {
            requester.requestFocus();
        }
    }

    public boolean validateTransfer(char key) {
        for (int i = 0; i < transferChars.length; i++) {
            char transferChar = transferChars[i];
            if (transferChar == key) {
                return true;
            }
        }
        return false;
    }
}
