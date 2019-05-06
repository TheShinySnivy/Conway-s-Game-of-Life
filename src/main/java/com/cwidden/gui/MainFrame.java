package com.cwidden.gui;

import com.cwidden.ConwaysGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    private JLabel coordinateLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton clearButton;

    private ConwaysGameController conwaysGameController;

    public MainFrame() {
        super("Conway's Game of Life");

        conwaysGameController = ConwaysGameController.getInstance();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e){
            System.out.println("Oops");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void build(DrawingPanel drawingPanel) {
        //this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));



        JPanel informationPane = new JPanel();
        informationPane.setLayout(new BoxLayout(informationPane, BoxLayout.LINE_AXIS));

        coordinateLabel = new JLabel("X: Y: ");

        informationPane.add(coordinateLabel);


        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));

        startButton = new JButton("Start");
        startButton.addActionListener(this);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);

        buttonPane.add(startButton);
        buttonPane.add(stopButton);
        buttonPane.add(clearButton);


        this.getContentPane().add(drawingPanel, BorderLayout.CENTER);
        this.getContentPane().add(informationPane, BorderLayout.SOUTH);
        this.getContentPane().add(buttonPane, BorderLayout.EAST);
    }

    public void updateCoordinates(int x, int y){
        coordinateLabel.setText("X: " + x + "  Y: " + y);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(startButton)) {
            conwaysGameController.startTimer();
        }
        if (e.getSource().equals(stopButton)) {
            conwaysGameController.stopTimer();
        }
        if (e.getSource().equals(clearButton)) {
            conwaysGameController.clearCells();
        }
    }
}
