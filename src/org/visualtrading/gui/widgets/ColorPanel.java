/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


import org.zaval.data.event.TextEvent;
import org.zaval.data.event.TextListener;
import org.zaval.lw.LwBorderPan;
import org.zaval.lw.LwCanvas;
import org.zaval.lw.LwConstraints;
import org.zaval.lw.LwFlowLayout;
import org.zaval.lw.LwGridLayout;
import org.zaval.lw.LwImgSetRender;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwSlider;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.LwView;
import org.zaval.lw.event.LwActionListener;
import org.zaval.lw.event.LwActionSupport;
import org.zaval.lw.mask.BasicMaskValidator;
import org.zaval.lw.mask.LwMaskTextField;
import org.zaval.lw.mask.MaskValidator;

import java.awt.*;


public class ColorPanel
        extends LwPanel
        implements LwActionListener, TextListener {

// ------------------------------ FIELDS ------------------------------

    private static final LwView BUNDLE_VIEW = new LwImgSetRender("img/slider.gif", 11, 11, 9, 8, LwView.ORIGINAL);

    private LwActionSupport support;
    private LwMaskTextField redField, blueField, greenField;
    private LwSlider redSlider, blueSlider, greenSlider;
    private LwCanvas selected;
    private ColorChooser colors = new ColorChooser();
    private Object lock__;

// --------------------------- CONSTRUCTORS ---------------------------

    public ColorPanel() {

        this(Color.red);

    }

    public ColorPanel(Color color) {

        setInsets(2, 2, 2, 2);
        int boxSize = 10;
        colors.addActionListener(this);
        colors.setPSSize(60, 200);
        LwBorderPan tPanel = new LwBorderPan(new LwLabel((String) LwToolkit.getStaticObj("cd.predef")),
                                             colors);

        LwPanel sPanel = new LwPanel();
        sPanel.setLwLayout(new LwGridLayout(1, 2, LwToolkit.HORIZONTAL));
        selected = new LwCanvas();
        selected.getViewMan(true).setBorder("br.plain");
        selected.setPSSize(2 * boxSize, boxSize);
        sPanel.add(new LwLabel((String) LwToolkit.getStaticObj("cd.selected")));
        sPanel.add(selected);

        LwPanel rgb = new LwPanel();
        MaskValidator rgbValidator = new BasicMaskValidator(0, 255);
        rgb.setLwLayout(new LwGridLayout(3,
                                         3,
                                         LwToolkit.HORIZONTAL |
                                         LwToolkit.VERTICAL));
        LwConstraints rgbCon1 = new LwConstraints();
        rgbCon1.insets = new Insets(2, 2, 2, 2);
        rgbCon1.fill = LwToolkit.HORIZONTAL;
        LwConstraints rgbCon2 = new LwConstraints();
        rgbCon2.insets = new Insets(2, 2, 2, 2);
        rgbCon2.fill = LwToolkit.HORIZONTAL | LwToolkit.VERTICAL;

        rgb.add(rgbCon1,
                new LwLabel((String) LwToolkit.getStaticObj("cd.red")));
        redField = new LwMaskTextField("", "nnn");
        redField.setValidator(rgbValidator);
        redField.getTextModel().addTextListener(this);
        rgb.add(rgbCon1, redField);
        redSlider = new LwSlider();
        redSlider.showTitle(false);
        redSlider.showScale(false);
        redSlider.jumpOnPress(true);
        redSlider.setView(LwSlider.BUNDLE_VIEW, BUNDLE_VIEW);
        redSlider.setValues(0, 255, new int[]{0}, 1, 1);
        redSlider.setPSSize(150, -1);
        redSlider.addActionListener(this);
        rgb.add(rgbCon2, redSlider);

        rgb.add(rgbCon1,
                new LwLabel((String) LwToolkit.getStaticObj("cd.green")));
        greenField = new LwMaskTextField("", "nnn");
        greenField.setValidator(rgbValidator);
        greenField.getTextModel().addTextListener(this);
        rgb.add(rgbCon1, greenField);
        greenSlider = new LwSlider();
        greenSlider.showTitle(false);
        greenSlider.showScale(false);
        greenSlider.jumpOnPress(true);
        greenSlider.setView(LwSlider.BUNDLE_VIEW, BUNDLE_VIEW);
        greenSlider.setValues(0, 255, new int[]{0}, 1, 1);
        greenSlider.setPSSize(150, -1);
        greenSlider.addActionListener(this);
        rgb.add(rgbCon2, greenSlider);

        rgb.add(rgbCon1,
                new LwLabel((String) LwToolkit.getStaticObj("cd.blue")));
        blueField = new LwMaskTextField("", "nnn");
        blueField.setValidator(rgbValidator);
        blueField.getTextModel().addTextListener(this);
        rgb.add(rgbCon1, blueField);
        blueSlider = new LwSlider();
        blueSlider.showTitle(false);
        blueSlider.showScale(false);
        blueSlider.jumpOnPress(true);
        blueSlider.setView(LwSlider.BUNDLE_VIEW, BUNDLE_VIEW);
        blueSlider.setValues(0, 255, new int[]{0}, 1, 1);
        blueSlider.setPSSize(150, -1);
        blueSlider.addActionListener(this);
        rgb.add(rgbCon2, blueSlider);

        int w = Math.max(Math.max(sPanel.getPreferredSize().width,
                                  tPanel.getPreferredSize().width),
                         rgb.getPreferredSize().width);
        sPanel.setPSSize(w, -1);
        tPanel.setPSSize(w, -1);
        rgb.setPSSize(w, -1);

        add(sPanel);
        add(tPanel);
        add(rgb);
        setSelectedColor(color);

    }

    /**
     * Sets the specified color as the selected color.
     *
     * @param c the specified color to set as selected.
     */
    public void setSelectedColor(Color c) {

        Color oldColor = getSelectedColor();
        if (!oldColor.equals(c)) {
            syncByColor(c.getRed(),
                        c.getGreen(),
                        c.getBlue());
        }

    }

    /**
     * Gets the current selected color.
     *
     * @return a current selected color.
     */
    public Color getSelectedColor() {

        return selected.getBackground();

    }

    private void syncByColor(int r, int g, int b) {

        if (lock__ == null) {
            lock__ = selected;
        }
        try {
            setRgbFields(r, g, b);
            setRgbSliders(r, g, b);
            changeBg(new Color(r, g, b));
        } finally {
            if (lock__ == selected) {
                lock__ = null;
            }
        }

    }

    private void setRgbFields(int r, int g, int b) {

        if (lock__ == null) {
            lock__ = redField;
        }
        try {
            redField.setText(completeInt(r, 3));
            greenField.setText(completeInt(g, 3));
            blueField.setText(completeInt(b, 3));
        } finally {
            if (lock__ == redField) {
                lock__ = null;
            }
        }

    }

    private String completeInt(int v, int f) {

        StringBuffer buffer = new StringBuffer(Integer.toString(v));
        while (buffer.length() < f) {
            buffer.insert(0, '0');
        }
        return buffer.toString();

    }

    private void setRgbSliders(int r, int g, int b) {

        if (lock__ == null) {
            lock__ = redSlider;
        }
        try {
            redSlider.setValue(r);
            greenSlider.setValue(g);
            blueSlider.setValue(b);
        } finally {
            if (lock__ == redSlider) {
                lock__ = null;
            }
        }

    }

    private void changeBg(Color c) {

        selected.setBackground(c);
        if (support != null) {
            support.perform(this, c);
        }

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwActionListener ---------------------

    public void actionPerformed(Object target, Object data) {

        if (lock__ == null) {
            if (target == redSlider ||
                target == greenSlider ||
                target == blueSlider) {
                int r = redSlider.getValue(), g = greenSlider.getValue(), b = blueSlider.getValue();
                setRgbFields(r, g, b);
//                colors.removeSelection();
                changeBg(new Color(r, g, b));
            } else if (target == colors) {
                Color color = (Color) data;
                syncByColor(color.getRed(),
                            color.getGreen(),
                            color.getBlue());
            }
        }

    }

// --------------------- Interface TextListener ---------------------


    public void textRemoved(TextEvent e) {

        fieldChanged();

    }

    public void textInserted(TextEvent e) {

        fieldChanged();

    }

    public void textUpdated(TextEvent e) {

        fieldChanged();

    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Adds the specified action listener to receive action events from this component. The action event is performed
     * whenever a selected color has been changed. The action event stores the new color value in the event data field.
     *
     * @param l the specified action listener.
     */
    public void addActionListener(LwActionListener l) {

        if (support == null) {
            support = new LwActionSupport();
        }
        support.addListener(l);

    }

    private void fieldChanged() {

        if (lock__ == null) {
            int r = Integer.parseInt(redField.getText());
            int g = Integer.parseInt(greenField.getText());
            int b = Integer.parseInt(blueField.getText());
            setRgbSliders(r, g, b);
//            colors.removeSelection();
            changeBg(new Color(r, g, b));
        }

    }

    protected /*C#override*/ LwLayout getDefaultLayout() {

        return new LwFlowLayout(LwToolkit.CENTER,
                                LwToolkit.CENTER,
                                LwToolkit.VERTICAL,
                                4);

    }

    /**
     * Removes the specified action listener so it no longer receives action events from this component.
     *
     * @param l the specified action listener.
     */
    public void removeActionListener(LwActionListener l) {

        if (support != null) {
            support.removeListener(l);
        }

    }

}

