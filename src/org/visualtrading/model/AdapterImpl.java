/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.model;

import java.util.Hashtable;


public class AdapterImpl implements Adapter {

// ------------------------------ FIELDS ------------------------------

    protected Hashtable fields;
    protected Application application;
    protected Object target;

// --------------------------- CONSTRUCTORS ---------------------------

    public AdapterImpl() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Adapter ---------------------

    public void prime(Application theApplication, Object obj, Hashtable theFields) {
        fields = theFields;
        application = theApplication;
        target = obj;

    }

}
