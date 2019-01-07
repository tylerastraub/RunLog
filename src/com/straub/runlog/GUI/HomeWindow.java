package com.straub.runlog.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HomeWindow extends JFrame {
    public HomeWindow(String title, String version){
        super(title + " " + version);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        pack();
        // this needs to be at end so that it can center after components are
        // added
        setLocationRelativeTo(null);
    }
}
