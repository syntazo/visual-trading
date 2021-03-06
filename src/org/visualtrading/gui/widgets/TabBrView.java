/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Config;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwTabBrView;

import java.awt.*;


public class TabBrView extends LwTabBrView {

// --------------------------- CONSTRUCTORS ---------------------------

    public TabBrView(int o) {
        super(o);
    }

// -------------------------- OTHER METHODS --------------------------

    public void paint(Graphics g, int x, int y, int w, int h, Layoutable d) {
        int xx = x + w - 1;
        int yy = y + h - 1;
        super.paint(g, x, y, w, h, d);

        int[] xs = {x + 1, x + 1, x + 4, xx - 2, xx - 1, xx - 1, x + 1};
        int[] ys = {yy, y + 4, y + 1, y + 1, y + 2, yy, yy};
        g.setColor(Config.LIGHT_GRAY);
        g.fillPolygon(xs, ys, ys.length);
    }

}