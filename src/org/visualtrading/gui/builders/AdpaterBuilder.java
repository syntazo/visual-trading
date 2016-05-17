/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders;

import org.visualtrading.model.Application;
import org.visualtrading.model.Form;
import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Enumeration;


public abstract class AdpaterBuilder extends Builder {

// -------------------------- OTHER METHODS --------------------------

    /* (non-Javadoc)
     * @see org.visualtrading.gui.builders.Builder#configure(org.visualtrading.Application, java.lang.Object, nanoxml.XMLElement)
     */
    public Object configure(Application application, Object obj, XMLElement xml) {

        return obj;
    }

    public Object embed(Application application, Object obj, XMLElement xml) {
        Form form = (Form) obj;
        if (xml.countChildren() > 0) {
            return addChildren(application, obj, xml);
        }
        form.setFields(getScope());
        return form;
    }

    public Object addChildren(Application application, Object obj, XMLElement xml) {
        Form form = (Form) obj;
        for (Enumeration enum = xml.enumerateChildren(); enum.hasMoreElements();) {
            XMLElement element = (XMLElement) enum.nextElement();
            String fieldName = element.getName();
            Object field = getScope().get(fieldName);
            if (field != null) {
                form.getFields().put(fieldName, field);
            }
        }
        return form;
    }

    /* (non-Javadoc)
     * @see org.visualtrading.gui.builders.Builder#getClass(java.lang.String)
     */
    public Class getClass(String className) {

        return AdpaterBuilder.class;
    }

}
