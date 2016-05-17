/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;




public class IntSpin extends Spin {

// ------------------------------ FIELDS ------------------------------

    private int min = Integer.MIN_VALUE,
    max = Integer.MAX_VALUE;

// --------------------------- CONSTRUCTORS ---------------------------

    public IntSpin() {
        super(new IntField());
        setStep(new Integer(1));
    }


    public IntSpin(int theMin, int theMax) {
        this();
        setRange(theMin, theMax);
    }

    public void setRange(int theMin, int theMax) {
        min = theMin;
        max = theMax;
    }


// -------------------------- OTHER METHODS --------------------------

    public Object decrement() {
        int step = ((Integer) getStep()).intValue();
        int size = ((Integer) getValue()).intValue();
        size -= step;
        return changeValue(size);
    }


    public Object getObject() {
        return getValue();
    }


    /* (non-Javadoc)
     * @see org.visualtrading.gui.widgets.Spin#inRange(java.lang.Object)
     */
    public boolean inRange(Object object) {
        if (max > min) {
            int value = ((Integer) object).intValue();
            return value >= min && value <= max;
        }
        return true;
    }


    public Object increment() {
        int step = ((Integer) getStep()).intValue();
        int size = ((Integer) getValue()).intValue();
        size += step;
        return changeValue(size);
    }

    public Object changeValue(int value) {
        Integer i = new Integer(value);
        return i;
    }
//    {
//        return Config.getKeyModel();
//    }


}
