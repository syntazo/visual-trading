/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Input;
import org.visualtrading.gui.widgets.KeyCodeField;


public class FieldBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public FieldBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

/* (non-Javadoc)
 * @see org.visualtrading.gui.builders.Builder#getClass(java.lang.String)
 */
    public Class getClass(String className) {
        if (className == null) {
            return Input.class;
        }
        if (className.equalsIgnoreCase("keycode")) {
            return KeyCodeField.class;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Input.class;
    }

}
