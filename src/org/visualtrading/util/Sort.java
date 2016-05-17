/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.util;

import java.util.Dictionary;
import java.util.Enumeration;


public class Sort {

// ------------------------------ FIELDS ------------------------------

    public static Comparator stringComparator = new Comparator() {

        public boolean equals(Object lhs, Object rhs) {
            return lhs.equals(rhs);
        }

        public int compare(Object lhs, Object rhs) {
            return ((String) lhs).compareTo((String) rhs);
        }

    };

// -------------------------- STATIC METHODS --------------------------

    public static Object[] sort(Dictionary table, Comparator comparator) {
        Object[] sorted = new Object[table.size()];
        for (Enumeration e = table.keys(); e.hasMoreElements();) {
            sortedInsert(sorted, e.nextElement(), comparator);
        }
        return sorted;
    }

    public static void sortedInsert(Object[] values, Object value, Comparator comparator) {
        int i = values.length - 2;
        for (; i >= 0 && (values[i] == null || comparator.compare(values[i], value) > 0); i--) {
            values[i + 1] = values[i];
        }
        values[i + 1] = value;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public Sort() {
        super();

    }
}
