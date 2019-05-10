package com.cwidden.gui;


import com.cwidden.ConwaysGameController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;

public class StartDialog extends JDialog implements ActionListener {

    private ConwaysGameController conwaysGameController;

    private JTextField textFieldWidth;
    private JTextField textFieldHeight;
    private JButton buildButton;
    private JLabel errorLabel;
    private JCheckBox conwaysGameCheckBox;

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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        screenSize.height -= screenInsets.top + screenInsets.bottom;
        screenSize.width -= screenInsets.left + screenInsets.right;

        int maxX = (int) (conwaysGameController.getMaxDimension().width / 5);
        int maxY = (int) (conwaysGameController.getMaxDimension().height / 5);
        textFieldWidth = new JTextField(Integer.toString(maxX), 8);
        textFieldHeight = new JTextField(Integer.toString(maxY), 8);

        JPanel widthPanel = new JPanel();
        widthPanel.setLayout(new BoxLayout(widthPanel, BoxLayout.X_AXIS));
        widthPanel.setBorder(new EmptyBorder(5, 5, 0, 5));

        widthPanel.add(new JLabel("Width: "));
        widthPanel.add(textFieldWidth);


        JPanel heightPanel = new JPanel();
        heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.X_AXIS));
        heightPanel.setBorder(new EmptyBorder(5, 5, 0, 5));

        heightPanel.add(new JLabel("Height: "));
        heightPanel.add(textFieldHeight);

        buildButton = new JButton("Build");
        buildButton.addActionListener(this);

        errorLabel = new JLabel("Invalid values.");
        errorLabel.setVisible(false);
        errorLabel.setForeground(Color.RED);

        conwaysGameCheckBox = new JCheckBox("Conway's Game of Life");
        conwaysGameCheckBox.setSelected(true);


        contentPane.add(widthPanel);
        contentPane.add(heightPanel);
        contentPane.add(buildButton);
        contentPane.add(errorLabel);
        contentPane.add(conwaysGameCheckBox);



    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buildButton)) {
            if (conwaysGameController.buildFrame(textFieldWidth.getText(), textFieldHeight.getText(), conwaysGameCheckBox.isSelected())) {
                this.setVisible(false);
            } else {
                //errorLabel.setText("Invalid values.");
                errorLabel.setVisible(true);
                this.pack();
            }
        }
    }
}
