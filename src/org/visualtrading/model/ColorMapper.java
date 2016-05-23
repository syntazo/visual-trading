/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.model;

import java.awt.*;


public class ColorMapper extends Mapper {

// --------------------------- CONSTRUCTORS ---------------------------

    public ColorMapper() {
        super("colors");

    }

    public ColorMapper(Object[] map) {
        super("colors", map);

    }

// -------------------------- OTHER METHODS --------------------------

    public void updateValue(Mappable mappable, Object group, Object value) {
        ColorUser colorUser = (ColorUser) mappable;
        colorUser.setColor(group, (Color) value);

    }
}
