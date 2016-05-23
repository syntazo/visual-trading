/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo llc
 *
 * @author thanos vassilakis, yarik chinskiy
 *
 */
package org.visualtrading.gui;

import org.visualtrading.model.Model;


public interface Action {

// -------------------------- OTHER METHODS --------------------------

    public void process(Object context, Model subject);
}
