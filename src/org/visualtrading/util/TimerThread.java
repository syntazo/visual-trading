/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.util;

import org.visualtrading.model.StaticObject;


public class TimerThread extends Thread implements StaticObject {

    private int delay = -1;

    private volatile int delta = 1000;// millsec

    private boolean isStopped = false;

    private int lastPosition = -1;

    private TimerThreadListener[] listeners = new TimerThreadListener[128];

    public TimerThread(String name) {
        this(name, -1);
    }

    public TimerThread(String name, int _delay) {
        super(name);
        this.delay = _delay;
    }

    public synchronized boolean addTickerListener(TimerThreadListener listener) {

        if (this.isInterrupted()) {
            throw new IllegalStateException("This thread was interrupted!");
        }

        if (listener == null) {
            return false;
        }

        if (indexOf(listener) >= 0) {
            return false;
        }
        if (++lastPosition == listeners.length) {
            TimerThreadListener[] tmp = new TimerThreadListener[2 * listeners.length];
            System.arraycopy(listeners, 0, tmp, 0, listeners.length - 1);
            listeners = tmp;
        }

        listeners[lastPosition] = listener;

        resetDelta();
        synchronized (listeners) {
            listeners.notify();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see com.icap.btecapplet.StaticObject#cleanup()
     */
    public void cleanup() {
        flushListeners();
    }

    public boolean isInterrupted() {
        return super.isInterrupted() || isStopped;
    }

    public synchronized boolean removeTickerListener(TimerThreadListener listener) {

        if (this.isInterrupted()) {
            throw new IllegalStateException("This thread was interrupted!");
        }

        int index = indexOf(listener);
        if (index < 0) {
            return false;
        }

        if (lastPosition == 0) {
            listeners[lastPosition--] = null;
        } else {

            listeners[index] = null;

            if (index != lastPosition) {
                System.arraycopy(listeners,
                                 index + 1,
                                 listeners,
                                 index,
                                 lastPosition - index);
            }
            lastPosition--;
        }

        resetDelta();

        synchronized (listeners) {
            listeners.notify();
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ie) {
                this.setStopped(true);
            }
        }

        // stop the thread through the interrupt
        while (!isInterrupted()) {

            while (lastPosition < 0) {
                try {
                    synchronized (listeners) {
                        listeners.wait();
                    }
                } catch (InterruptedException e1) {
                    // do nothing
                }
            }

            try {
                synchronized (listeners) {
                    for (int i = 0; i <= lastPosition; i++) {
                        listeners[i].timerTick(delta);
                    }
                }

                Thread.sleep(delta);
            } catch (InterruptedException e) {
                this.setStopped(true); // send the interrupt to this thread
            }
        }

        flushListeners();
        isStopped = true;
    }

    /**
     * @param _isStopped The isStopped to set.
     */
    public void setStopped(boolean _isStopped) {
        this.isStopped = _isStopped;
    }

    private void flushListeners() {
        synchronized (listeners) {
            for (int i = 0; i < lastPosition; i++) {
                listeners[i] = null;
            }
            lastPosition = -1;
        }
    }

    private int indexOf(Object key) {
        if (key == null) {
            return -1;
        }
        for (int i = 0; i <= lastPosition; i++) {
            if (listeners[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    private void resetDelta() {
        // FIXME find adjasent elements were new delta >0, otherwise use the first period
        delta = 250;
    }
}
