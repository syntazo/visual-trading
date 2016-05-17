/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.zaval.lw.LwComposite;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public abstract class Mapper extends NewModelImp {

// ------------------------------ FIELDS ------------------------------

    static final String DEPENDANTS = "MapperDependants";

    final Hashtable dependants = getDependants();

// -------------------------- STATIC METHODS --------------------------

    public static synchronized Hashtable getDependants() {
        Hashtable table = (Hashtable) StaticTable.get(DEPENDANTS);
        if (table == null) {
            StaticTable.put(DEPENDANTS, table = new Hashtable());
        }
        System.out.println("dependants:" + table);
        return table;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Mapper(Object id) {
        this(id, new Object[]{});
    }


    public Mapper(Object id, Object[] mapping) {
        super(id, mapping);
        
//        dependants = (Hashtable) StaticTable.get(DEPENDANTS);
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * 
     */
    public void clear() {
        super.clear();
        dependants.clear();
    }

    public Object get(Object key, Object defaultValue) {
        Object value = get(key);
        if (value == null) {
            value = defaultValue;
            put(key, value);
        }
        return value;
    }


    public Object put(Object key, Object Value) {
        if (!dependants.containsKey(key)) {
            dependants.put(key, new Vector());
        }
        return super.put(key, Value);
    }


    public Object get(Object key, Object defaultValue, Mappable mappable) {
        Object o = get(key, defaultValue);
        subscribe(key, mappable);
        return o;
    }

    public void subscribe(Object key, Mappable mappable) {
        if (((Vector) dependants.get(key)).indexOf(mappable) == -1) {
            ((Vector) dependants.get(key)).addElement(mappable);
        }
    }


    public Object remove(Object key) {
        if (containsKey(key)) {
            dependants.remove(key);
            return remove(key);
        }
        return null;
    }


    public void removeDependant(Mappable mappable, boolean deep) {
        for (Enumeration ek = dependants.keys(); ek.hasMoreElements();) {
            removeDependant(ek.nextElement(), mappable, deep);
        }
    }

    public void removeDependant(Object group, Mappable mappable, boolean deep) {
        if (deep && mappable instanceof LwComposite) {
            cleanComposite(group, (LwComposite) mappable, deep);
        }
        ((Vector) dependants.get(group)).removeElement(mappable);
        //System.out.println("REMOVED: "+ group+", "+mappable);
    }

    public void cleanComposite(Object group, LwComposite container, boolean deep) {
        for (int ci = 0; ci < container.count(); ci++) {
            Object child = container.get(ci);
            if (child instanceof Mappable) {
                removeDependant(group, (Mappable) child, deep);
            } else if (child instanceof LwComposite) {
                cleanComposite(group, (LwComposite) child, deep);
            }
        }
    }

    /**
     * 
     */
    public void updateDependants() {
        for (Enumeration ke = keys(); ke.hasMoreElements();) {
            updateDependants(ke.nextElement());
        }
    }


    /**
     */
    public void updateDependants(Object group) {
        Object value = get(group);
        Vector tonotify = (Vector) dependants.get(group);
        for (Enumeration e = tonotify.elements(); e.hasMoreElements();) {

            Mappable mappable = (Mappable) e.nextElement();
            //System.out.println("UPDATING:"+mappable+"<-"+ value);
            updateValue(mappable, group, value);
        }
    }

    /**
     * @param mappable
     * @param group
     * @param value
     */
    public abstract void updateValue(Mappable mappable, Object group, Object value);
    


//    public void copyTo(Object destination) {
////        if (destination instanceof Mapper) {
////            Mapper destmap = (Mapper) destination;
////      //      copy(destmap.dependants, dependants);
////        }
//        super.copyTo(destination);
//    }
    
//    public void copyFrom(Object source) {
////        if (source instanceof Mapper) {
////            Mapper srcmap = (Mapper) source;
////         //   copy(dependants, srcmap.dependants);
////        }
//        super.copyFrom(source);
//    }


}
