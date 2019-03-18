// The main window that holds the JTabbedPane and displays essentially all
// content.

package com.straub.runlog.home;

import com.straub.runlog.data.FileIO;
import com.straub.runlog.log.LogTab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
        setMinimumSize(new Dimension(800, 600));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        LogTab logTab = new LogTab();
        HomeTab homeTab = new HomeTab(userInfo, logTab);

        final JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.add("Log", logTab);
        tabs.add("Home", homeTab);
        tabs.setSelectedComponent(homeTab);
        add(tabs);

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabs.getSelectedComponent() == logTab) {
                    logTab.update();
                }
            }
        });

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

// TODO: create log tab, calendar tab, and stats tab. add weekly minutes to
// weekly summary on home tab
