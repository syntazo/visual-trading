/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Label;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwTextRender;

import java.awt.*;


public class LabelBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public LabelBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {

        LwLabel label = (LwLabel) super.configure(application, obj, xml);
        String text = xml.getStringAttribute("LABEL");
        LwTextRender textRenderer = new LwTextRender(text);
        Font font = getFont(xml, textRenderer.getFont());
        textRenderer.setFont(font);
        Color color = getColor(xml, "foreground");
        if (color != null) {
            textRenderer.setForeground(color);
        }
        label.getViewMan(true).setView(textRenderer);
        label.setInsets(1, 1, 1, 1);
        return label;
    }

    public Class getClass(String className) {
        return Label.class;
    }
}
