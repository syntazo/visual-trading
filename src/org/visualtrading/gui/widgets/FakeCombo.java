/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.gui.Config;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwCombo;
import org.zaval.lw.LwList;


public class FakeCombo extends LwCombo implements Widget {

// ------------------------------ FIELDS ------------------------------

    /**
     *
     */
    final int givenValue;

// --------------------------- CONSTRUCTORS ---------------------------

    public FakeCombo(int index) {
        super();
        givenValue = index;
        setPSSize(50, 20);
        setBackground(Config.WHITE);

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface LwActionListener ---------------------

/* (non-Javadoc)
 * @see org.zaval.lw.event.LwActionListener#actionPerformed(java.lang.Object, java.lang.Object)
 */
    public void actionPerformed(Object src, Object data) {
        super.actionPerformed(src, data);
        if (data != null) {
            LwList list = this.getList();
            if (src == list) {
                if (list.getSelectedIndex() != givenValue) {
                    list.select(givenValue);
                }
            }
        }
    }

// --------------------- Interface Widget ---------------------


    public void cleanup() {
    }
}
