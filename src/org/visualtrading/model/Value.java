/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;


public interface Value {

// -------------------------- OTHER METHODS --------------------------

    public String getId();

    public Object getValue();

    public void setId(String id);

    public void setValue(Object value);
}
