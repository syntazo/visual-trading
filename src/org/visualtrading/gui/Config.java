/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui;

import org.zaval.lw.LwToolkit;

import java.awt.*;

public class Config {

// ------------------------------ FIELDS ------------------------------

    public final static Color BLACK = new Color(0, 0, 0);
    public final static Color ORANGE = new Color(255, 200, 0);
    public final static Color BLUE = new Color(0, 0, 255);
    public static final Font BOLD = new Font("dialog", Font.BOLD, 14);
    public static final int CELL_ALIGNMENT = LwToolkit.CENTER;
    public final static Color DARK_GRAY = new Color(64, 64, 64);
    public final static Color GREEN = new Color(0, 255, 0);
    public final static Color LIGHT_GRAY = new Color(192, 192, 192);
    public final static Color MAGENTA = new Color(255, 0, 255);
    public final static Color PINK = new Color(255, 175, 175);
    public static final Font PLAIN = new Font("dialog", Font.PLAIN, 12);
    public final static Color RED = new Color(255, 0, 0);
    public static final int ROW_HIGHT = 20;
    public final static Color WHITE = new Color(255, 255, 255);
    public final static Color YELLOW = new Color(255, 255, 0);
    public final static Color background = new Color(0, 0, 99);
    public final static Color menuBarColor = LIGHT_GRAY;
    public static boolean infoOn = true;
    public static boolean warnOn = true;

// -------------------------- STATIC METHODS --------------------------

    /**
     * @return
     */
    public static Color getBackground() {
        return background;
    }

}