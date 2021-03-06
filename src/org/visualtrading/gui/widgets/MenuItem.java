/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LwComponent;
import org.zaval.lw.LwDesktop;
import org.zaval.lw.event.LwActionListener;


public interface MenuItem extends LwComponent, LwActionListener {

// -------------------------- OTHER METHODS --------------------------

    public void configure(LwDesktop desktop);

    public String getTitle();

    public void setTitle(String label);
}
