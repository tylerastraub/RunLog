// JFrame that is launched on first time and prompts the user for their name.
// Eventually, this will be a little more comprehensive and ask for a few more
// settings, but until RunLog is approaching alpha, not much reason to work on
// customization before just basic functionality.

package com.straub.runlog;

import com.straub.runlog.data.FileIO;
import com.straub.runlog.home.HomeWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

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

        final JLabel name_text = new JLabel("Please enter your first name:");
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
        HashMap<String, String> userData = new HashMap<>();
        userData.put("USER_NAME:", user_name);
        userData.put("PREFERREDUNITS:", "miles");

        fileIO.writeToFile("res/data.txt", userData);
    }
}

// TODO: add option to select default distance units (miles or kilometers)