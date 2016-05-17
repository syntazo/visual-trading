/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

public interface FocusRequester {

// -------------------------- OTHER METHODS --------------------------

    FocusRequester getNextFocusRequester();

    void requestFocus();

    void setNextFocusRequester(FocusRequester requester);

    void setTabCharacters(char[] chars);

    void transferFocus();

    boolean validateTransfer(char key);

}

