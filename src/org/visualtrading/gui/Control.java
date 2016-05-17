/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui;

import org.visualtrading.model.Model;
import org.zaval.lw.event.LwActionListener;


public interface Control extends Action {

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    public void process(Object context, Model subject);

// -------------------------- OTHER METHODS --------------------------

    public void addActionListener(LwActionListener a);


    public Action getAction();

    public void removeActionListener(LwActionListener a);

    public void setAction(Action action);
}
