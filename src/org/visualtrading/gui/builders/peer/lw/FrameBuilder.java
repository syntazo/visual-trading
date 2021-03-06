/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */

package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.builders.Builder;
import org.visualtrading.model.Application;
import org.visualtrading.model.ModelManager;
import org.visualtrading.model.User;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwContainer;
import org.zaval.lw.LwFrame;
import org.zaval.lw.event.LwFocusEvent;


public abstract class FrameBuilder extends ZavalBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public FrameBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    /* (non-Javadoc)
     * @see org.visualtrading.gui.builders.Builder#addChildren(java.lang.Object, nanoxml.XMLElement)
     */
    public Object addChildren(Application application, Object obj, XMLElement xml) {
        LwFrame frame = (LwFrame) obj;
        LwContainer root = frame.getRoot();
        root.setLwLayout(getLayout(xml));
        //System.out.println(getLayout(xml)+ " : "+ xml);
        super.addChildren(application, root, xml);
        frame.repaint();
        return frame;
    }

    /* (non-Javadoc)
     * @see XmlObject#configure(nanoxml.XMLElement)
     */
    public void configure(XMLElement xml) {

    }


    /* (non-Javadoc)
     * @see org.visualtrading.gui.builders.Builder#configure(java.lang.Object, nanoxml.XMLElement)
     */
    public Object configure(Application application, Object obj, XMLElement xml) {
        LwFrame frame = (LwFrame) obj;
        frame.setSize(getSize(xml, frame.getSize()));
        frame.setVisible(getBoolean(xml, "visible", frame.isVisible()));
        frame.setTitle(xml.getStringAttribute("title", "HiHo"));

        return frame;
    }

    public void focusGained(LwFocusEvent arg0) {
        //System.out.println("focusGained");

    }

    public void focusLost(LwFocusEvent arg0) {
        // System.out.println("focusLost");
    }

    public Class getClass(String className) {
        return LwFrame.class;
    }

// --------------------------- main() method ---------------------------

    public static void main(String[] args) throws Exception {
        Builder.process(new Application() {

            public User getUser() {

                return null;
            }

            public ModelManager getModelManager() {

                return null;
            }

            public LwContainer getRoot() {
                return null;
            }
        }, ZavalBuilder.class, "frame.xml");

    }

}
