/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.xml;

import java.awt.*;


public class PointConverter extends ConverterImpl {

// ------------------------------ FIELDS ------------------------------

    final static Point DEFAULT = new Point(0, 0);

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new PointConverter());
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public PointConverter() {
        super();

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------


    public Object convertTo(String value) {
        Dimension dim = DimensionConverter.toDimension(value);
        return new Point(dim.width, dim.height);
    }

    public Object getDefault() {
        return DEFAULT;
    }
    public Class getObjClass() {
        return Point.class;
    }

    public String tagName() {
        return "Point";
    }
}
