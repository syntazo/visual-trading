/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.visualtrading.util.Sort;
import org.visualtrading.xml.ConverterEngine;
import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;


public class NewModelImp implements NewModel {

// ------------------------------ FIELDS ------------------------------

    public static final String TAG = "NewModel";

    final static Object[] BLANK = {};


    protected Dictionary map;
    private Object id;

// -------------------------- STATIC METHODS --------------------------

    public static boolean isNewModelTag(XMLElement xml) {
        return xml.getName().equalsIgnoreCase(TAG);
    }

    static public void copy(Dictionary destination, Dictionary source) {
        for (Enumeration keyenum = source.keys(); keyenum.hasMoreElements();) {

            Object key = keyenum.nextElement();
            Object obj = source.get(key);
            if (obj instanceof NewModel) {
                destination.put(key, ((NewModelImp) obj).clone());
            } else {
                destination.put(key, source.get(key));
            }
        }
    }

    public Object clone() {
        NewModelImp o = null;
        try {
            o = (NewModelImp) super.clone();
            o.map = new Hashtable();
            o.copyFrom(this);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }


    public void copyFrom(Object source) {
        if (source instanceof NewModel) {
            copy(map, ((NewModel) source).getMap());
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public NewModelImp() {
        this("");
    }

    public NewModelImp(Object id) {
        this(id, BLANK);
    }

    public NewModelImp(Object id, Object[] mapping) {
        this(id, mapping, new Hashtable());
    }

    public NewModelImp(Object id, Object[] mapping, Object aMap) {
        map = (Dictionary) aMap;
        setId(id);
        load(mapping);
        initialize();
    }


    public void load(Object[] mapping) {
        if (mapping.length > 1 && mapping.length % 2 != 0) {
            throw new IllegalArgumentException("must be array of key,color pairs");
        }
        for (int i = 0; i < mapping.length; i += 2) {
            put(mapping[i], mapping[i + 1]);
        }

    }


    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    public void initialize() {
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Object getId() {
        return id;
    }

    public void setId(Object theId) {
        id = theId;
    }


    public Dictionary getMap() {
        return map;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface NewModel ---------------------


    public void clear() {
        ((Hashtable) map).clear();
    }


    public boolean containsKey(Object key) {
        return ((Hashtable) map).containsKey(key);
    }

    public void copyTo(Object destination) {
        if (destination instanceof NewModel) {
            copy(((NewModel) destination).getMap(), map);
        }
    }


    public Enumeration elements() {
        return map.elements();
    }


    public Object get(Object key) {
        return map.get(key);
    }

   

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Object[] keySorted() {
        return Sort.sort(this.map, Sort.stringComparator);
    }


    public Enumeration keys() {
        return map.keys();
    }


    public Object remove(Object key) {
        return map.remove(key);
    }
    public int size() {
        return map.size();
    }

// --------------------- Interface XMLObj ---------------------


    public XMLElement toXML() {
        XMLElement xml = getElement();
        for (Enumeration ev = keys(); ev.hasMoreElements();) {
            Object key = ev.nextElement();
            Object obj = get(key);
            if (obj instanceof NewModel) {
                modelToXML(key, (NewModel) obj, xml);
            } else {
                objToXML(key, obj, xml);
            }
        }

        return xml;

    }


    public void fromXML(XMLElement xml) {
        setId(xml.getStringAttribute("key"));
        for (Enumeration e = xml.enumerateChildren(); e.hasMoreElements();) {
            XMLElement element = (XMLElement) e.nextElement();
            if (isNewModelTag(element)) {
                try {
                    NewModel submodel = (NewModel) Class.forName(element.getStringAttribute("class")).newInstance();
                    submodel.fromXML(element);
                    map.put(submodel.getId(), submodel);
                } catch (InstantiationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                Object obj = ConverterEngine.fromXML(element);
                map.put(element.getAttribute("key"), obj);
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public XMLElement getElement() {
        XMLElement xml = new XMLElement();
        xml.setAttribute("key", getId());
        xml.setAttribute("class", this.getClass().getName());
        //xml.setAttribute("obj", this.toString());
        xml.setName(TAG);
        return xml;
    }

    public XMLElement modelToXML(Object key, NewModel model, XMLElement xml) {
        XMLElement child = model.toXML();
        child.setAttribute("key", key);
        //child.setAttribute("obj",model.toString());
        xml.addChild(child);
        return xml;
    }

    public XMLElement objToXML(Object key, Object obj, XMLElement xml) {
        XMLElement child = ConverterEngine.toXML(obj);
        if (child != null) {
            child.setAttribute("key", key);
            //child.setAttribute("obj", obj.toString());
            xml.addChild(child);
        }
        return xml;
    }

}
