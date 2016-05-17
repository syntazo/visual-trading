/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;


import org.visualtrading.gui.builders.Builder;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwBorder;
import org.zaval.lw.LwBorderLayout;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwConstraints;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwFlowLayout;
import org.zaval.lw.LwGridLayout;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwListLayout;
import org.zaval.lw.LwPercentLayout;
import org.zaval.lw.LwRasterLayout;
import org.zaval.lw.LwToolkit;

import java.util.Enumeration;
import java.util.Hashtable;


public abstract class ZavalBuilder extends Builder {

// ------------------------------ FIELDS ------------------------------

    static Hashtable toolkitInts = new Hashtable();
    static Hashtable borderPositions = new Hashtable();


    /**
     * @param root
     * @param xml
     */
    String[] CONSTRAINTS = {"insets", "ax", "ay", "fill", "rowSpan", "colSpan"};

// -------------------------- STATIC METHODS --------------------------

    static {
        toolkitInts.put("BOTTOM", new Integer(LwToolkit.BOTTOM));
        toolkitInts.put("CENTER", new Integer(LwToolkit.CENTER));
        toolkitInts.put("HORIZONTAL", new Integer(LwToolkit.HORIZONTAL));
        toolkitInts.put("LEFT", new Integer(LwToolkit.LEFT));
        toolkitInts.put("NONE", new Integer(LwToolkit.NONE));
        toolkitInts.put("RIGHT", new Integer(LwToolkit.RIGHT));
        toolkitInts.put("TOP", new Integer(LwToolkit.TOP));
        toolkitInts.put("VERTICAL", new Integer(LwToolkit.VERTICAL));
        toolkitInts.put("BOTH", new Integer(LwToolkit.VERTICAL | LwToolkit.HORIZONTAL));

        toolkitInts.put("RAISED", new Integer(LwBorder.RAISED));
        toolkitInts.put("SUNKEN", new Integer(LwBorder.SUNKEN));
        toolkitInts.put("ETCHED", new Integer(LwBorder.ETCHED));
        toolkitInts.put("PLAIN", new Integer(LwBorder.PLAIN));
        toolkitInts.put("DOT", new Integer(LwBorder.DOT));
        toolkitInts.put("SUNKEN2", new Integer(LwBorder.SUNKEN2));
        toolkitInts.put("RAISED2", new Integer(LwBorder.RAISED2));

        borderPositions.put("NORTH", LwBorderLayout.NORTH);
        borderPositions.put("SOUTH", LwBorderLayout.SOUTH);
        borderPositions.put("EAST", LwBorderLayout.EAST);
        borderPositions.put("WEST", LwBorderLayout.WEST);
        borderPositions.put("CENTER", LwBorderLayout.CENTER);


    }


    public static LwLayout getLayout(XMLElement xml) {
        return getLayout(xml, new LwRasterLayout());
    }

    public static LwLayout getLayout(XMLElement xml, LwLayout defaultLayout) {
        String string = xml.getStringAttribute("layout");
        if (string != null) {
            if (string.equalsIgnoreCase("LIST")) {
                return new LwListLayout(xml.getIntAttribute("gap", 0));
            } else if (string.equalsIgnoreCase("FLOW")) {
                return new LwFlowLayout(getAlignment(xml, "align", LwToolkit.LEFT),
                                        getAlignment(xml, "valign", LwToolkit.TOP),
                                        getAlignment(xml, "dir", LwToolkit.HORIZONTAL),
                                        xml.getIntAttribute("gap", 0));
            } else if (string.equalsIgnoreCase("GRID")) {
                return new LwGridLayout(xml.getIntAttribute("rows", 1),
                                        xml.getIntAttribute("cols", 1),
                                        getAlignment(xml, "stretch", LwToolkit.HORIZONTAL));
            } else if (string.equalsIgnoreCase("BORDER")) {
                return new LwBorderLayout(xml.getIntAttribute("hgap", 0),
                                          xml.getIntAttribute("vgap", 0));
            } else if (string.equalsIgnoreCase("PERCENT")) {
                return new LwPercentLayout(getAlignment(xml, "dir", LwToolkit.HORIZONTAL),
                                           xml.getIntAttribute("gap", 0));
            } else {
                String[] flags = {"DEFAULT", "H", "PS", "W"};
                String flagstr = xml.getStringAttribute("flag", "DEFAULT").toUpperCase();
                for (int fi = 0; fi < flags.length; fi++) {
                    if (flagstr.equals(flags[fi])) {
                        return new LwRasterLayout(fi);
                    }
                }
                return new LwRasterLayout();
            }
        }
        return defaultLayout;
    }

    public static int getAlignment(XMLElement xml, String name, int defaultAlignment) {
        return getIntConstant(xml, name, defaultAlignment);
    }

    public static int getIntConstant(XMLElement xml, String name, int defaultAlignment) {
        String string = xml.getStringAttribute(name);
        if (string != null) {
            Object i = toolkitInts.get(string.toUpperCase());
            if (i != null) {
                return ((Integer) i).intValue();
            }
        }
        return defaultAlignment;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public ZavalBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object addChildren(Application application, Object obj, XMLElement xml) {
        LwContainer root = (LwContainer) obj;
        LwConstraints c1, c = getConstraints(xml, null);
        for (Enumeration enum = xml.enumerateChildren(); enum.hasMoreElements();) {
            XMLElement element = (XMLElement) enum.nextElement();
            String elementName = element.getName().toUpperCase();
            if (borderPositions.containsKey(elementName)) {
                element = (XMLElement) element.getChildren().firstElement();
                add(root, borderPositions.get(elementName), Builder.process(application, this.getClass(), element)); 
                //System.out.println("added" + element);
            } else {
                c1 = getConstraints(element, c);
                Object comp = Builder.process(application, this.getClass(), element);
                if (c1 != null) {
                    add(root, c1, comp);
                } else {
                    add(root, comp);
                }
            }
        }
        return obj;
    }

    LwConstraints getConstraints(XMLElement xml, LwConstraints c) {
        if (hasAnyAttribute(xml, CONSTRAINTS)) {
            LwConstraints individualC = new LwConstraints();
            if (c == null) {
                c = individualC;
            }
            //System.out.println(xml);
            individualC.insets = getInsets(xml, "insets", c.insets);
            individualC.ax = getAlignment(xml, "ax", c.ax);
            individualC.ay = getAlignment(xml, "ay", c.ay);
            individualC.fill = getAlignment(xml, "fill", c.fill);
            individualC.rowSpan = getInt(xml, "rowSpan", c.rowSpan);
            individualC.colSpan = getInt(xml, "colSpan", c.colSpan);
            return individualC;
        }
        return c;
    }

    public void add(LwContainer container, Object constraint, Object o) {
        container.add(constraint, (LwComponent) o);
    }

    public void add(LwContainer container, Object o) {
        container.add((LwComponent) o);
    }
}
