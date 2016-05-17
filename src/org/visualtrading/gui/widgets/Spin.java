/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


import org.visualtrading.gui.Config;
import org.visualtrading.model.Field;
import org.visualtrading.model.KeyCode;
import org.visualtrading.model.KeyUser;
import org.visualtrading.model.Mappable;
import org.visualtrading.model.Mapper;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LayoutContainer;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwAdvViewMan;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwSpeedButton;
import org.zaval.lw.LwStButton;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.event.LwActionListener;

import java.awt.*;


public  abstract class Spin
        extends LwPanel
        implements Field, LwLayout, LwActionListener, KeyUser {

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

    static final Mapper NULLMapper = new Mapper("NULL") {

        public void updateValue(Mappable mappable, Object group, Object value) {
        }

        public void fromXML(XMLElement xml) {
        }

        public void subscribe(Object incKey, Object decKey) {
        }
    };

    String id;

    Object incCode, decCode;

    Object STEP;

    protected LwStButton dec;

    protected LwStButton inc;

    private Input editor;
    private LwStButton incButton, decButton;

// --------------------------- CONSTRUCTORS ---------------------------

    public Spin(Input editor) {
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

        Input editorFld = editor;
        add(INC_BUTTON, incBut);
        add(EDITOR_EL, editorFld);
        add(DEC_BUTTON, decBut);
        editorFld.getViewMan(true).setBorder(LwToolkit.getView("br.sunken"));
        setPSSize(75, 20);
        getEditor().setBackground(Config.WHITE);
        getEditor().setOpaque(true);
        inc = getButton(INC_BUTTON);
        dec = getButton(DEC_BUTTON);
        dec.addActionListener(this);
        inc.addActionListener(this);
    }

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

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Gets the spin editor component.
     *
     * @return a spin editor component.
     */
    public LwComponent getEditor() {
        return editor;
    }

    /* (non-Javadoc)
     * @see org.visualtrading.model.Field#getId()
     */
    public String getId() {

        return id;
    }

    /* (non-Javadoc)
     * @see org.visualtrading.model.Field#setId(java.lang.String)
     */
    public void setId(String theID) {
        id = theID;

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Field ---------------------



    /**
     * Gets the current value.
     *
     * @return a current value.
     */
    public Object getValue() {
        return editor.getValue();
    }

// --------------------- Interface KeyUser ---------------------


    public  Mapper getKeyMapper(){
        return NULLMapper;
    }


    /* (non-Javadoc)
     * @see org.visualtrading.model.KeyUser#keyTyped(java.lang.Object, java.lang.Object)
     */
    public void keyTyped(Object keyCode, Object key) {
        KeyCode up = (KeyCode) getKeyMapper().get(incCode);
        KeyCode down = (KeyCode) getKeyMapper().get(decCode);
        if (keyCode == up) {
            actionPerformed(inc, null);
        } else if (keyCode == down) {
            actionPerformed(dec, null);
        }
        validate();

    }

// --------------------- Interface LwActionListener ---------------------


    public void actionPerformed(Object src, Object data) {
        Object newValue = getValue();
        if (src == dec) {
            newValue = decrement();
        }
        if (src == inc) {
            newValue = increment();
        }
        if (inRange(newValue)) {
            setValue(newValue);
        }
    }

// --------------------- Interface LwLayout ---------------------

    public void componentAdded(Object id, Layoutable lw, int index) {
        if (id.equals(INC_BUTTON) && incButton == null) {
            incButton = (LwStButton) lw;
        } else if (id.equals(DEC_BUTTON) && decButton == null) {
            decButton = (LwStButton) lw;
        } else if (id.equals(EDITOR_EL) && editor == null) {
            editor = (Input) lw;
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
     * @return
     */
    public abstract Object decrement();


    protected /*C#override*/ LwLayout getDefaultLayout() {
        return this;
    }

    public Object getStep() {
        return STEP;
    }

    public abstract boolean inRange(Object object);

    /**
     * @return
     */
    public abstract Object increment();

    public void setFromObject(Object value) {
        setValue(value.toString());

    }

    /**
     * Sets the specified value. If the loop mode is enabled than a case when the new value is less than minimum the
     * method sets maximum value as the current or if the new value is greater than maximum the method sets minimum
     * value as the current.
     *
     * @param v the new current value.
     */
    public void setValue(Object value) {
        if (value != null) {
            Input editor = (Input) getEditor();
            String v = editor.toString(value);
            String prev = editor.toString(getValue());
            if (!prev.equals(v)) {
                editor.setValue(value);
                editor.invalidate();
                repaint();
            }
        } else if (getValue() != null) {
            editor.setValue(value);
            editor.invalidate();
            repaint();
        }
    }

    public abstract void setRange(int min, int max);

    public void setStep(Object step) {
        STEP = step;
    }

//    public abstract void subscribe(Object incKey, Object decKey);
    public void subscribe(Object incKey, Object decKey) {
        incCode = incKey;
        decCode = decKey;
        getKeyMapper().subscribe(incKey, this);
        getKeyMapper().subscribe(decKey, this);
    }


}





