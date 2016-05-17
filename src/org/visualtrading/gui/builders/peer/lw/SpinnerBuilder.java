/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.builders.Builder;
import org.visualtrading.gui.widgets.DoubleSpin;
import org.visualtrading.gui.widgets.IntSpin;
import org.visualtrading.gui.widgets.SizeSpin;
import org.visualtrading.gui.widgets.Spin;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;

import java.awt.*;


public class SpinnerBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    public SpinnerBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

/* (non-Javadoc)
 * @see org.visualtrading.gui.builders.Builder#configure(org.visualtrading.model.Application, java.lang.Object, org.visualtrading.xml.nanoxml.XMLElement)
 */
    public Object configure(Application application, Object obj, XMLElement xml) {

        Spin spinner = (Spin) super.configure(application, obj, xml);
        Dimension dim = Builder.getDimension(xml, "range", null);
        if (dim != null) {
            spinner.setRange(dim.width, dim.height);
        }
        return spinner;
    }


    public Class getClass(String className) {
        if (className == null) {
            return IntSpin.class;
        }
        if (className.equalsIgnoreCase("size")) {
            return SizeSpin.class;
        }
        if (className.equalsIgnoreCase("float")) {
            return DoubleSpin.class;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return IntSpin.class;
    }
}
