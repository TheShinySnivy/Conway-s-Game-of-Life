package com.cwidden.gui;

import com.cwidden.ConwaysGameController;
import sun.awt.geom.AreaOp;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements MouseListener {

    private int width;
    private int height;

    public DrawingPanel() {
        this(new BorderLayout(), 100, 100);
    }

    private ConwaysGameController conwaysGameController;

    public DrawingPanel(int width, int height) {
        this(new BorderLayout(), width, height);
    }

    public DrawingPanel(BorderLayout borderLayout, int width, int height) {
        super(borderLayout);

        conwaysGameController = ConwaysGameController.getInstance();

        this.width = width;
        this.height = height;

        //this.setSize(width, height);
        this.setMaximumSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        addMouseListener(this);

    }

    public void displayPoint(int x, int y) {
        Graphics g = this.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(x, y,1,1);

        System.out.println("Set color at " + x + ", " + y);
    }

    public void drawCells(Graphics g, boolean[][] cellArray) {
        //Graphics g = this.getGraphics();

        //paintComponent(g);

        //g.setColor(Color.BLACK);

        for (int x = 0; x < cellArray.length; x++) {
            for (int y = 0; y < cellArray[x].length; y++) {
                boolean pointOn = cellArray[x][y];
                if (pointOn) {
                    g.setColor(Color.WHITE);
                    g.fillRect(5* x, 5* y, 5, 5);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(5*x, 5*y, 5, 5);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        conwaysGameController.drawCells(g);
    }

    public void mouseClicked(MouseEvent e) {
        conwaysGameController.addPoint();
        conwaysGameController.changeCell();
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered drawing panel");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited drawing panel");
    }
}
