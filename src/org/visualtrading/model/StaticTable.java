/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.zaval.lw.LwToolkit;

import java.util.Enumeration;
import java.util.Hashtable;

public final class StaticTable implements StaticObject {

// ------------------------------ FIELDS ------------------------------

    Hashtable staticObjects;

// -------------------------- STATIC METHODS --------------------------

    //private static StaticTable self;
    public static synchronized StaticTable getTable() {
        StaticTable table = (StaticTable) LwToolkit.getStaticObj("BtecStatics");
        if (table == null) {
            table = new StaticTable();
            LwToolkit.putStaticObj("BtecStatics", table);
        }
        return table;
    }
    
//    public static synchronized StaticTable getTableOld() 
//    {
//       if (self == null)
//           self = new StaticTable();
//       return self;
//    }
//    
    

    public static synchronized Object get(Object key) {
        StaticTable self = getTable();
        return self.staticObjects.get(key);
    }

    public static synchronized Object put(String key, Object obj) {
        StaticTable self = getTable(); 
        //Util.infoln("ADDING TO StaticTable: "+self+"<-"+key+"->"+obj);
        //Util.infoln("ADDING TO self.staticObjects: "+self.staticObjects);
        return (obj == null) ? self.staticObjects.remove(key) : self.staticObjects.put(key, obj);
    }

    public static void shutdown() {
        StaticTable self = getTable();
        self.cleanup();
    }

    public void cleanup() {
        // synchronized (self)
        // {
        if (staticObjects != null) {
            for (Enumeration en = staticObjects.elements(); en.hasMoreElements();) {
                Object staticObject = en.nextElement();
                if (staticObject instanceof StaticObject) {
                    ((StaticObject) staticObject).cleanup();
                }
            }

            staticObjects.clear();
            //  self = null;
            // }
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private StaticTable() {
        super();
        staticObjects = new Hashtable();
    }
}