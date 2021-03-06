/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 */
package org.visualtrading.util;

public abstract class TimerThreadListener implements Runnable {

    private int period;

    private long timeLine = 0;

    /**
     *
     */
    public TimerThreadListener(int _period) {
        super();
        if (_period <= 0) {
            throw new IllegalArgumentException("Period can not be <= 0");
        }

        this.period = _period;
    }

    /**
     * @return Returns the period.
     */
    public int getPeriod() {
        return this.period;
    }

    public long getTimeLine() {
        return timeLine;
    }

    public void timerTick(long delta) {
        timeLine += delta;
        if (timeLine % period == 0) {
            run();
        }
    }
}
