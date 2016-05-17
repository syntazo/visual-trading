/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;

import java.awt.*;
import java.util.StringTokenizer;


public class ColorConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static Color DEFAULT = new Color(0, 0, 0);

// -------------------------- STATIC METHODS --------------------------

    public static Color toColor(String colorstr) {
        int color = 0;
        if (colorstr.startsWith("#")) {
            color = Integer.parseInt(colorstr.substring(1), 16);
        } else if (colorstr.startsWith("0x")) {
            color = Integer.parseInt(colorstr.substring(2), 16);
        } else {
            StringTokenizer st = new StringTokenizer(colorstr, " \r\n\t,");
            color = 0xff000000 | ((Integer.parseInt(st.nextToken()) & 0xff) << 16) |
                    ((Integer.parseInt(st.nextToken()) & 0xff) << 8) |
                    (Integer.parseInt(st.nextToken()) & 0xff);
        }
        return new Color(color);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public ColorConverter() {
        super();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public String tagName() {
        return "Color";
    }
    public Class getObjClass() {
        return Color.class;
    }

    public Object convertTo(String value) {
        return toColor(value);
    }

    public Object getDefault() {
        return DEFAULT;
    }

// -------------------------- OTHER METHODS --------------------------

    public String convertFrom(Object value) {
        return fromColor((Color) value);
    }

    public static String fromColor(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).toUpperCase().substring(2);
    }
}
