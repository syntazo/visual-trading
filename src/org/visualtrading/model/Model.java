/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import java.util.Hashtable;

public interface Model {

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return
     */
    ModelBuilder getBuilder();

    public Hashtable getValues();

}
