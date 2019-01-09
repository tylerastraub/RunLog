package com.straub.runlog.GUI;

import com.straub.runlog.data.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class HomeWindow extends JFrame {
    public HomeWindow(String title, String version){
        super(title + " " + version);

        HashMap<String, String> userInfo = new HashMap<String, String>();
        readUserInfo(userInfo);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true); // this is the default value anyways
        setLayout(new GridLayout(1, 1));
        setPreferredSize(new Dimension(800, 600));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        HomeTab homeTab = new HomeTab(userInfo);

        final JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.add("Home", homeTab);
        add(tabs);

        pack();
        // this needs to be at end so that it can center after components are
        // added
        setLocationRelativeTo(null);
    }

    private void readUserInfo(HashMap<String, String> map) {
        FileIO fileReader = new FileIO();
        HashMap<String, String> temp = fileReader.createHashMapFromFile(
                "res/data.txt", ":");
        for (HashMap.Entry<String, String> entry : temp.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            map.put(key, value);
        }
    }
}

// TODO: create general home environment. should figure out tab system for easy
// switching between environments. general idea is that there will be a calendar,
// a weekly logger, a stats page, and some other stuff. home page should have
// all the tabs, a welcome message, and a brief weekly stats recap
