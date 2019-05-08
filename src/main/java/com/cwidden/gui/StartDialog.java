package com.cwidden.gui;


import com.cwidden.ConwaysGameController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartDialog extends JDialog implements ActionListener {

    private ConwaysGameController conwaysGameController;

    private JTextField textFieldWidth;
    private JTextField textFieldHeight;
    private JButton buildButton;
    private JLabel errorLabel;

    public StartDialog() {
        super();

        conwaysGameController = ConwaysGameController.getInstance();

        this.setTitle("Conway's Game of Life");

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



        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        textFieldWidth = new JTextField("Width", 8);
        textFieldHeight = new JTextField("Height", 8);

        JPanel widthHeightPanel = new JPanel();
        widthHeightPanel.setLayout(new BoxLayout(widthHeightPanel, BoxLayout.X_AXIS));
        widthHeightPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        widthHeightPanel.add(textFieldHeight);
        widthHeightPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        widthHeightPanel.add(textFieldWidth);

        buildButton = new JButton("Build");
        buildButton.addActionListener(this);

        errorLabel = new JLabel("Invalid values.");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);


        contentPane.add(widthHeightPanel);
        contentPane.add(buildButton);
        contentPane.add(errorLabel);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buildButton)) {
            if (conwaysGameController.buildFrame(textFieldWidth.getText(), textFieldHeight.getText())) {
                this.setVisible(false);
            } else {
                errorLabel.setText("Text");
                errorLabel.setVisible(true);
            }
        }
    }
}
