/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;

import java.awt.*;
import java.util.StringTokenizer;


public class FontConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static Font DEFAULT = new Font("times", Font.PLAIN, 10);

    static String[] styles = {"", " bold", " italic"};

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new FontConverter());
    }

    static public Font toFont(String string) {
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
                    fontname = (fontname == null) ?
                               token :
                               (fontname + " " + token);
                }
            }
        }
        return fontname != null ?
               new Font(fontname,
                        (bold ? Font.BOLD : 0) |
                        (italic ? Font.ITALIC : 0),
                        size) :
               null;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public FontConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public Object convertTo(String value) {
        return toFont(value);
    }

    public Object getDefault() {
        return DEFAULT;
    }
    public Class getObjClass() {
        return Font.class;
    }

    public String tagName() {
        return "Font";
    }

// -------------------------- OTHER METHODS --------------------------

    public String convertFrom(Object value) {
        return fromFont((Font) value);
    }

    static public String fromFont(Font font) {
        StringBuffer buff = new StringBuffer();
        buff.append(font.getName());
        buff.append(" ");
        buff.append(font.getSize());
        buff.append(" ");
        buff.append(styles[font.getStyle()]);
        return buff.toString();
    }
}
