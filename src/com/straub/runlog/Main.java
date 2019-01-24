package com.straub.runlog;

import com.straub.runlog.GUI.FirstTimeWindow;
import com.straub.runlog.GUI.HomeWindow;
import com.straub.runlog.data.FileIO;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //final String text = args[0]; // use this for command line args
        String title = "RunLog";
        String version = "0.0.2";

        FileIO fileReader = new FileIO();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final String data = fileReader.readFile("res/data.txt");
                if(data == null) {
                    final FirstTimeWindow firstTimeWindow = new FirstTimeWindow(title, version);
                    firstTimeWindow.setVisible(true);
                } else {
                    final HomeWindow homeWindow = new HomeWindow(title, version);
                    homeWindow.setVisible(true);
                }
            }
        });
    }
}