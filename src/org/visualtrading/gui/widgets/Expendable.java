/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.model.NewModel;
import org.zaval.lw.Layoutable;

/**
 * Created on: Jun 13, 2005
 * <p/>
 * Project: BTecLite
 *
 * @author Yaroslav Chinskiy
 */
public interface Expendable extends Layoutable{

    public Panel getExpendedView(NewModel criteria);

    public Panel getCollapsedView(NewModel criteria);

    public Expender attach(Expender expender);

    public Expender detach(Expender expender);


}
