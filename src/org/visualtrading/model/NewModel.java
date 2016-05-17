/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.visualtrading.xml.XMLObj;

import java.util.Dictionary;
import java.util.Enumeration;


public interface NewModel extends XMLObj, Cloneable {

// -------------------------- OTHER METHODS --------------------------

    public void clear();

    public boolean containsKey(Object key);

    public void copyFrom(Object source);

    public void copyTo(Object destination);

    public Enumeration elements();

    public Object get(Object key);

    public Object getId();

    public Dictionary getMap();

    public boolean isEmpty();

    public Object[] keySorted();

    public Enumeration keys();

    public Object put(Object key, Object value);

    public Object remove(Object key);

    public void setId(Object key);

    public int size();
}
