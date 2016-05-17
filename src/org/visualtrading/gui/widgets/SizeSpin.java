/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Part;
import org.visualtrading.model.Field;
import org.visualtrading.model.Model;
import org.visualtrading.xml.nanoxml.XMLElement;

public abstract class SizeSpin extends IntSpin implements Field, Part {

// ------------------------------ FIELDS ------------------------------

    String id;

// --------------------------- CONSTRUCTORS ---------------------------

    public SizeSpin() {
        super(0, 10000);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public void setId(String theId) {
        id = theId;

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Part ---------------------

    public abstract void getFrom(Model order);
//    {
//        int size = ((Order) order).getSize();
//        setValue(new Integer(size));
//    }

    public abstract void setOn(Model order);

// -------------------------- OTHER METHODS --------------------------

//    {
//        try {
//            Input editor = (Input)getEditor();
//            int size  = Integer.parseInt(editor.toString(getValue()));
//            ((Order) order).setSize(size);
//        } catch (NumberFormatException e) {
//            System.err.println(e);
//        }
//    }

    public void loadConfig(XMLElement xml) {
        // TODO
    }

    public XMLElement saveConfig() {
        return null;
    }

}
