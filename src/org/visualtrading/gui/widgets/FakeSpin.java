/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


import org.visualtrading.gui.Config;
import org.zaval.lw.LayoutContainer;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwAdvViewMan;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwSpeedButton;
import org.zaval.lw.LwStButton;
import org.zaval.lw.LwTextField;
import org.zaval.lw.LwToolkit;

import java.awt.*;


public class FakeSpin
        extends LwPanel
        implements LwLayout {

// ------------------------------ FIELDS ------------------------------

    /**
     * This is increment button layout constraint.
     */
    public static final Integer INC_BUTTON = new Integer(1);

    /**
     * This is decrement button layout constraint.
     */
    public static final Integer DEC_BUTTON = new Integer(2);


    private static final Integer EDITOR_EL = new Integer(3);

    private LwTextField editor;
    private LwStButton incButton, decButton;

// --------------------------- CONSTRUCTORS ---------------------------

    public FakeSpin() {
        this(new LwTextField("0"));
    }

    public FakeSpin(LwTextField editor) {
        LwAdvViewMan m1 = new LwAdvViewMan();
        m1.put("st.out", LwToolkit.getView("sp.b1.out"));
        m1.put("st.over", LwToolkit.getView("sp.b1.over"));
        m1.put("st.pressed", LwToolkit.getView("sp.b1.pressed"));
        m1.setBorder(LwToolkit.getView("br.etched"));
        LwSpeedButton incBut = new LwSpeedButton();
        incBut.fireByPress(true, 150);
        incBut.setViewMan(m1);

        LwAdvViewMan m2 = new LwAdvViewMan();
        m2.put("st.out", LwToolkit.getView("sp.b2.out"));
        m2.put("st.over", LwToolkit.getView("sp.b2.over"));
        m2.put("st.pressed", LwToolkit.getView("sp.b2.pressed"));
        m2.setBorder(LwToolkit.getView("br.etched"));
        LwSpeedButton decBut = new LwSpeedButton();
        decBut.fireByPress(true, 150);
        decBut.setViewMan(m2);

        LwTextField editorFld = editor;
        add(INC_BUTTON, incBut);
        add(EDITOR_EL, editorFld);
        add(DEC_BUTTON, decBut);
        editorFld.getViewMan(true).setBorder(LwToolkit.getView("br.sunken"));
        setPSSize(75, 20);
        getEditor().setBackground(Config.WHITE);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Gets the spin editor component.
     *
     * @return a spin editor component.
     */
    public LwComponent getEditor() {
        return editor;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwLayout ---------------------

    public void componentAdded(Object id, Layoutable lw, int index) {
        if (id.equals(INC_BUTTON) && incButton == null) {
            incButton = (LwStButton) lw;
        } else if (id.equals(DEC_BUTTON) && decButton == null) {
            decButton = (LwStButton) lw;
        } else if (id.equals(EDITOR_EL) && editor == null) {
            editor = (LwTextField) lw;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void componentRemoved(Layoutable lw, int index) {
        if (lw == incButton) {
            incButton = null;
        } else if (lw == decButton) {
            decButton = null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Dimension calcPreferredSize(LayoutContainer target) {
        Dimension ps1 = editor.getPreferredSize();
        Dimension ps2 = incButton.getPreferredSize();
        Dimension ps3 = decButton.getPreferredSize();
        return new Dimension(ps1.width + Math.max(ps2.width, ps3.width),
                             Math.max(ps1.height, ps2.height + ps3.height));
    }

    public void layout(LayoutContainer target) {
        Insets ins = target.getInsets();
        Dimension ps2 = incButton.getPreferredSize();
        Dimension ps3 = decButton.getPreferredSize();
        int mBtWidth = Math.max(ps2.width, ps3.width);

        editor.setLocation(ins.left, ins.top);
        editor.setSize(width - mBtWidth - ins.left - ins.right,
                       height - ins.top - ins.bottom);

        incButton.setLocation(width - mBtWidth - ins.right, ins.top);
        incButton.setSize(mBtWidth, ps2.height);
        decButton.setLocation(width - mBtWidth - ins.right, height - ps3.height - ins.bottom);
        decButton.setSize(mBtWidth, ps3.height);
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Gets the increment or decrement button component depending on the specified layout constraint.
     *
     * @param buttonType the specified layout constraint. Use one of the following constant: <ul> <li>INC_BUTTON - to
     *                   get increment button component. </li> <li>DEC_BUTTON - to get decrement button component.
     *                   </li> </ul>
     *
     * @return a button component.
     */
    public LwStButton getButton(Integer buttonType) {
        if (buttonType.equals(INC_BUTTON)) {
            return incButton;
        } else if (buttonType.equals(DEC_BUTTON)) {
            return decButton;
        } else {
            throw new IllegalArgumentException();
        }
    }


    protected /*C#override*/ LwLayout getDefaultLayout() {
        return this;
    }

    public void setValue(int v) {
        setStrValue(Integer.toString(v));
    }

    /**
     * Sets the specified value. If the loop mode is enabled than a case when the new value is less than minimum the
     * method sets maximum value as the current or if the new value is greater than maximum the method sets minimum
     * value as the current.
     *
     * @param v the new current value.
     */
    public void setStrValue(String v) {
        String prev = getValue();
        if (!prev.equals(v)) {
            editor.setText(v);
            editor.invalidate();
            repaint();
        }
    }


    /**
     * Gets the current value.
     *
     * @return a current value.
     */
    public String getValue() {
        return editor.getText();
    }


}





