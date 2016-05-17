/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.model.NewModel;
import org.zaval.lw.LwLayout;

public class Expender extends Panel {

    private NewModel model;
    private Expendable expendable;
    private boolean isCollapsed = false;

    public Expender(LwLayout layout, NewModel _model) {
        super(layout);
        setModel(model);
        this.add(new Panel());
    }


    private void setModel(NewModel _model) {
        this.model = _model;
        if (expendable != null) {
            if (isCollapsed) {
//                collapse();
            } else {
//                expend();
            }
        }
    }

    public void attach(Expendable _expendable, boolean isCollapsed) {
        this.expendable = _expendable;
        if (isCollapsed) {
//            collapse();
        } else {
//            expend();
        }
    }

    public void process(boolean collapse) {
        if (isCollapsed == collapse) {
            return;
        }
        isCollapsed = collapse;
        if (expendable != null) {
            this.remove(0);
            this.insert(0, isCollapsed ? expendable.getExpendedView(model) : expendable.getCollapsedView(model));
        }
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }
}
