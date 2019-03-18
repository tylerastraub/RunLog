// RunLog is a local application that allows users to record their runs and keep
// track of their training over an extended period of time.
//
// Written by Tyler Straub.

package com.straub.runlog;

import com.straub.runlog.data.FileIO;
import com.straub.runlog.home.HomeWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //final String text = args[0]; // use this for command line args
        String title = "RunLog";
        String version = "0.0.5";

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