/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui;

import org.visualtrading.model.Model;


public class ControlImplementor {

// ------------------------------ FIELDS ------------------------------

    Action action;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public ControlImplementor() {
        super();

    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Action getAction() {
        return action;
    }


    public void setAction(Action anAction) {
        action = anAction;

    }

// -------------------------- OTHER METHODS --------------------------

    public void process(Object context, Model subject) {
        if (action != null) {
            action.process(context, subject);
        }

    }

}
