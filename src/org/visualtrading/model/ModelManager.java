/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import java.util.Hashtable;


public class ModelManager {

// ------------------------------ FIELDS ------------------------------

    Hashtable models = new Hashtable();

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public ModelManager() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Model getModel(ModelBuilder builder) {

        Model model = (Model) models.get(builder);
        if (model == null) {
            model = builder.build(null);
            models.put(builder, model);
        }
        return model;
    }

    public void setModel(Model model) {

        models.put(model.getBuilder(), model);
    }
}
