/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Config;
import org.visualtrading.model.Field;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.data.Text;
import org.zaval.lw.LwCheckbox;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwLabel;
import org.zaval.lw.event.LwKeyEvent;
import org.zaval.lw.event.LwMouseEvent;

import java.awt.*;


public class CheckBox extends LwCheckbox implements Widget, Field, FocusRequester {

// ------------------------------ FIELDS ------------------------------

    private static Font cbFont = new Font("Dialog", Font.PLAIN, 10);

    FocusRequester helper = new FocusRequesterHelper();

    String myId;

// -------------------------- STATIC METHODS --------------------------

    public static CheckBox make(Label lcb1) {
        lcb1.getTextRender().setFont(cbFont);
        CheckBox checkbox = new CheckBox(lcb1, LwCheckbox.CHECK);
        checkbox.init();
        return checkbox;

    }

    public static CheckBox make(String str) {
        LwLabel lcb1 = new Label(new Text(str));
        lcb1.getTextRender().setFont(cbFont);
        CheckBox checkbox = new CheckBox(lcb1, LwCheckbox.CHECK);
        checkbox.init();
        return checkbox;

    }

    protected void init() {
        setOpaque(false);
        setBackground(Config.getBackground());
        getBox().getViewMan(true).setBorder("br.sunken");
        getBox().setBackground(Config.WHITE);
        //LwAdvViewMan m6 = new LwAdvViewMan();
        //m6.put("check.on", new LwImage("img/c_off.gif").getViewMan(true).getView());
        //m6.put("check.off", new LwImage("img/c_on.gif").getViewMan(true).getView());
        //getBox().setViewMan(m6);

    }

// --------------------------- CONSTRUCTORS ---------------------------

    public CheckBox() {
        super();

    }

    /**
     * @param lab
     */
    public CheckBox(String lab) {
        super(lab);

    }

    /**
     * @param lcb1
     * @param check
     */
    public CheckBox(LwLabel lcb1, int check) {

        super(lcb1, check);
    }

    /**
     * @param c
     * @param type
     */
    public CheckBox(LwComponent c, int type) {
        super(c, type);

    }

    /**
     * @param lab
     * @param type
     */
    public CheckBox(String lab, int type) {
        super(lab, type);

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface Field ---------------------


    /* (non-Javadoc)
     * @see org.visualtrading.model.Field#getObject()
     */
    public Object getValue() {
        return new Boolean(getState());
    }

    /* (non-Javadoc)
     * @see org.visualtrading.model.Field#setFromObject(java.lang.Object)
     */
    public void setValue(Object value) {
        setState(((Boolean) value).booleanValue());

    }

    /* (non-Javadoc)
     * @see org.visualtrading.model.Field#getId()
     */
    public String getId() {
        return myId;
    }

    /* (non-Javadoc)
     * @see org.visualtrading.model.Field#setId(java.lang.String)
     */
    public void setId(String id) {
        myId = id;

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

// --------------------- Interface LwContainer ---------------------


    public void paintOnTop(Graphics g) {
        super.paintOnTop(g);
        if (hasFocus()) {
            g.setColor(Color.black);
            g.drawRect(getBox().getX() - 1, getBox().getY() - 1, getBox().getWidth() + 1, getBox().getHeight() + 1);
        }
    }

// --------------------- Interface LwKeyListener ---------------------


    public void keyPressed(LwKeyEvent e) {
        //Util.warn(this+" - keyPressed:"+e+", state:"+getState());
        super.keyPressed(e);
//Util.warnln("->"+getState());
    }

    public void keyReleased(LwKeyEvent e) {
        //Util.warn(this+" - keyReleased:"+e);
        super.keyReleased(e);
//Util.warnln("->"+getState());
    }

    public void keyTyped(LwKeyEvent e) {
        //Util.warn(this+" - keyTyped:"+e);
        super.keyTyped(e);
//Util.warnln("->"+getState());
    }

// --------------------- Interface LwMouseListener ---------------------


    public void mouseClicked(LwMouseEvent e) {
        //Util.warn(this+" - mouseClicked:"+e);
        super.mouseClicked(e);
//Util.warnln("->"+getState());
    }

    public void mouseEntered(LwMouseEvent e) {
        //Util.warn(this+" - mouseEntered:"+e);
        super.mouseEntered(e);
//Util.warnln("->"+getState());
    }

    public void mouseExited(LwMouseEvent e) {
        //Util.warn(this+" - mouseExited:"+e);
//super.mouseExited(e);
        //Util.warnln("->"+getState());
    }

    public void mousePressed(LwMouseEvent e) {
        //Util.warn(this+" - mousePressed:"+e);
        super.mousePressed(e);
//Util.warnln("->"+getState());
    }


    public void mouseReleased(LwMouseEvent e) {
        //Util.warn(this+" - mouseReleased:"+e);
        super.mouseReleased(e);
//Util.warnln("->"+getState());
    }

// --------------------- Interface Switchable ---------------------



    public void switched(boolean b) {
        //Util.warn(this+" - switched:"+b);
        super.switched(b);
//Util.warnln("->"+getState());
    }

// --------------------- Interface Widget ---------------------



    public void cleanup() {
    }
}
