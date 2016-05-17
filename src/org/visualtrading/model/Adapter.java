/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import java.util.Hashtable;


public interface Adapter {

// ------------------------------ FIELDS ------------------------------

    final static String TAG = "Adapter";

// -------------------------- OTHER METHODS --------------------------

    public void prime(Application application, Object obj, Hashtable fields);
}
