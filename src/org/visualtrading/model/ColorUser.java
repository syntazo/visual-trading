/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import java.awt.*;


public interface ColorUser extends Mappable {

// -------------------------- OTHER METHODS --------------------------

    Mapper getColorMapper();

    void setColor(Object key, Color color);
}
