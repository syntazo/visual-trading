/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LwToolkit;
import org.zaval.lw.event.LwActionListener;
import org.zaval.lw.event.LwMouseEvent;
import org.zaval.lw.event.LwMouseListener;
import org.zaval.lw.event.LwMouseMotionListener;

import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

public class ColorChooser extends Panel implements LwMouseListener,
                                                   LwMouseMotionListener {

// ------------------------------ FIELDS ------------------------------

    private static final int numCols = 22;
    private static final int numRows = 18;

    private static final Polygon polygons[][] = new Polygon[numCols][numRows];;
    private static final Color hexColor[][] = new Color[numCols][numRows];

    private Vector listeners = new Vector();
    private int hexWidth = 12;
    private int hexHeight = 10;
    private int col;
    private int row;
    private int currentCol = 0;
    private int currentRow = 0;
    private int inc;
    private int leftBorder;
    private int topBorder;

    private int bigWidthStretch = (hexWidth * 2) / 4;
    private int bigHeightStretch = (hexHeight * 2) / 4;

// --------------------------- CONSTRUCTORS ---------------------------

    public ColorChooser() {

        init();
        LwToolkit.getEventManager().addMouseListener(this);

    }

    public void init() {

        int num_inc = 6;
        inc = 255 / (num_inc - 1);
        leftBorder = bigWidthStretch;
        topBorder = bigHeightStretch;
        int hexy = 0;
        int hexx = 0;
        int rowStart = 0;
        int rowEnd = 0;
        int changePt = 0;
        int changePt2 = 0;
        int changePt3 = 0;
        for (col = 0; col < numCols; col++) {
            hexx = col * (hexWidth - hexWidth / 4) + leftBorder;
            hexy = topBorder;
            if (col % 2 == 0) {
                hexy += hexHeight / 2;
            }
            for (row = 0; row < numRows; row++) {
                hexy += hexHeight;

                polygons[col][row] = new Polygon();
                polygons[col][row].addPoint(hexx, hexy);
                polygons[col][row].addPoint(hexx + hexWidth / 4,
                                            hexy - hexHeight / 2);
                polygons[col][row].addPoint(hexx + (hexWidth * 3) / 4,
                                            hexy - hexHeight / 2);
                polygons[col][row].addPoint(hexx + hexWidth, hexy);
                polygons[col][row].addPoint(hexx + (hexWidth * 3) / 4,
                                            hexy + hexHeight / 2);
                polygons[col][row].addPoint(hexx + hexWidth / 4,
                                            hexy + hexHeight / 2);
                polygons[col][row].addPoint(hexx, hexy);
            }
        }

        for (col = 0; col < numCols; col++) {
            if (col == 0) {
                rowStart = num_inc / 2 - 1;
                rowEnd = (rowStart + num_inc) - 1;
            } else if (col > 0 && col < num_inc - 1) {
                if (col % 2 == 0) {
                    rowStart--;
                } else {
                    rowEnd++;
                }
            } else if (col == num_inc - 1) {
                rowStart = 0;
                rowEnd = 3 * num_inc - 3;
            } else if (col >= num_inc && col < (num_inc + num_inc) - 2) {
                if (col % 2 == 1) {
                    rowStart++;
                    rowEnd++;
                }
            } else if (col == (num_inc + num_inc) - 2) {
                rowStart = num_inc / 2 - 1;
                rowEnd = ((3 * num_inc - 3) + num_inc / 2) - 1;
            } else if (col >= (num_inc + num_inc) - 1 &&
                       col < (num_inc + num_inc + num_inc) - 3) {
                if (col % 2 == 0) {
                    rowStart--;
                    rowEnd--;
                }
            } else if (col == (num_inc + num_inc + num_inc) - 3) {
                rowStart = 0;
                rowEnd = 3 * num_inc - 3;
            } else if (col == (num_inc + num_inc + num_inc) - 2) {
                rowEnd = 2 * num_inc - 3;
            } else if (col % 2 == 1) {
                rowStart++;
            } else {
                rowEnd--;
            }
            for (row = 0; row < numRows; row++) {
                if (row < rowStart || row > rowEnd) {
                    continue;
                }
                if (col == 0) {
                    hexColor[col][row] =
                    new Color(255, 255, (rowEnd - row) * inc);
                    continue;
                }

                if (col > 0 && col < num_inc - 1) {
                    changePt = rowEnd - (num_inc - 1);
                    if (row < changePt) {
                        hexColor[col][row] =
                        new Color(255 - (changePt - row) * inc,
                                  255 - (row - rowStart) * inc,
                                  255);
                    } else {
                        hexColor[col][row] =
                        new Color(255,
                                  255 - col * inc,
                                  (rowEnd - row) * inc);
                    }
                    continue;
                }
                if (col == num_inc - 1) {
                    changePt = num_inc;
                    changePt2 = num_inc * 2 - 1;
                    if (row < changePt) {
                        hexColor[col][row] =
                        new Color(row * inc, 255 - row * inc, 255);
                        continue;
                    }
                    if (row < changePt2) {
                        hexColor[col][row] =
                        new Color(255, 0, (changePt2 - row - 1) * inc);
                    } else {
                        hexColor[col][row] =
                        new Color(255, 0, 255 - (rowEnd - row) * inc);
                    }
                    continue;
                }
                if (col > num_inc - 1 && col < num_inc * 2 - 2) {
                    changePt = num_inc * 2 - 1 - col;
                    changePt2 = changePt + (num_inc - 1);
                    changePt3 = rowEnd - (num_inc - 1);
                    if (row < changePt) {
                        hexColor[col][row] =
                        new Color((row - rowStart) * inc,
                                  (changePt - (row + 1)) * inc,
                                  255);
                        continue;
                    }
                    if (row < changePt2) {
                        hexColor[col][row] =
                        new Color(255 - ((col - num_inc) + 1) * inc,
                                  0,
                                  (changePt2 - row - 1) * inc);
                        continue;
                    }
                    if (row < changePt3) {
                        int temp = (((col - num_inc) + 1) -
                                    (changePt3 - row)) *
                                   inc;
                        if (temp < 0) {
                            hexColor[col][row] = new Color(255, 255, 255);
                        } else {
                            hexColor[col][row] =
                            new Color(255 - (changePt3 - row) * inc, temp, 0);
                        }
                    } else {
                        hexColor[col][row] =
                        new Color(255,
                                  ((col - num_inc) + 1) * inc,
                                  255 - (rowEnd - row) * inc);
                    }
                    continue;
                }
                if (col == num_inc * 2 - 2) {
                    changePt = (num_inc - 1) + rowStart;
                    changePt2 = (changePt + num_inc) - 1;
                    if (row < changePt) {
                        hexColor[col][row] =
                        new Color(0, 0, (changePt - row) * inc);
                        continue;
                    }
                    if (row < changePt2) {
                        hexColor[col][row] =
                        new Color(255 - (changePt2 - row) * inc,
                                  255 - (changePt2 - row) * inc,
                                  0);
                    } else {
                        hexColor[col][row] =
                        new Color(255, 255, (row - changePt2) * inc);
                    }
                    continue;
                }
                if (col > num_inc * 2 - 2 && col < num_inc * 3 - 3) {
                    changePt = col - (num_inc * 2 - 2);
                    changePt2 = changePt + (num_inc - 1);
                    changePt3 = rowEnd - num_inc;
                    if (row <= changePt) {
                        hexColor[col][row] =
                        new Color((changePt - (row - rowStart)) * inc,
                                  row * inc,
                                  255);
                        continue;
                    }
                    if (row < changePt2) {
                        hexColor[col][row] =
                        new Color(0,
                                  (col - (num_inc * 2 - 1)) * inc,
                                  (row - changePt - 1) * inc);
                        continue;
                    }
                    if (row < changePt3) {
                        hexColor[col][row] =
                        new Color(((row - changePt2) + 1) * inc,
                                  (changePt3 - row) * inc,
                                  0);
                        continue;
                    }
                    int temp = 255 - (rowEnd - row) * inc;
                    if (temp < 0) {
                        hexColor[col][row] = new Color(255, 255, 255);
                    } else {
                        hexColor[col][row] =
                        new Color(255 - (col - (num_inc * 2 - 2)) * inc,
                                  255,
                                  255 - (rowEnd - row) * inc);
                    }
                    continue;
                }
                if (col == num_inc * 3 - 3) {
                    changePt = num_inc;
                    changePt2 = num_inc * 2 - 1;
                    if (row < changePt) {
                        hexColor[col][row] =
                        new Color(255 - row * inc, row * inc, 255);
                        continue;
                    }
                    if (row < changePt2) {
                        hexColor[col][row] =
                        new Color(0, 255, (changePt2 - row - 1) * inc);
                    } else {
                        hexColor[col][row] =
                        new Color(0, 255, 255 - (rowEnd - row) * inc);
                    }
                    continue;
                }
                if (col < 21) {
                    changePt = rowEnd - (num_inc - 1);
                    if (row < changePt) {
                        hexColor[col][row] =
                        new Color(255 - (row - rowStart) * inc,
                                  255 - (changePt - row) * inc,
                                  255);
                    } else {
                        hexColor[col][row] =
                        new Color(255 - (num_inc * 4 - 4 - col) * inc,
                                  255,
                                  (rowEnd - row) * inc);
                    }
                }
            }
        }

        hexColor[21][2] = new Color(204, 204, 204);
        hexColor[21][1] = new Color(153, 153, 153);
        hexColor[20][0] = new Color(102, 102, 102);
        hexColor[19][0] = new Color(51, 51, 51);
        hexColor[7][1] = new Color(0, 153, 255);
        hexColor[7][2] = new Color(51, 102, 255);
        hexColor[7][3] = new Color(102, 51, 255);
        hexColor[7][4] = new Color(153, 0, 255);
        hexColor[7][5] = new Color(153, 0, 204);
        hexColor[7][6] = new Color(153, 0, 153);
        hexColor[7][7] = new Color(153, 0, 102);
        hexColor[7][8] = new Color(153, 0, 51);
        hexColor[8][1] = new Color(0, 102, 255);
        hexColor[8][2] = new Color(51, 51, 255);
        hexColor[8][3] = new Color(102, 0, 255);
        hexColor[8][4] = new Color(102, 0, 204);
        hexColor[8][5] = new Color(102, 0, 153);
        hexColor[8][6] = new Color(102, 0, 102);
        hexColor[8][7] = new Color(102, 0, 51);
        hexColor[8][8] = new Color(102, 0, 0);
        hexColor[9][2] = new Color(0, 51, 255);
        hexColor[9][3] = new Color(51, 0, 255);
        hexColor[9][4] = new Color(51, 0, 204);
        hexColor[9][5] = new Color(51, 0, 153);
        hexColor[9][6] = new Color(51, 0, 102);
        hexColor[9][7] = new Color(51, 0, 51);
        hexColor[9][8] = new Color(51, 0, 0);
        hexColor[11][2] = new Color(51, 0, 255);
        hexColor[11][3] = new Color(0, 51, 255);
        hexColor[11][4] = new Color(0, 51, 204);
        hexColor[11][5] = new Color(0, 51, 153);
        hexColor[11][6] = new Color(0, 51, 102);
        hexColor[11][7] = new Color(0, 51, 51);
        hexColor[11][8] = new Color(0, 51, 0);
        hexColor[11][9] = new Color(51, 102, 0);
        hexColor[11][10] = new Color(102, 153, 0);
        hexColor[11][11] = new Color(153, 204, 0);
        hexColor[12][1] = new Color(102, 0, 255);
        hexColor[12][2] = new Color(51, 51, 255);
        hexColor[12][3] = new Color(0, 102, 255);
        hexColor[12][4] = new Color(0, 102, 204);
        hexColor[12][5] = new Color(0, 102, 153);
        hexColor[12][6] = new Color(0, 102, 102);
        hexColor[12][7] = new Color(0, 102, 51);
        hexColor[12][8] = new Color(0, 102, 0);
        hexColor[12][9] = new Color(51, 153, 0);
        hexColor[12][10] = new Color(102, 204, 0);
        hexColor[12][11] = new Color(153, 255, 0);
        hexColor[13][1] = new Color(153, 0, 255);
        hexColor[13][2] = new Color(102, 51, 255);
        hexColor[13][3] = new Color(51, 102, 255);
        hexColor[13][4] = new Color(0, 153, 255);
        hexColor[13][5] = new Color(0, 153, 204);
        hexColor[13][6] = new Color(0, 153, 153);
        hexColor[13][7] = new Color(0, 153, 102);
        hexColor[13][8] = new Color(0, 153, 51);
        hexColor[13][9] = new Color(0, 153, 0);
        hexColor[13][10] = new Color(51, 204, 0);
        hexColor[13][11] = new Color(102, 255, 0);
        hexColor[13][12] = new Color(102, 255, 51);
        hexColor[14][0] = new Color(204, 0, 255);
        hexColor[14][1] = new Color(153, 51, 255);
        hexColor[14][2] = new Color(102, 102, 255);
        hexColor[14][3] = new Color(51, 153, 255);
        hexColor[14][4] = new Color(0, 204, 255);
        hexColor[14][5] = new Color(0, 204, 204);
        hexColor[14][6] = new Color(0, 204, 153);
        hexColor[14][7] = new Color(0, 204, 102);
        hexColor[14][8] = new Color(0, 204, 51);
        hexColor[14][9] = new Color(0, 204, 0);
        hexColor[14][10] = new Color(51, 255, 0);
        hexColor[14][11] = new Color(51, 255, 51);

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwMouseListener ---------------------

    public void mouseClicked(LwMouseEvent e) {

        mouseMove(e);

    }

    public void mouseEntered(LwMouseEvent e) {


    }

    public void mouseExited(LwMouseEvent e) {

    }

    public void mousePressed(LwMouseEvent e) {

    }

    public void mouseReleased(LwMouseEvent e) {

    }

// --------------------- Interface LwMouseMotionListener ---------------------


    public void startDragged(LwMouseEvent e) {
        // TODO Implement this method

    }

    public void endDragged(LwMouseEvent e) {
        // TODO Implement this method

    }

    public void mouseDragged(LwMouseEvent e) {
        mouseClicked(e);
    }

    public void mouseMoved(LwMouseEvent e) {
        // TODO Implement this method

    }

// -------------------------- OTHER METHODS --------------------------

    public void addActionListener(LwActionListener listener) {

        if (listeners.indexOf(listener) < 0) {
            listeners.addElement(listener);
        }

    }

    public void mouseMove(LwMouseEvent e) {

        int _x = e.getX();
        int _y = e.getY();
        if (e.getSource() != this) {
            return;
        }

        for (int col = 0; col < polygons.length; col++) {
            Polygon[] polygons = ColorChooser.polygons[col];
            for (int row = 0; row < polygons.length; row++) {
                if (hexColor[col][row] != null) {
                    Polygon polygon = polygons[row];
                    if (polygon.contains(_x, _y)) {
                        currentCol = col;
                        currentRow = row;
                        notify(hexColor[currentCol][currentRow]);
                        repaint();
                        return;
                    }
                }
            }
        }

        currentCol = currentRow = -1;

    }

    private void notify(Color color) {

        Enumeration enumeration = listeners.elements();
        while (enumeration.hasMoreElements()) {
            LwActionListener listener = (LwActionListener) enumeration.nextElement();
            listener.actionPerformed(this, color);
        }

    }

    public void paint(Graphics g) {

        for (col = 0; col < numCols; col++) {
            for (row = 0; row < numRows; row++) {
                if (hexColor[col][row] != null) {
                    Polygon polygon = polygons[col][row];
                    g.setColor(hexColor[col][row]);
                    g.fillPolygon(polygon);
                }
            }
        }
        if (currentCol > -1 &&
            hexColor[currentCol][currentRow] != null) {
            g.setColor(hexColor[currentCol][currentRow]);
            Polygon selectedPolygon = generateSelectedPolygon(polygons[currentCol][currentRow],
                                                              bigHeightStretch,
                                                              bigWidthStretch);
            g.fillPolygon(selectedPolygon);
            g.setColor(Color.black);
            g.drawPolygon(selectedPolygon);
        }

    }

    private Polygon generateSelectedPolygon(Polygon _polygon,
                                            int _width,
                                            int _height) {

        Polygon polygon = new Polygon();
        polygon.addPoint(_polygon.xpoints[0] -
                         _width,
                         _polygon.ypoints[0]);
        polygon.addPoint(_polygon.xpoints[1] -
                         _width / 2,
                         _polygon.ypoints[1] -
                         _height);
        polygon.addPoint(_polygon.xpoints[2] +
                         _width / 2,
                         _polygon.ypoints[2] -
                         _height);
        polygon.addPoint(_polygon.xpoints[3] +
                         _width,
                         _polygon.ypoints[3]);
        polygon.addPoint(_polygon.xpoints[4] +
                         _width / 2,
                         _polygon.ypoints[4] +
                         _height);
        polygon.addPoint(_polygon.xpoints[5] -
                         _width / 2,
                         _polygon.ypoints[5] +
                         _height);
        polygon.addPoint(_polygon.xpoints[6] -
                         _width,
                         _polygon.ypoints[6]);
        return polygon;

    }

    public void removeActionListener(LwActionListener listener) {

        listeners.removeElement(listener);

    }

    public void removeSelection() {

        currentCol = currentRow = -1;

    }
}
