// Actual component for the home tab. Displays a welcome message as well as a
// brief summary of weekly mileage and the "Add New Entry" button.

package com.straub.runlog.home;

import com.straub.runlog.data.RunData;
import com.straub.runlog.log.LogTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static java.awt.Font.PLAIN;

public class HomeTab extends JComponent {
    private RunData runData = new RunData();

    public HomeTab(HashMap<String, String> userInfo, LogTab logTab) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 5, 5, 5);
        Font welcomeFont = new Font("Calibri", PLAIN, 36);
        Font summaryFont = new Font("Calibri", PLAIN, 24);

        /****** WELCOME MESSAGE ******/

        final JLabel welcomeMessage = new JLabel("Hi, "
                + userInfo.get("USER_NAME") + "!");
        welcomeMessage.setFont(welcomeFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcomeMessage, gbc);

        /****** WEEKLY SUMMARY ******/

        final JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridBagLayout());
        summaryPanel.setOpaque(false);

        final JLabel youHaveRanLabel = new JLabel("You have ran");
        youHaveRanLabel.setFont(summaryFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        summaryPanel.add(youHaveRanLabel, gbc);

        final JLabel weeklyDistanceLabel = new JLabel();
        weeklyDistanceLabel.setFont(summaryFont);
        weeklyDistanceLabel.setText(String.format("%.2f", runData.getWeeklyDistance()) + " " + userInfo.get("PREFERREDUNITS"));
        gbc.gridx = 1;
        summaryPanel.add(weeklyDistanceLabel, gbc);

        final JLabel thisWeekLabel = new JLabel("this week");
        thisWeekLabel.setFont(summaryFont);
        gbc.gridx = 2;
        summaryPanel.add(thisWeekLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(summaryPanel, gbc);

        /****** ADD NEW ENTRY BUTTON ******/

        final JButton addEntryButton = new JButton("Add New Entry");
        addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pass label as argument so that after new entry is added, we
                // can check for the new file and update the home screen
                final NewEntryWindow newEntryWindow =
                        new NewEntryWindow(weeklyDistanceLabel, userInfo);
                newEntryWindow.setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(addEntryButton, gbc);
    }
}
