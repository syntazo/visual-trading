/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;


import org.visualtrading.gui.Config;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LayoutContainer;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwAdvViewMan;
import org.zaval.lw.LwButton;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwImgRender;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwTextRender;
import org.zaval.lw.LwTitleInfo;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.LwView;
import org.zaval.lw.event.LwActionListener;
import org.zaval.lw.event.LwActionSupport;
import org.zaval.lw.event.LwFocusEvent;
import org.zaval.lw.event.LwFocusListener;
import org.zaval.lw.event.LwKeyEvent;
import org.zaval.lw.event.LwKeyListener;
import org.zaval.lw.event.LwMouseEvent;
import org.zaval.lw.event.LwMouseListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;


public class Notebook
        extends LwPanel
        implements LwMouseListener, LwKeyListener,
                   LwLayout, LwTitleInfo, LwFocusListener {

// ------------------------------ FIELDS ------------------------------

    /**
     * The tab border view id.
     */
    public static final int TABBR_VIEW = 0;

    /**
     * The selected tab border view id.
     */
    public static final int STABBR_VIEW = 1;

    /**
     * The selection marker view id.
     */
    public static final int MARKER_VIEW = 2;

    String[] buts = {"expand", "f", "s"};
    LwView[] onviews;
    LwButton[] offviews;

    Rectangle[] buttonBounds;

    private Vector pages = new Vector();
    private LwView[] views = new LwView[3];
    private LwTextRender textRender;
    private int selectedIndex = -1, selectedTabIndent = 1, tabBorderIndent = 1, orient, vgap, hgap;
    private int tabAreaX, tabAreaY, tabAreaWidth, tabAreaHeight, hTabGap = 1, vTabGap = 1;
    private int shift;
    private LwActionSupport support;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructs a notebook container using default (LwToolkit.TOP) tabs alignment.
     */
    public Notebook() {
        this(1);
    }

    /**
     * Constructs a notebook container using the specified tabs alignment.
     *
     * @param o the specified tabs alignement.
     */

    public Notebook(int decor) {

        orient = LwToolkit.TOP;
        setTabIndents(1, 1, 1, 1);

        textRender = new LwTextRender("");
        setView(TABBR_VIEW, new TabBrView(orient));
        setView(MARKER_VIEW, LwToolkit.getView("br.dot"));
        getViewMan(true).setBorder(LwToolkit.getView("nb.br"));
        decorate(decor);
    }

    /**
     * Sets the tab metrics.
     *
     * @param vg the vertical gap between the tab border and the tab view.
     * @param hg the horizontal gap between the tab border and the tab view.
     * @param ea the selected tab extra indent. The indent is used to extend selected tab size.
     * @param bi the border indent.
     */
    public void setTabIndents(int vg, int hg, int ea, int bi) {
        if (vTabGap != vg || hg != hTabGap || selectedTabIndent != ea || tabBorderIndent != bi) {
            vTabGap = vg;
            hTabGap = hg;
            selectedTabIndent = ea;
            tabBorderIndent = bi;
            vrp();
        }
    }

    /**
     * Sets the specified view for the given element type.
     *
     * @param type the given element type. Use the TABBR_VIEW (tab border view), STABBR_VIEW (selected tab border) or
     *             MARKER_VIEW (marker) constants as the argument value.
     * @param t    the specified view.
     */
    public void setView(int type, LwView t) {
        if (views[type] != t) {
            views[type] = t;
            vrp();
        }
    }

    protected void decorate(int decor) {
        //setLwLayout(new LwFlowLayout());
        offviews = new LwButton[decor];
        onviews = new LwView[decor];
        buttonBounds = new Rectangle[decor];
        for (int bi = 0; bi < decor; bi++) {
            LwButton button = new LwButton();
            LwAdvViewMan man = new LwAdvViewMan();
            if (bi > 0) {
                man.put("button.off", new LwImgRender("img/" + buts[bi] + "_off.gif"));
                man.put("button.on", new LwImgRender("img/" + buts[bi] + "_on.gif"));
            } else {
                man.put("button.on", new LwImgRender("img/" + buts[bi] + "_off.gif"));
                man.put("button.off", new LwImgRender("img/" + buts[bi] + "_on.gif"));

            }
            button.setViewMan(man);
            offviews[bi] = button;
        }

    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Gets the tab index of the selected tab.
     *
     * @return a tab index.
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Returns the tab border indent.
     *
     * @return a tab border indent.
     */
    public int getTabBorderIndent() {
        return tabBorderIndent;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwContainer ---------------------


    public /*C#override*/ void remove(int i) {
        if (selectedIndex == i) {
            select(-1);
        }
        pages.removeElementAt(i * 2 + 1);
        pages.removeElementAt(i * 2);
        super.remove(i);
    }

    public /*C#override*/ void removeAll() {
        if (selectedIndex >= 0) {
            select(-1);
        }
        pages.removeAllElements();
        super.removeAll();
    }

// --------------------- Interface LwFocusListener ---------------------


    public void focusGained(LwFocusEvent e) {
        if (selectedIndex < 0) {
            select(next(0, 1));
        }
    }

    public void focusLost(LwFocusEvent e) {
    }

// --------------------- Interface LwKeyListener ---------------------


    public void keyPressed(LwKeyEvent e) {
        if (selectedIndex != -1 && pages.size() > 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_LEFT:
                    {
                        int nxt = next(selectedIndex - 1, -1);
                        if (nxt >= 0) {
                            select(nxt);
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_RIGHT:
                    {
                        int nxt = next(selectedIndex + 1, 1);
                        if (nxt >= 0) {
                            select(nxt);
                        }
                    }
                    break;
            }
        }
    }

    public void keyReleased(LwKeyEvent e) {

    }

    public void keyTyped(LwKeyEvent e) {
    }

// --------------------- Interface LwLayout ---------------------


    public void componentAdded(Object id, Layoutable lw, int index) {
    }

    public void componentRemoved(Layoutable lw, int index) {
    }

    public Dimension calcPreferredSize(LayoutContainer target) {
        Dimension max = LwToolkit.getMaxPreferredSize(target);
        if (orient == LwToolkit.BOTTOM || orient == LwToolkit.TOP) {
            max.width = Math.max(2 * selectedTabIndent + max.width, tabAreaWidth);
            max.height += tabAreaHeight;
        } else {
            max.width += tabAreaWidth;
            max.height = Math.max(2 * selectedTabIndent + max.height, tabAreaHeight);
        }
        max.width += (hgap * 2);
        max.height += (vgap * 2);
        return max;
    }

    public void layout(LayoutContainer target) {
        Insets ti = getInsets();
        boolean b = (orient == LwToolkit.TOP || orient == LwToolkit.BOTTOM);

        if (tabAreaX == -1) {
            if (b) {
                tabAreaX = ti.left;
                tabAreaY = (orient == LwToolkit.TOP) ? ti.top : height - ti.bottom - tabAreaHeight;
            } else {
                tabAreaX = (orient == LwToolkit.LEFT) ? ti.left : width - ti.right - tabAreaWidth;
                tabAreaY = ti.top;
            }

            for (int i = 0; i < pages.size() / 2; i++) {
                Rectangle r = getTabBounds(i);
                if (b) {
                    r.y = tabAreaY + ((orient == LwToolkit.BOTTOM) ? tabBorderIndent : selectedTabIndent);
                } else {
                    r.x = tabAreaX + ((orient == LwToolkit.RIGHT) ? tabBorderIndent : selectedTabIndent);
                }
            }
        }

        for (int i = 0; i < count(); i++) {
            Layoutable l = get(i);
            if (isSelected(i)) {
                if (b) {
                    l.setSize(width - ti.left - ti.right - 2 * hgap,
                              height - tabAreaHeight - ti.top - ti.bottom - 2 * vgap);
                } else {
                    l.setSize(width - tabAreaWidth - ti.left - ti.right - 2 * hgap,
                              height - ti.top - ti.bottom - 2 * vgap);
                }

                l.setLocation(hgap + ((b || orient == LwToolkit.RIGHT) ? ti.left : tabAreaWidth + ti.left),
                              vgap +
                              ((orient == LwToolkit.RIGHT || orient == LwToolkit.BOTTOM || orient == LwToolkit.LEFT) ?
                               ti.top :
                               tabAreaHeight + ti.top));
            } else {
                l.setSize(0, 0);
            }
        }

        if (selectedIndex >= 0) {
            Rectangle r = getTabBounds(selectedIndex);
            int dt = 0;
            if (b) {
                dt = (r.x < ti.left) ?
                     ti.left - r.x
                     :
                     (r.x + r.width > width - ti.right) ? width - ti.right - r.x - r.width : 0;
            } else {
                dt = (r.y < ti.top) ?
                     ti.top - r.y
                     :
                     (r.y + r.height > height - ti.bottom) ? height - ti.bottom - r.y - r.height : 0;
            }
            for (int i = 0; i < pages.size() / 2; i++) {
                Rectangle br = getTabBounds(i);
                if (b) {
                    br.x += dt;
                } else {
                    br.y += dt;
                }
            }

            shift += dt;
        }
    }

// --------------------- Interface LwMouseListener ---------------------


    public void mouseClicked(LwMouseEvent e) {

    }

    public void mouseEntered(LwMouseEvent e) {
    }

    public void mouseExited(LwMouseEvent e) {
    }

    public void mousePressed(LwMouseEvent e) {
        if (LwToolkit.isActionMask(e.getMask())) {
            int index = getTabAt(e.getX(), e.getY());
            if (index >= 0 && isPageEnabled(index)) {
                select(index);
            }
            int button = onButtons(e);
            if (button > -1) {
                if (offviews[button].isPressed()) {
                    offviews[button].mouseReleased(e);
                } else {
                    offviews[button].mousePressed(e);
                }
            }
            repaint();
        }

    }

    public void mouseReleased(LwMouseEvent e) {
    }

// --------------------- Interface LwTitleInfo ---------------------


    public Rectangle getTitleBounds() {
        boolean b = (orient == LwToolkit.LEFT || orient == LwToolkit.RIGHT);
        Rectangle res = b ? new Rectangle(tabAreaX, 0, tabAreaWidth, 0)
                        : new Rectangle(0, tabAreaY, 0, tabAreaHeight);

        if (selectedIndex >= 0) {
            Rectangle r = getTabBounds(selectedIndex);
            if (b) {
                res.y = r.y;
                res.height = r.height;
            } else {
                res.x = r.x;
                res.width = r.width;
            }
        }
        return res;
    }
    /**
     * Gets the tabs alignment.
     *
     * @return a tabs alignment.
     */
    public int getTitleAlignment() {
        return orient;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Adds the page with the specified title.
     *
     * @param title the specified tab title. It is possible to use a string or a view as the title value.
     * @param c     the specified page component.
     */
    public void addPage(Object title, LwComponent c) {
        pages.addElement(title);
        pages.addElement(new Rectangle());
        add(c);
        if (selectedIndex < 0) {
            select(next(0, 1));
        }
    }

    private int next(int page, int d) {
        for (; page >= 0 && page < pages.size() / 2; page += d) {
            if (isPageEnabled(page)) {
                return page;
            }
        }
        return -1;
    }

    /**
     * Tests if the specified page is enabled or not.
     *
     * @param index the specified page index.
     *
     * @return <code>true</code> if the page is enaled;otherwise <code>false</code>.
     */
    public boolean isPageEnabled(int index) {
        return ((LwComponent) get(index)).isEnabled();
    }

    /**
     * Adds the specified selection listener to receive selection events from this notebook container.
     *
     * @param l the specified listener.
     */
    public void addSelectionListener(LwActionListener l) {
        if (support == null) {
            support = new LwActionSupport();
        }
        support.addListener(l);
    }

    protected /*C#override*/ int calcInsets(int type, int ci, int bi) {
        return type == orient ? 0 : bi;
    }

    public /*C#override*/ boolean canHaveFocus() {
        return true;
    }

    /**
     * Sets the given enabled state for the specified page.
     *
     * @param index the specified page index.
     * @param b     the given enabled state.
     */
    public void enablePage(int index, boolean b) {
        LwComponent c = (LwComponent) get(index);
        if (c.isEnabled() != b) {
            c.setEnabled(b);
            if (!b && selectedIndex == index) {
                select(-1);
            }
            repaint();
        }
    }


    /**
     * Selects the tab by the specified index. Use <code>-1</code> index value to de-select current selected tab, in
     * this case no one tab will be selected.
     *
     * @param index the specified item index.
     */
    public void select(int index) {
        if (selectedIndex != index) {
            selectedIndex = index;
            perform(selectedIndex);
            vrp();
        }
    }

    /**
     * Fires appropriate selection event for list of selection listeners. The method performs selection events. The
     * passed data argument identifies a selected tab index. The argument is represeted as java.lang.Integer object.
     *
     * @param index the tab index that has been selected.
     */
    protected /*C#virtual*/ void perform(int index) {
        if (support != null) {
            support.perform(this, new Integer(index));
        }
    }

    /**
     * Gets the default layout manager that is set with the container during initialization. The component defines
     * itself as the default layout. Don't use any other layout for the component, in this case the working of the
     * component can be wrong.
     *
     * @return a layout manager.
     */
    protected /*C#override*/ LwLayout getDefaultLayout() {
        return this;
    }

    /**
     * Returns the selected tab extra indent.
     *
     * @return a selected tab extra indent.
     */
    public int getSelTabIndent() {
        return selectedTabIndent;
    }

    /**
     * Gets the tab index that is located at the given location.
     *
     * @param theX the specified x coordinate.
     * @param theY the specified y coordinate.
     *
     * @return a tab index. The method returns <code>-1</code> if no one tabs can be found at the specified location.
     */
    public int getTabAt(int theX, int theY) {
        validate();
        if (theX >= tabAreaX &&
            theY >= tabAreaY &&
            theX < tabAreaX + tabAreaWidth &&
            theY < tabAreaY + tabAreaHeight) {
            for (int i = 0; i < pages.size() / 2; i++) {
                if (getTabBounds(i).contains(theX, theY)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the tab horizontal gap.
     *
     * @return a horizontal gap.
     */
    public int getTabHGap() {
        return hTabGap;
    }

    /**
     * Returns the tab vertical gap.
     *
     * @return a vertical gap.
     */
    public int getTabVGap() {
        return vTabGap;
    }

    /**
     * Returns the view of the given element type.
     *
     * @param type the given element type. Use the TABBR_VIEW (tab border view), STABBR_VIEW (selected tab border) or
     *             MARKER_VIEW (marker) constants as the argument value.
     *
     * @return an element view.
     */
    public LwView getView(int type) {
        return views[type];
    }

    /**
     * Tests if the tab with the specified index is selected or not.
     *
     * @return <code>true</code> if the tab with the specified index is selected; otherwise <code>false</code>.
     */
    public boolean isSelected(int i) {
        return i == selectedIndex;
    }

    public void loadConfig(XMLElement xml) {
        setBackground(Config.getBackground());

    }

    public int onButtons(LwMouseEvent e) {

        for (int bi = 0; bi < buttonBounds.length; bi++) {
            if (buttonBounds[bi].contains(e.getX(), e.getY())) {
                return bi;
            }
        }
        return -1;
    }

    /**
     * Paints this component.
     *
     * @param g the graphics context to be used for painting.
     */
    public /*C#override*/ void paint(Graphics g) {
        Rectangle clip = g.getClipBounds();
        if (selectedIndex >= 0) {
            Rectangle r = getTabBounds(selectedIndex);
            if (orient == LwToolkit.LEFT || orient == LwToolkit.RIGHT) {
                g.clipRect(r.x, tabAreaY, r.width, r.y - tabAreaY);
            } else {
                g.clipRect(tabAreaX, r.y, r.x - tabAreaX, r.height);
            }
        }

        for (int i = 0; i < selectedIndex; i++) {
            paintTab(g, i);
        }

        if (selectedIndex >= 0) {
            g.setClip(clip);
            Rectangle r = getTabBounds(selectedIndex);
            if (orient == LwToolkit.LEFT || orient == LwToolkit.RIGHT) {
                g.clipRect(r.x, r.y + r.height, r.width, height - r.y - r.height);
            } else {
                g.clipRect(r.x + r.width, r.y, width - r.x - r.width, r.height);
            }
        }

        for (int i = selectedIndex + 1; i < pages.size() / 2; i++) {
            paintTab(g, i);
        }

        if (clip != null) {
            g.setClip(clip);
        }

        if (selectedIndex >= 0) {
            paintTab(g, selectedIndex);
            if (hasFocus()) {
                drawMarker(g, getTabBounds(selectedIndex));
            }
        }
    }

    protected void paintTab(Graphics g, int pageIndex) {
        Rectangle b = getTabBounds(pageIndex);
        Layoutable page = get(pageIndex);
        if (getSelectedIndex() == pageIndex && views[STABBR_VIEW] != null) {
            views[STABBR_VIEW].paint(g, b.x, b.y, b.width, b.height, page);
        } else {
            views[TABBR_VIEW].paint(g, b.x, b.y, b.width, b.height, page);
        }

        LwView v = getTabView(pageIndex);
        Dimension ps = v.getPreferredSize();
        Point p = LwToolkit.getLocation(ps, LwToolkit.CENTER, LwToolkit.CENTER,
                                        b.width, b.height);
        v.paint(g, b.x + p.x, b.y + p.y, ps.width, ps.height, page);
        if (getSelectedIndex() == pageIndex) {
            v.paint(g, b.x + p.x + 1, b.y + p.y, ps.width, ps.height, page);
        }
        paintButtons(g, b, page);
        /*

             LwView iv = box.getViewMan(true).getView();
             Dimension ips = iv.getPreferredSize();
             Point     ip  = LwToolkit.getLocation (ips, LwToolkit.CENTER, LwToolkit.CENTER,
                                                 b.width, b.height);
             iv.paint(g, (getWidth()-ips.width * (10+1))-3, ip.y+2,page);
       */
    }

    private void paintButtons(Graphics g, Rectangle b, Layoutable page) {
        Color prevColor = g.getColor();
        g.setColor(getBackground());
        Dimension ips = offviews[0].getViewMan(true).getView().getPreferredSize();
        Point ip = LwToolkit.getLocation(ips, LwToolkit.CENTER, LwToolkit.CENTER,
                                         b.width, b.height);
        g.fillRect(getWidth() - ips.width * offviews.length - 10, 1, ips.width * offviews.length + 10, ip.y +
                                                                                                       ips.height +
                                                                                                       2);
        g.setColor(prevColor);
        for (int bi = 0; bi < offviews.length; bi++) {
            LwView iv = offviews[bi].getViewMan(true).getView();
            ips = iv.getPreferredSize();
            ip = LwToolkit.getLocation(ips, LwToolkit.CENTER, LwToolkit.CENTER,
                                       b.width, b.height);
            iv.paint(g, (getWidth() - ips.width * (bi + 1)) - 3, ip.y + 2, page);

            buttonBounds[bi] = new Rectangle((getWidth() - ips.width * (bi + 1)) - 3, ip.y + 2, ips.width, ips.height);
        }


    }

    /**
     * Renders the marker for the specified tab bounds using the given graphical context.
     *
     * @param g the specified context to be used for painting.
     * @param r the specified tab bounds.
     */
    protected void drawMarker(Graphics g, Rectangle r) {

        views[MARKER_VIEW].paint(g, r.x + views[TABBR_VIEW].getLeft(),
                                 r.y + views[TABBR_VIEW].getTop(),
                                 r.width - views[TABBR_VIEW].getLeft() - views[TABBR_VIEW].getRight(),
                                 r.height - views[TABBR_VIEW].getTop() - views[TABBR_VIEW].getBottom(), this);


    }

    /**
     * Renders the given tab using the given graphical context.
     *
     * @param g         the specified context to be used for painting.
     * @param pageIndex the specified tab index.
     */
    protected /*C#virtual*/ void paintTab1(Graphics g, int pageIndex) {
        Rectangle b = getTabBounds(pageIndex);
        Layoutable page = get(pageIndex);
        if (getSelectedIndex() == pageIndex && views[STABBR_VIEW] != null) {
            views[STABBR_VIEW].paint(g, b.x, b.y, b.width, b.height, page);
        } else {
            views[TABBR_VIEW].paint(g, b.x, b.y, b.width, b.height, page);
        }

        LwView v = getTabView(pageIndex);
        Dimension ps = v.getPreferredSize();
        Point p = LwToolkit.getLocation(ps, LwToolkit.CENTER, LwToolkit.CENTER,
                                        b.width, b.height);

        /*v.paint (g, b.x + p.x, b.y + p.y, ps.width, ps.height, page);
        */
        //g.setColor(Color.PINK);
        //g.fillRect(b.x + p.x + 1, b.y + p.y, ps.width, ps.height);
        if (getSelectedIndex() == pageIndex) {
            v.paint(g, b.x + p.x + 1, b.y + p.y, ps.width, ps.height, page);
        }

    }

    /**
     * Gets the view for the specified tab.
     *
     * @param index the specified tab index.
     *
     * @return a tab view.
     */
    protected /*C#virtual*/ LwView getTabView(int index) {
        Object data = pages.elementAt(2 * index);
        if (data instanceof LwView) {
            return (LwView) data;
        }
        textRender.getTextModel().setText((String) data);
        return textRender;

    }

    /**
     * The method is overridden by the component for internal usage. Don't tuch the method.
     */
    protected /*C#override*/ void recalc() {
        int count = pages.size() / 2;
        if (count > 0) {
            tabAreaHeight = 0;
            tabAreaWidth = 0;

            Insets ti = getInsets();
            boolean b = (orient == LwToolkit.LEFT || orient == LwToolkit.RIGHT);
            int hadd = views[TABBR_VIEW].getLeft() + views[TABBR_VIEW].getRight() + 2 * hTabGap;
            int vadd = views[TABBR_VIEW].getTop() + views[TABBR_VIEW].getBottom() + 2 * vTabGap;
            int sx = ti.left + ((orient == LwToolkit.RIGHT) ? 0 : selectedTabIndent);
            int sy = ti.top + ((orient == LwToolkit.BOTTOM) ? 0 : selectedTabIndent);
            int max = 0;

            for (int i = 0; i < count; i++) {
                Dimension ps = getTabView(i).getPreferredSize();
                Rectangle r = getTabBounds(i);
                if (b) {
                    r.height = ps.height + vadd;
                    r.y = sy;
                    sy += r.height;
                    if (ps.width + hadd > max) {
                        max = ps.width + hadd;
                    }
                    tabAreaHeight += r.height;
                } else {
                    r.width = ps.width + hadd;
                    r.x = sx;
                    sx += r.width;
                    if (ps.height + vadd > max) {
                        max = ps.height + vadd;
                    }
                    tabAreaWidth += r.width;
                }
            }

            for (int i = 0; i < count; i++) {
                Rectangle r = getTabBounds(i);
                if (b) {
                    r.width = max;
                    r.y += shift;
                } else {
                    r.height = max;
                    r.x += shift;
                }
            }

            if (b) {
                tabAreaWidth = (max + selectedTabIndent + tabBorderIndent);
                tabAreaHeight += (2 * selectedTabIndent);
            } else {
                tabAreaWidth += (2 * selectedTabIndent);
                tabAreaHeight = (max + selectedTabIndent + tabBorderIndent);
            }

            if (selectedIndex >= 0) {
                Rectangle r = getTabBounds(selectedIndex);
                r.x -= (orient == LwToolkit.RIGHT) ? tabBorderIndent : selectedTabIndent;
                r.y -= (orient == LwToolkit.BOTTOM) ? tabBorderIndent : selectedTabIndent;
                r.width += b ? tabBorderIndent + selectedTabIndent : 2 * selectedTabIndent;
                r.height += b ? 2 * selectedTabIndent : tabBorderIndent + selectedTabIndent;
            }
            tabAreaX = -1;
        }
    }

    /**
     * Gets the tab bounds. The returned bounds is not a copy, it means you should not change its fields since it can
     * bring to fault.
     *
     * @param index the specified tab index.
     *
     * @return a tab bounds.
     */
    protected /*C#virtual*/ Rectangle getTabBounds(int index) {
        return (Rectangle) pages.elementAt(2 * index + 1);
    }

    /**
     * Removes the specified selection listener so it no longer receives selection events from this notebook conatiner.
     *
     * @param l the specified listener.
     */
    public void removeSelectionListener(LwActionListener l) {
        if (support != null) {
            support.removeListener(l);
        }
    }

    public XMLElement saveConfig() {
        return null;
    }

    /**
     * Sets the notebook layout gaps. The gaps are used as the vertical and horizontal indents to place content
     * components.
     *
     * @param vg the vertical layout gap.
     * @param hg the horizontal layout gap.
     */
    public void setGaps(int vg, int hg) {
        if (vgap != vg || hg != hgap) {
            vgap = vg;
            hgap = hg;
            vrp();
        }
    }

    public /*C#override*/ void setSize(int w, int h) {
        if (width != w || height != h) {
            if (orient == LwToolkit.RIGHT || orient == LwToolkit.BOTTOM) {
                tabAreaX = -1;
            }
            super.setSize(w, h);
        }
    }

    /**
     * Sets the specified page title.
     *
     * @param pageIndex the specified page index.
     * @param data      the specified tab title. It is possible to use a string or a view as the title value.
     */
    public void setTitle(int pageIndex, Object data) {
        if (!pages.elementAt(2 * pageIndex).equals(data)) {
            pages.setElementAt(data, pageIndex * 2);
            vrp();
        }
    }


}
