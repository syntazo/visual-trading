/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.ScrollPane;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.LwView;

import java.awt.*;

public abstract class ComponentBuilder extends ZavalBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public ComponentBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    protected Object addScrollPane(Application application, Object obj, XMLElement xml) {
        LwComponent component = (LwComponent) obj;
        int scroll = getAlignment(xml, "scroll", 0);
        if (scroll > 0) {
            return new ScrollPane(component, scroll);
        }
        return component;
    }


    public Object configure(Application application, Object obj, XMLElement xml) {
        LwComponent component = (LwComponent) obj;
        setBackground(component, xml);
        setEnabled(component, xml);
        setInsets(component, xml);
        setLocation(component, xml);
        setOpaque(component, xml);
        setSize(component, xml);
        setVisible(component, xml);
        setView(component, xml);
        setBg(component, xml);
        setBorder(component, xml);
        return component;
    }


    public void setBackground(LwComponent component, XMLElement xml) {
        Color background = getColor(xml, "background");
        if (background != null) {
            component.setBackground(background);
        }
    }

    public void setEnabled(LwComponent component, XMLElement xml) {
        component.setEnabled(getBoolean(xml, "enabled", component.isEnabled()));
    }


    public void setInsets(LwComponent component, XMLElement xml) {
        Insets insets = getInsets(xml, "insets", component.getInsets());
        component.setInsets(insets.top, insets.left, insets.bottom, insets.right);
    }


    public void setLocation(LwComponent component, XMLElement xml) {
        Point p = getLocation(xml, component.getLocation());
        component.setLocation(p.x, p.y);
    }

    public void setOpaque(LwComponent component, XMLElement xml) {
        component.setOpaque(getBoolean(xml, "opaque", component.isOpaque()));
    }

    public void setSize(LwComponent component, XMLElement xml) {
        Dimension size = getSize(xml, component.getSize());
        component.setSize(size.width, size.height);
    }

    public void setVisible(LwComponent component, XMLElement xml) {
        component.setVisible(getBoolean(xml, "visible", true));
    }

    public void setView(LwComponent component, XMLElement xml) {
        LwView view = getView(xml, "view");
        if (view != null) {
            component.getViewMan(true).setView(view);
        }
    }

    public LwView getView(XMLElement xml, String name) {
        String viewName = xml.getStringAttribute(name);
        if (viewName != null) {
            LwView view = (LwView) getScope().get(viewName);
            if (view == null) {
                return LwToolkit.getView(viewName);
            }
            return view;
        }
        return null;
    }

    public void setBg(LwComponent component, XMLElement xml) {
        LwView view = getView(xml, "Bg");
        if (view != null) {
            component.getViewMan(true).setBg(view);
        }
    }

    public void setBorder(LwComponent component, XMLElement xml) {
        LwView view = getView(xml, "BORDER");
        if (view != null) {
            component.getViewMan(true).setBorder(view);
        }
    }


}
