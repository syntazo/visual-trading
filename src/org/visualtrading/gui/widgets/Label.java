/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Config;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.data.TextModel;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwTextRender;

public class Label extends LwLabel implements Widget {

// --------------------------- CONSTRUCTORS ---------------------------

    public Label() {
        super();

        init();
    }

    public void init() {
        setOpaque(false);
        setForeground(Config.WHITE);
    }

    /**
     * @param textRender
     */
    public Label(LwTextRender textRender) {
        super(textRender);

        init();
    }

    /**
     * @param textModel
     */
    public Label(TextModel textModel) {
        super(textModel);
        init();
    }


    public Label(String str) {
        super(str);
        init();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface Widget ---------------------

    public void cleanup() {
    }

// -------------------------- INNER CLASSES --------------------------

    static class OrderLabel extends LwLabel {

    }
}
