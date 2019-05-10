package com.cwidden;

import com.cwidden.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ConwaysGameController {
    private static final ConwaysGameController INSTANCE = new ConwaysGameController();

    private Timer timer;
    private MainFrame mainFrame;
    private StartDialog startDialog;
    private DrawingPanel drawingPanel;

    private Dimension maxScreenSize;

    private int mouseX;
    private int mouseY;

    private ArrayList<Point> pointArrayList;

    private int xDimension;
    private int yDimension;

    private Dimension maxDimension;

    //private final int X_DIMENSION = 300;
    //private final int Y_DIMENSION = 180;

    private final int EXTRA_WIDTH = 60;
    private final int EXTRA_HEIGHT = 90;

    private  boolean conwaysGame = true;

    private boolean[][] cellArray;
    private boolean[][] newCellArray;
    private int[][] cellNeighborsArray;

    private boolean isStarted;

    public static ConwaysGameController getInstance() {
        return INSTANCE;
    }

    private ConwaysGameController() {
        pointArrayList = new ArrayList<Point>();
        isStarted = false;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        maxDimension = new Dimension(screenSize.width - EXTRA_WIDTH, screenSize.height - EXTRA_HEIGHT);

    }

    public void buildGui(){

        startDialog = new StartDialog();

        startDialog.pack();
        startDialog.setVisible(true);


    }

    public boolean buildFrame(String xString, String yString, boolean conwaysGameOn) {
        try {
            xDimension = 5 * Integer.parseInt(xString);
            yDimension = 5 * Integer.parseInt(yString);
            if (xDimension > maxDimension.width || yDimension > maxDimension.height || xDimension < 5 || yDimension < 5) {
                return false;
            } else {
                conwaysGame = conwaysGameOn;
                cellArray = new boolean[xDimension][yDimension];
                newCellArray = new boolean[xDimension][yDimension];
                cellNeighborsArray = new int[xDimension][yDimension];
                buildFrame(xDimension, yDimension);
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void buildFrame(int xDimension, int yDimension) {
        mainFrame = new MainFrame();
        //mainFrame.setLayout(new FlowLayout());
        drawingPanel = new DrawingPanel(xDimension, yDimension);
        drawingPanel.setLocation(0,0);
        drawingPanel.setSize(xDimension, yDimension);
        drawingPanel.setPreferredSize(new Dimension(xDimension, yDimension));
        drawingPanel.setMaximumSize(new Dimension(xDimension, yDimension));
        mainFrame.build(drawingPanel);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();

        mainFrame.setMinimumSize(mainFrame.getSize());

        mainFrame.setVisible(true);

        startMouseLocationTimer();
    }

    public Dimension getMaxDimension() {
        return maxDimension;
    }

    public void startTimer() {
        if (!isStarted) {
            timer = new Timer();
            TimerTask task = new Helper();

            timer.scheduleAtFixedRate(task, 0, 500);

            isStarted = true;
        }
    }

    public void step() {
        if (!isStarted) {
            Helper helper = new Helper();
            helper.run();
        }
    }

    public void startMouseLocationTimer() {
        Timer mouseTimer = new Timer();
        TimerTask task = new MouseHelper();

        mouseTimer.scheduleAtFixedRate(task, 0, 33);
    }

    public void stopTimer() {
        timer.cancel();
        isStarted = false;
    }

    public boolean addPoint() {
        Point p = getRelativeLocation();
        return addPoint(p.x, p.y);
    }

    public boolean addPoint(int x, int y){
        Point p = new Point(x, y);
        if (pointArrayList.contains(p)) {
            return false;
        } else {
            pointArrayList.add(p);
            return true;
        }
    }

    public void changeCell() {
        Point mouseLocation = getRelativeLocation();

        int xPoint = mouseLocation.x / 5;
        int yPoint = mouseLocation.y / 5;

        if (xPoint >= 0 && yPoint >= 0) {
            changeCell(xPoint, yPoint);
        }
    }

    public void changeCell(int x, int y){
        if (cellArray[x][y]) {
            cellArray[x][y] = false;
        } else {
            cellArray[x][y] = true;
        }
    }

    public void clearCells() {
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                cellArray[x][y] = false;
            }
        }

        drawingPanel.repaint();
    }

    private void updateNeighbors() {

        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {

                int neighborCount = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int w = x + i;
                        int h = y + j;
                        if (w < 0 || h < 0 || w >= xDimension || h >= yDimension) {
                            continue;
                        }
                        if (w == x && h == y && conwaysGame) {
                            continue;
                        }
                        if (cellArray[w][h]) {
                            neighborCount++;
                        }
                    }
                }

                if (!conwaysGame) {
                    neighborCount = neighborCount - 1;
                }

                switch (neighborCount) {
                    case 0:
                        newCellArray[x][y] = false;
                        break;
                    case 1:
                        newCellArray[x][y] = false;
                        break;
                    case 2:
                        if (cellArray[x][y]) {
                            newCellArray[x][y] = true;
                        } else if (conwaysGame){
                            newCellArray[x][y] = false;
                        }
                        if (conwaysGame) {
                            break;
                        }
                    case 3:
                        newCellArray[x][y] = true;
                        break;
                    default:
                        newCellArray[x][y] = false;
                }
            }
        }

        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                cellArray[x][y] = newCellArray[x][y];
            }
        }

    }

    private Point getRelativeLocation() {
        Point windowLocation = mainFrame.getLocationOnScreen();
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

        mouseX = mouseLocation.x - windowLocation.x - 7;
        mouseY = mouseLocation.y - windowLocation.y - 31;

        Point relativeLocation = new Point(mouseX, mouseY);
        return relativeLocation;
    }

    public void drawCells(Graphics g){
        drawingPanel.drawCells(g, cellArray);
    }

    private class Helper extends TimerTask {

        @Override
        public void run() {

            updateNeighbors();
            drawingPanel.repaint();

        }

    }

    private class MouseHelper extends TimerTask {

        @Override
        public void run() {

            Point relativeLocation = getRelativeLocation();
            mainFrame.updateCoordinates(relativeLocation.x / 5, relativeLocation.y / 5);

        }

    }
}
