/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.gui.widgets;

import com.ibm.math.BigDecimal;
import com.ibm.math.MathContext;


public class DoubleSpin extends Spin {

// ------------------------------ FIELDS ------------------------------

    private double min = Double.MIN_VALUE,
    max = Double.MAX_VALUE;

// --------------------------- CONSTRUCTORS ---------------------------

    public DoubleSpin() {
        super(new DoubleField());
        setStep(new BigDecimal(.1));

    }

    public DoubleSpin(double theMin, double theMax) {
        this();
        min = theMin;
        max = theMax;
    }


// -------------------------- OTHER METHODS --------------------------

    public Object changeValue(double value) {
        BigDecimal d = new BigDecimal(value);
        return d;
    }

    public Object decrement() {
        BigDecimal value = (BigDecimal) getValue();
        value = value.subtract((BigDecimal) getStep(), MathContext.DEFAULT);
        return value;

    }

    public boolean inRange(Object object) {
        double value = ((BigDecimal) object).doubleValue();
        return value >= min && value <= max;
    }

    public Object increment() {

        BigDecimal value = (BigDecimal) getValue();
        value = value.add((BigDecimal) getStep(), MathContext.DEFAULT);
        return value;
    }

    public void setRange(int theMin, int theMax) {
        min = theMin;
        max = theMax;
    }
//        return Config.getKeyModel();
//    }
}
