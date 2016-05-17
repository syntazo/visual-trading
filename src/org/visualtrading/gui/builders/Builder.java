/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders;

import org.visualtrading.model.Adapter;
import org.visualtrading.model.Application;
import org.visualtrading.xml.BooleanConverter;
import org.visualtrading.xml.ColorConverter;
import org.visualtrading.xml.DimensionConverter;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.visualtrading.xml.nanoxml.XMLParseException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;


public abstract class Builder {

// ------------------------------ FIELDS ------------------------------

    final private static Hashtable scope = new Hashtable();

    final static Object[] EMPTY = {};

// -------------------------- STATIC METHODS --------------------------

    public static Hashtable getScope() {
        return scope;
    }


    public static Object process(Application application, Class framework, String xmlfileName) {
        XMLElement xml = new XMLElement();
        InputStream
                inputstream = application.getClass().getResourceAsStream(xmlfileName);

        Reader reader = new BufferedReader(new InputStreamReader(inputstream));
        //System.err.println("RESOUR:" + reader);
        try {
            xml.parseFromReader(reader);
            return process(application, framework, xml);
        } catch (XMLParseException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Color getColor(XMLElement xml, String name) {
        return getColor(xml, name, null);
    }

    public static Color getColor(XMLElement xml, String name, Color defaultColor) {
        String colorstr = xml.getStringAttribute(name);
        return colorstr != null ? ColorConverter.toColor(colorstr) : defaultColor;
    }


    public static Font getFont(XMLElement xml, Font defaultFont) {
        return getFont(xml, "font", defaultFont);

    }

    public static Font getFont(XMLElement xml, String name, Font defaultFont) {
        String string = xml.getStringAttribute(name);
        if (string != null) {
            String fontname = null;
            boolean bold = false;
            boolean italic = false;
            int size = 0;
            StringTokenizer st = new StringTokenizer(string);
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if ("bold".equalsIgnoreCase(token)) {
                    bold = true;
                } else if ("italic".equalsIgnoreCase(token)) {
                    italic = true;
                } else {
                    try {
                        size = Integer.parseInt(token);
                    } catch (NumberFormatException nfe) {
                        fontname = (fontname == null) ? token : (fontname + " " + token);
                    }
                }
            }
            if (fontname == null) {
                fontname = defaultFont.getName();
            }
            if (size == 0) {
                size = defaultFont.getSize();
            }
            return new Font(fontname, (bold ? Font.BOLD : 0) | (italic ? Font.ITALIC : 0), size);

        }
        return defaultFont;
    }

    public static Insets getInsets(XMLElement xml, String name, Insets defaultInsets) {
        String string = xml.getStringAttribute(name);
        if (string != null) {
            try {
                StringTokenizer st = new StringTokenizer(string, ",");
                int top = Integer.parseInt((String) st.nextElement());
                int left = Integer.parseInt((String) st.nextElement());
                int bottom = Integer.parseInt((String) st.nextElement());
                int right = Integer.parseInt((String) st.nextElement());
                return new Insets(top, left, bottom, right);
            } catch (NumberFormatException e) {
                System.err.println("[" + xml.getLineNr() + "]<" + xml.getName() + "> has INVALID Insets attribute: " + string);
            }
        }
        return defaultInsets;
    }


    public static boolean getBoolean(XMLElement xml, String name, boolean defaultValue) {
        String string = xml.getStringAttribute(name);
        if (string != null) {
            Boolean result = BooleanConverter.toBoolean(string);
            return result != null ? result.booleanValue() : defaultValue;
        }
        return defaultValue;
    }

    public static int getInt(XMLElement xml, String name, int defaultValue) {
        return xml.getIntAttribute(name, defaultValue);
    }


    public static Integer getInteger(XMLElement xml, String name) {
        int value = xml.getIntAttribute(name);
        return new Integer(value);
    }

    public static Dimension getSize(XMLElement xml, Dimension defaultDimension) {

        return getDimension(xml, "size", defaultDimension);
    }

    public static Dimension getDimension(XMLElement xml, String name, Dimension defaultDimension) {
        String string = xml.getStringAttribute(name);
        if (string != null) {
            try {
                return DimensionConverter.toDimension(string);
            } catch (NumberFormatException e) {
                System.err.println(
                        "[" + xml.getLineNr() + "]<" + xml.getName() + "> has INVALID Dimension attribute: " + string);
            }
        }
        return defaultDimension;
    }

    public static Point getLocation(XMLElement xml, Point defaultLocation) {
        Dimension defaultDimension = new Dimension(defaultLocation.x, defaultLocation.y);

        Dimension newDim = getDimension(xml, "location", defaultDimension);
        return new Point(newDim.width, newDim.height);
    }

    public static boolean hasAnyAttribute(XMLElement xml, String[] attributes) {
        for (int i = 0; i < attributes.length; i++) {
            if (xml.getAttribute(attributes[i]) != null) {
                return true;
            }
        }
        return false;
    }

// -------------------------- OTHER METHODS --------------------------

    public Object[] getParams(Application application, XMLElement xml) {

        //System.out.println(xml.countChildren());
        Object[] params = new Object[xml.countChildren()];
        Enumeration enum = xml.enumerateChildren();
        int i = 0;
        while (enum.hasMoreElements()) {
            params[i++] = process(application, this.getClass(), (XMLElement) enum.nextElement());
        }
        return params;
    }

    public static Object process(Application application, Class root, XMLElement xml) {
        Object obj;
        String tagName = xml.getName();


        if (tagName != null) {
            obj = scope.get(tagName);
            if (obj != null) {
                return obj;
            }
        }
        String className = xml.getStringAttribute("class");
        String classpath = root.getName();
        int idx = classpath.lastIndexOf(".");
        String builderName = classpath.substring(0, idx) + "." + tagName + "Builder";


        try {
            Builder builder = (Builder) Class.forName(builderName).newInstance();
            Class classObj = null;
            if (className != null) {
                try {
                    classObj = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    System.out.println("not found: " + className);
                }
            }
            if (classObj == null) {
                classObj = builder.getClass(className);
            }
            if ((obj = builder.construct(application, classObj, xml)) != null) {
                obj = builder.configure(application, obj, xml);
                obj = builder.embed(application, obj, xml);
                obj = builder.wireup(application, obj, xml);
            }
            String id = (String) xml.getAttribute("id");
            if (id != null) {
                scope.put(id, obj);
            }
            return builder.addScrollPane(application, obj, xml);
        } catch (InstantiationException e) {
            // TODO
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO
            e.printStackTrace();
        }


        return null;
    }

    public abstract Class getClass(String className);


    public Object construct(Application application, Class classObj, XMLElement xml) throws SecurityException,
                                                                                            NoSuchMethodException,
                                                                                            IllegalArgumentException,
                                                                                            InstantiationException,
                                                                                            IllegalAccessException,
                                                                                            InvocationTargetException {

        Object[] arguments = getParams(null, xml, "CONSTRUCT");
        Object obj = null;
        //System.err.println("CTOR: "+ classObj);
        if (arguments.length > 0) {
            Constructor ctor = classObj.getConstructor(toClasses(arguments));
            obj = ctor.newInstance(arguments);
        } else {
            obj = classObj.newInstance();
        }
        return obj;
    }


    public Object[] getParams(Application application, XMLElement xml, String name) {
        Enumeration enum = xml.enumerateChildren();
        while (enum.hasMoreElements()) {
            XMLElement element = (XMLElement) enum.nextElement();
            if (element.getName().equalsIgnoreCase(name)) {
                return getParams(null, element);
            }
        }
        return EMPTY;
    }

    public Class[] toClasses(Object[] objects) {
        Class[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    public abstract Object configure(Application application, Object obj, XMLElement xml);


    public Object embed(Application application, Object obj, XMLElement xml) {
        Enumeration enum = xml.enumerateChildren();
        while (enum.hasMoreElements()) {
            XMLElement element = (XMLElement) enum.nextElement();
            if (element.getName().equalsIgnoreCase("CHILDREN")) {
                obj = addChildren(application, obj, element);
            }
        }
        return obj;
    }


    public abstract Object addChildren(Application application, Object obj, XMLElement xml);


    public Object wireup(Application application, Object obj, XMLElement xml) throws ClassNotFoundException,
                                                                                     SecurityException,
                                                                                     NoSuchMethodException,
                                                                                     IllegalArgumentException,
                                                                                     InvocationTargetException,
                                                                                     InstantiationException,
                                                                                     IllegalAccessException {
        Enumeration enum = xml.enumerateChildren();
        while (enum.hasMoreElements()) {
            XMLElement element = (XMLElement) enum.nextElement();
            if (element.getName().equalsIgnoreCase("WIRING")) {
                for (Enumeration wiringEnum = element.enumerateChildren(); wiringEnum.hasMoreElements();) {
                    XMLElement wiringElement = (XMLElement) wiringEnum.nextElement();
                    String elementName = wiringElement.getName().toUpperCase();
                    if (elementName.equalsIgnoreCase(Adapter.TAG)) {
                        String adapterClassName = wiringElement.getStringAttribute("class");
                        Class adapterClass = Class.forName(adapterClassName);
                        if (Adapter.class.isAssignableFrom(adapterClass)) {
                            Adapter adapter = (Adapter) adapterClass.newInstance();
                            Method m = adapterClass.getMethod("prime",
                                                              new Class[]{Application.class,
                                                                          Object.class,
                                                                          Hashtable.class});
                            Hashtable fields = new Hashtable();
                            if (wiringElement.countChildren() > 0) {
                                for (Enumeration fe = wiringElement.enumerateChildren(); fe.hasMoreElements();) {
                                    XMLElement fieldXml = (XMLElement) fe.nextElement();
                                    String fieldName = fieldXml.getName();
                                    Object field = getScope().get(fieldName);
                                    if (field != null) {
                                        fields.put(fieldName, field);
                                    }
                                }
                            } else {
                                for (Enumeration se = getScope().keys(); se.hasMoreElements();) {
                                    Object key = se.nextElement();
                                    fields.put(key, getScope().get(key));
                                }

                            }
                            m.invoke(adapter, new Object[]{application, obj, fields});
                        }


                    } else if (elementName.equals("EVENT")) {

                        Object src = scope.get(wiringElement.getStringAttribute("src"));
                        if (src != null) {
                            String event = wiringElement.getStringAttribute("event");
                            Class listenerInterface = Class.forName(event + "Listener");
                            if (listenerInterface.isAssignableFrom(obj.getClass())) {
                                Class srcClass = src.getClass();
                                Method m = srcClass.getMethod(wiringElement.getStringAttribute("adder"),
                                                              new Class[]{listenerInterface, });
                                m.invoke(src, new Object[]{obj});
                            }
                        }
                    }


                }

            }
        }
        return obj;
    }


    /**
     * @param obj
     * @param obj2
     * @param xml
     *
     * @return
     */
    protected abstract Object addScrollPane(Application application, Object object, XMLElement xml);

}