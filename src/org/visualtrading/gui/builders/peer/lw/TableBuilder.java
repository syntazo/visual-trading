/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Table;


public class TableBuilder extends CompositeBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public TableBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Class getClass(String className) {
        return Table.class;
    }

}
