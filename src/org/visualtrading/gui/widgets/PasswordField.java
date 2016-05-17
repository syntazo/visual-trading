/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.data.TextModel;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwTextRender;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.TxtSelectionInfo;

import java.awt.*;


public class PasswordField extends TextField {

// --------------------------- CONSTRUCTORS ---------------------------

    public PasswordField() {

        init();

    }

    private void init() {

        this.getViewMan(true).setView(new LwPasswordText(getTextModel()));

    }

    public PasswordField(String s, int i) {

        super(s, i);
        init();

    }

// -------------------------- INNER CLASSES --------------------------

    class LwPasswordText
            extends LwTextRender {

// ------------------------------ FIELDS ------------------------------

        private char echo = '*';

// --------------------------- CONSTRUCTORS ---------------------------

        public LwPasswordText(TextModel text) {

            super(text);

        }

// -------------------------- OTHER METHODS --------------------------

        protected void paintSelection(Graphics g,
                                      int theX,
                                      int theY,
                                      int line,
                                      Layoutable d) {

            if (d instanceof TxtSelectionInfo) {
                TxtSelectionInfo selection = (TxtSelectionInfo) d;
                Point p1 = selection.getStartSelection();
                if (p1 != null) {
                    Point p2 = selection.getEndSelection();
                    if (!p1.equals(p2)) {
                        Point start, end;
                        if (p1.y > p2.y) {
                            start = p2;
                            end = p1;
                        } else {
                            start = p1;
                            end = p2;
                        }
                        String s = getLine(line);
                        int w = lineWidth(line);
                        theX += substrWidth(s, 0, start.y);
                        w = substrWidth(s, 0, end.y - start.y);
                        int indent = getLineIndent();
                        LwToolkit.drawMarker(g,
                                             theX,
                                             theY,
                                             w == 0 ? 1 : w,
                                             getLineHeight() + indent,
                                             ((LwComponent) d).getBackground(),
                                             selection.getSelectColor());
                    }
                }
            }

        }

        protected /*C#override*/ String getLine(int r) {

            String s = super.getLine(r);
            char[] buf = new char[s.length()];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = echo;
            }
            return new String(buf);

        }

        public int substrWidth(String s, int off, int len) {

            return getFontMetrics().charWidth('*') * len;

        }

        public void setEchoChar(char ch) {

            if (echo != ch) {
                echo = ch;
                invalidate();
            }

        }

    }

}

