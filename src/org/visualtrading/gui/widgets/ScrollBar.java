/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LayoutContainer;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwCanvas;
import org.zaval.lw.LwScroll;
import org.zaval.lw.LwStButton;
import org.zaval.lw.LwToolkit;

import java.awt.*;

public class ScrollBar extends LwScroll implements Widget {

// ------------------------------ FIELDS ------------------------------

    private static final int MIN_BUNDLE_SIZE = 16;

    private LwStButton decBt = (LwStButton) getElement(DECBUTTON_EL);
    private LwStButton incBt = (LwStButton) getElement(INCBUTTON_EL);
    private LwCanvas bundle = (LwCanvas) getElement(BUNDLE_EL);
    private int type = LwToolkit.VERTICAL;

// --------------------------- CONSTRUCTORS ---------------------------

    public ScrollBar(int t) {

        super(t);
        this.type = t;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {

    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface LwLayout ---------------------

    public void layout(LayoutContainer target) {

        Insets i = getInsets();
        int ew = width - i.left - i.right, eh = height - i.top - i.bottom;
        boolean isHorizontalBar = (type == LwToolkit.HORIZONTAL);

        Dimension decButtonPrefferedSize = ps(decBt);
        if (decButtonPrefferedSize.width > 0) {
            decBt.setSize(isHorizontalBar ? decButtonPrefferedSize.width : ew,
                          isHorizontalBar ?
                          eh :
                          decButtonPrefferedSize.height);
            decBt.setLocation(i.left, i.top);
        }

        Dimension incButtonPrefferedSize = ps(incBt);
        if (incButtonPrefferedSize.width > 0) {
            incBt.setSize(isHorizontalBar ? incButtonPrefferedSize.width : ew,
                          isHorizontalBar ?
                          eh :
                          incButtonPrefferedSize.height);
            incBt.setLocation(isHorizontalBar ?
                              width - i.right - incButtonPrefferedSize.width :
                              i.left,
                              isHorizontalBar ?
                              i.top :
                              height -
                              i.bottom -
                              incButtonPrefferedSize.height);
        }

        if (bundle != null && bundle.isVisible()) {
            int am = amount();
            if (am > MIN_BUNDLE_SIZE) {
                int bsize = Math.max(Math.min((getExtraSize() * am) / getMaxOffset(),
                                              am - MIN_BUNDLE_SIZE),
                                     MIN_BUNDLE_SIZE);
                bundle.setSize(isHorizontalBar ? bsize : ew,
                               isHorizontalBar ? eh : bsize);
                bundle.setLocation(isHorizontalBar ? value2pixel() : i.left,
                                   isHorizontalBar ? i.top : value2pixel());
            } else {
                bundle.setSize(0, 0);
            }
        }

    }

// --------------------- Interface Widget ---------------------


    public void cleanup() {

    }

// -------------------------- OTHER METHODS --------------------------

    private Dimension ps(Layoutable l) {

        Dimension ps = l != null && l.isVisible() ?
                       l.getPreferredSize() :
                       new Dimension();
        ps.height = this.height / 2 <= ps.height ?
                    this.height / 2 :
                    ps.height;
        return ps;

    }

    private int value2pixel() {

        return (type == LwToolkit.VERTICAL) ?
               decBt.getY() +
               decBt.getHeight() +
               ((amount() - bundle.getHeight()) *
                getPosController().getOffset()) /
               getMaxOffset()
               :
               decBt.getX() +
               decBt.getWidth() +
               ((amount() - bundle.getWidth()) *
                getPosController().getOffset()) /
               getMaxOffset();

    }

    private int amount() {

        return (type == LwToolkit.VERTICAL) ? incBt.getY() -
                                              decBt.getY() -
                                              decBt.getHeight() :
               incBt.getX() - decBt.getX() - decBt.getWidth();

    }
}

