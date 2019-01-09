package com.straub.runlog.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import com.straub.runlog.tools.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class NewEntryWindow extends JFrame {
    public NewEntryWindow() {
        super("Add New Entry");

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        final JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        add(dateLabel, gbc);

        // date picker stuff
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel,
                new DateLabelFormatter());

        datePicker.setTextEditable(true);
        model.setSelected(true);

        gbc.gridx = 1;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(5,0,5,5);

        add(datePicker, gbc);

        final JLabel timeLabel = new JLabel("Time:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,5,5,5);
        add(timeLabel, gbc);

        String[] hours = new String[12];
        for(int i = 0; i < 12; i++) {
            hours[i] = Integer.toString(i + 1);
        }

        String[] minutes = new String[12];
        for(int i = 0; i < 59; i += 5) {
            minutes[i / 5] = Integer.toString(i);
        }
        minutes[0] = "00";
        minutes[1] = "05";
        String[] pmOrAm = {"AM", "PM"};

        final JComboBox hourBox = new JComboBox(hours);
        hourBox.setEditable(true);
        gbc.insets = new Insets(0,5,5,0);
        gbc.gridx = 1;
        add(hourBox, gbc);

        final JLabel colonLabel = new JLabel(":");
        gbc.insets = new Insets(0,0,5,0);
        gbc.gridx = 2;
        add(colonLabel, gbc);

        final JComboBox minuteBox = new JComboBox(minutes);
        minuteBox.setEditable(true);
        gbc.gridx = 3;
        add(minuteBox, gbc);

        final JComboBox pmOrAmBox = new JComboBox(pmOrAm);
        gbc.insets = new Insets(0,0,5,5);
        gbc.gridx = 4;
        add(pmOrAmBox, gbc);

        pack();
        setLocationRelativeTo(null);
    }
}
