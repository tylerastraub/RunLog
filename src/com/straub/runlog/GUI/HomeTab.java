package com.straub.runlog.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static java.awt.Font.PLAIN;

public class HomeTab extends JComponent {
    public HomeTab(HashMap<String, String> userInfo) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 5, 5, 5);
        Font welcomeFont = new Font("Calibri", PLAIN, 36);

        final JLabel welcomeMessage = new JLabel("Hi, "
                + userInfo.get("USER_NAME") + "!");
        welcomeMessage.setFont(welcomeFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcomeMessage, gbc);

        final JButton addEntryButton = new JButton("Add New Entry");
        addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final NewEntryWindow newEntryWindow = new NewEntryWindow();
                newEntryWindow.setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(addEntryButton, gbc);
    }
}
