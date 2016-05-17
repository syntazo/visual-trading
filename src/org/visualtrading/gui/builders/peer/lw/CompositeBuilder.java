/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.zaval.lw.LwComponent;
import org.zaval.lw.LwComposite;


public abstract class CompositeBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public CompositeBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public void setComponent(LwComposite container, LwComponent newComp, int pos) {

        if (container.count() > pos) {
            container.remove(pos);
        }
        container.add(newComp);
        container.invalidate();
    }
}
