// Tab responsible for displaying the running log in table form. Most of the
// heavy lifting is done in LogTable.java

package com.straub.runlog.log;

import javax.swing.*;
import java.awt.*;

public class LogTab extends JComponent {
    private LogTable logTable = new LogTable();

    public LogTab() {
        JScrollPane scrollPane = new JScrollPane(logTable);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        add(scrollPane, gbc);
    }

    // i don't know how to let other classes dynamically update LogTable so this
    // will have to do
    public void update() {
        logTable.updateTable();
    }
}
