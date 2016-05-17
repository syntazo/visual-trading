/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;


public class IntegerValue implements Value {

// ------------------------------ FIELDS ------------------------------

    Object myValue;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public IntegerValue() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Value ---------------------

    public Object getValue() {
        return myValue;
    }

/* (non-Javadoc)
 * @see org.visualtrading.model.Value#setValue(java.lang.Object)
 */
    public void setValue(Object value) {
        myValue = value;

    }

/* (non-Javadoc)
 * @see org.visualtrading.model.Value#getId()
 */
    public String getId() {

        return null;
    }

/* (non-Javadoc)
 * @see org.visualtrading.model.Value#setId(java.lang.String)
 */
    public void setId(String id) {


    }

}
