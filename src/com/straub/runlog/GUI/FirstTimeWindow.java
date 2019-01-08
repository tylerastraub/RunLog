package com.straub.runlog.GUI;

import com.straub.runlog.data.FileIO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FirstTimeWindow extends JFrame{
    public FirstTimeWindow(String title, String version) {
        super(title + " " + version);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        final JLabel name_text = new JLabel("Please enter your name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 10, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(name_text, gbc);

        final JTextField name_entry = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.ipadx = 200;
        gbc.insets = new Insets(0, 5, 5, 0);
        add(name_entry, gbc);

        final JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_name = name_entry.getText();
                if(user_name.isEmpty()){
                    name_entry.setBorder(new LineBorder(Color.RED, 1));
                    System.out.println("ERROR: name not entered");
                }
                else {
                    createDataFile(user_name);
                    closeWindow();
                    final HomeWindow homeWindow = new HomeWindow(title, version);
                    homeWindow.setVisible(true);
                }
            }
        });

        gbc.gridx = 4;
        gbc.ipadx = 0;
        gbc.insets = new Insets(0, 0, 5, 5);
        add(submit, gbc);

        pack();
        // this needs to be at end so that it can center after components are
        // added
        setLocationRelativeTo(null);
    }

    private void closeWindow(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void createDataFile(String user_name) {
        FileIO fileIO = new FileIO();

        fileIO.writeToFile("res/data.txt", "USER_NAME:"
                + user_name);
    }
}
