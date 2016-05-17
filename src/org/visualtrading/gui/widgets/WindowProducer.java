/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Config;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwAdvViewMan;
import org.zaval.lw.LwBorder;
import org.zaval.lw.LwBorderLayout;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwFlowLayout;
import org.zaval.lw.LwImage;
import org.zaval.lw.LwImgRender;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwStButton;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.LwWinProducer;

import java.awt.*;


public class WindowProducer
        extends LwWinProducer {

// ------------------------------ FIELDS ------------------------------

    private LwPanel buttons, icons;
    private LwLabel title;
    private Color activeColor = new Color(0, 25, 155), nonactiveColor = Config.DARK_GRAY;

// -------------------------- OTHER METHODS --------------------------

    protected /*C#override*/ void addElement(int type, LwComponent el) {
        if (type == ICON_EL) {
            icons.insert(0, null, el);
        } else if (type == CLOSE_BUTTON_EL) {
            buttons.add(el);
        } else {
            super.addElement(type, el);
        }
    }

    protected /*C#override*/ LwContainer createCaption() {
        LwPanel titlePanel = new LwPanel();
        titlePanel.setLwLayout(new LwBorderLayout(1, 1));
        titlePanel.setInsets(1, 1, 1, 1);

        LwStButton exit = new LwStButton();
        LwAdvViewMan man = new LwAdvViewMan();
        man.put("st.over", LwToolkit.getView("win.b.over"));
        man.put("st.out", LwToolkit.getView("win.b.out"));
        man.put("st.pressed", LwToolkit.getView("win.b.pressed"));
        exit.setViewMan(man);


        title = new LwLabel();
        title.setOpaque(true);
        title.setForeground(Color.white);

        buttons = new LwPanel();
        buttons.setLwLayout(new LwFlowLayout(LwToolkit.CENTER, LwToolkit.CENTER));
        buttons.setOpaque(true);
        buttons.add(exit);
        buttons.setBackground(Config.BLUE);
        buttons.setSize(buttons.getWidth(), 10);
        icons = new LwPanel();
        icons.setLwLayout(new LwFlowLayout(LwToolkit.LEFT,
                                           LwToolkit.CENTER,
                                           LwToolkit.HORIZONTAL,
                                           2));
        icons.add(new LwImage((LwImgRender) LwToolkit.getView("win.icon")));
        icons.add(title);
        icons.setSize(icons.getWidth(), 10);
        icons.setOpaque(true);
        icons.setBackground(Config.BLUE);
        titlePanel.add(LwBorderLayout.WEST, icons);
        titlePanel.add(LwBorderLayout.EAST, buttons);
        //titlePanel.add(LwBorderLayout.SOUTH, new LwLine(LwBorder.ETCHED));
        //titlePanel.getViewMan(true).setBorder("br.raised");


        titlePanel.getViewMan(true).setBorder(new CustomBorder()); //"br.raised");

        return titlePanel;
    }

    protected /*C#virtual*/ LwContainer createRoot() {
        LwPanel panel = new Panel();
        panel.setOpaque(true);
        return panel;
    }

    protected /*C#override*/ LwComponent fetchElement(int type) {
        if (type == CLOSE_BUTTON_EL) {
            return buttons.count() > 0 ?
                   (LwComponent) buttons.get(0) :
                   null;
        }
        return (type == ICON_EL) ? (icons.count() > 1 ?
                                    (LwComponent) icons.get(0) :
                                    null)
               : super.fetchElement(type);
    }

    public String getTitle() {
        return title.getText();
    }

    /**
     * Sets the specified color that will be used as the background color of the window caption according to the
     * specified window state.
     *
     * @param state the specified window state. <code>true</code> value corresponds to active window state and
     *              <code>false</code> value corresponds to non-active window state.
     * @param c     the specified color.
     */
    public void setCaptionColor(boolean state, Color c) {
        Color old = state ? activeColor : nonactiveColor;
        if (!old.equals(c)) {
            if (state) {
                activeColor = c;
            } else {
                nonactiveColor = c;
            }
            getCaptionComp().repaint();
        }
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    public void switched(boolean state) {
        getCaptionComp().setBackground(state ? activeColor : nonactiveColor);
    }

// -------------------------- INNER CLASSES --------------------------

    class CustomBorder
            extends LwBorder {

// ------------------------------ FIELDS ------------------------------

        private int size = 1;

// --------------------------- CONSTRUCTORS ---------------------------

        CustomBorder() {

            super(LwBorder.ETCHED);
        }

// -------------------------- OTHER METHODS --------------------------

        public int getBottom() {
            return size;
        }

        public int getLeft() {
            return size;
        }

        public int getRight() {
            return size;
        }

        public int getTop() {
            return size;
        }

        public void paint(Graphics g,
                          int x,
                          int y,
                          int w,
                          int h,
                          Layoutable d) {

            int base = (w > 100 ? 100 : 10);
            int jump = w / base;
            Color c = g.getColor();

            super.paint(g, x, y, w, h, d);

            if (jump == 0) {
                return;
            }

            g.setColor(c);
            int rs = Math.max(1, (255 - c.getRed()) / base);
            int gs = Math.max(1, (255 - c.getGreen()) / base);
            int bs = Math.max(1, (255 - c.getBlue()) / base);
            for (int j = 0; j < w; j += jump) {
                c = g.getColor();
                int r = c.getRed();
                int gr = c.getGreen();
                int b = c.getBlue();
                Color nc = new Color(Math.min((r + rs), 255),
                                     Math.min((gr + gs), 255),
                                     Math.min((b + bs), 255));
                g.setColor(nc);
                g.fillRect(x + j, y, jump, h - 1);
            }

        }

    }

}
