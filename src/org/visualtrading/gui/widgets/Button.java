/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Action;
import org.visualtrading.gui.Config;
import org.visualtrading.gui.Control;
import org.visualtrading.gui.ControlImplementor;
import org.visualtrading.model.KeyUser;
import org.visualtrading.model.Mapper;
import org.visualtrading.model.Model;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.data.Text;
import org.zaval.lw.LwButton;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwLabel;

import java.awt.*;


public class Button extends LwButton implements Control, Widget, KeyUser, FocusRequester {

// ------------------------------ FIELDS ------------------------------

    final public static Font FONT = new Font("Dialog", Font.BOLD, 11);
    final public static Font SMALLER_FONT = new Font("Dialog", Font.BOLD, 10);

    protected FocusRequester helper = new FocusRequesterHelper();

    protected ControlImplementor control = new ControlImplementor();

// -------------------------- STATIC METHODS --------------------------

    static public Button make(String str, Action action, int w, int h) {
        return make(str, action, w, h, FONT);
    }

    static public Button make(String label, Action action, int w, int h, Font font) {
        return make(label, action, w, h, font, Config.BLACK);
    }

    static public Button make(String label, Action action, int w, int h, Font font, Color color) {
        LwLabel lab = new LwLabel(new Text(label));
        if (font == null) {
            font = FONT;
        }
        lab.getTextRender().setFont(font);
        lab.getTextRender().setForeground(color);
        Button button = new Button(lab);
        button.setOpaque(true);
        button.setPSSize(w, h);
        button.getViewMan(true).setBorder("br.raised");
        button.setAction(action);

        return button;
    }

    public void setAction(Action action) {
        control.setAction(action);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Button() {
        super();
        loadConfig(null);
    }

    public Button(String lab) {
        super(lab);
        loadConfig(null);
    }


    public Button(LwComponent label) {
        super(label);
        loadConfig(null);
    }

    public void loadConfig(XMLElement xml) {
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface Control ---------------------



    public void process(Object context, Model subject) {
        control.process(context, subject);
    }


    public Action getAction() {
        return control.getAction();
    }

// --------------------- Interface FocusRequester ---------------------

    public FocusRequester getNextFocusRequester() {
        return helper.getNextFocusRequester();
    }

    public void setNextFocusRequester(FocusRequester requester) {
        helper.setNextFocusRequester(requester);
    }

    public void setTabCharacters(char[] chars) {
        helper.setTabCharacters(chars);
    }

    public void transferFocus() {
        helper.transferFocus();
    }

    public boolean validateTransfer(char key) {
        return helper.validateTransfer(key);
    }

// --------------------- Interface KeyUser ---------------------



    public void keyTyped(Object keyCode, Object key) {
        setPressed(true);
        keyReleased(null);
    }

// --------------------- Interface Widget ---------------------


    /* (non-Javadoc)
     * @see com.icap.btecapplet.gui.widgets.Widget#cleanup()
     */
    public void cleanup() {
    }

// -------------------------- OTHER METHODS --------------------------

    public void subscribe(Object keyGroup) {
        if (getKeyMapper() != null) {
            getKeyMapper().subscribe(keyGroup, this);
        }
    }

    public Mapper getKeyMapper() {
        return null;
    }

}
