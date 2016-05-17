/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import java.util.Hashtable;


public class Key {

// ------------------------------ FIELDS ------------------------------

    final static String MY_OFFER_FOREGROUND = "Stack Table My Offer Foreground";
    final static String MY_OFFER_FOREGROUND1 = "Stack Table My Offer Foreground1";

    /**
     * 
     */

    final String label;

// -------------------------- STATIC METHODS --------------------------

    public static void testee(String label, Object key1, Object key2) {
        long t = System.currentTimeMillis();
        long count = 0;
        Hashtable h = new Hashtable();
        while (count < 1000000) {
            if (key1 == key2) {
                h.put(MY_OFFER_FOREGROUND + count, new Object());
            }
            count++;
        }
        System.out.println(label + ":  " + (System.currentTimeMillis() - t));
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Key(String aLabel) {
        super();
        label = aLabel;
    }

// ------------------------ CANONICAL METHODS ------------------------

    public String toString() {
        return label.toString();
    }

// --------------------------- main() method ---------------------------

    public static void main(String[] args) {


        Key key = new Key(MY_OFFER_FOREGROUND);
        Key key1 = new Key(MY_OFFER_FOREGROUND1);
        test("STR KEY TIME", MY_OFFER_FOREGROUND, "Stack Table My Offer Foreground1");
        test("Obj KEY TIME", key, key1);
        //testee("Obj == KEY TIME", key, key1);
    }


    public static void test(String label, Object key1, Object key2) {
        long t = System.currentTimeMillis();
        Hashtable h = new Hashtable();
        long count = 0;
        while (count < 100000) {
            h.get(MY_OFFER_FOREGROUND + count);
            h.put(MY_OFFER_FOREGROUND + count, new Object());
            if (key1.equals(key2)) {
                ;
            }
            count++;
        }
        System.out.println(label + ":  " + (System.currentTimeMillis() - t));
    }
}
