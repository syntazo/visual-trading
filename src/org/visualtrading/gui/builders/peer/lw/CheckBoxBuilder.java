/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.Config;
import org.visualtrading.gui.widgets.CheckBox;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.data.Text;
import org.zaval.lw.LwCheckbox;
import org.zaval.lw.LwFlowLayout;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwToolkit;

import java.awt.*;


public class CheckBoxBuilder extends CompositeBuilder {

// ------------------------------ FIELDS ------------------------------

    final static Font FONT = new Font("Dialog", Font.BOLD, 14);

// --------------------------- CONSTRUCTORS ---------------------------

    public CheckBoxBuilder() {

        super();
    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        super.configure(application, obj, xml);

        LwCheckbox checkbox = (LwCheckbox) obj;
        checkbox.setLwLayout(new LwFlowLayout(LwToolkit.LEFT, LwToolkit.CENTER, LwToolkit.HORIZONTAL));
        String text = xml.getStringAttribute("LABEL");
        LwLabel lab = new LwLabel(new Text(text));
        Font font = getFont(xml, lab.getTextRender().getFont());
        lab.getTextRender().setFont(font);
        Color color = getColor(xml, "foreground");
        if (color != null) {
            lab.getTextRender().setForeground(color);
        }
        setComponent(checkbox, lab, 1);
        checkbox.getBox().getViewMan(true).setBorder("br.sunken");
        checkbox.getBox().setBackground(Config.WHITE);
        checkbox.getBox().setOpaque(true);
        //checkbox.setPSSize(35, 24);
        return checkbox;
    }


    public Class getClass(String className) {

        return CheckBox.class;
    }

}