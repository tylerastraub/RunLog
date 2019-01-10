package com.straub.runlog.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.straub.runlog.tools.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class NewEntryWindow extends JFrame {
    public NewEntryWindow() {
        super("Add New Entry");

        Calendar calendar = GregorianCalendar.getInstance();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

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

        String[] distanceUnits = {"Miles", "Kilometers", "Feet", "Meters"};

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        /****** DATE ******/
        final JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,5,5,5);
        add(dateLabel, gbc);

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
        gbc.gridwidth = 5;
        gbc.insets = new Insets(5,0,5,5);

        add(datePicker, gbc);

        /****** TIME ******/
        final JLabel timeLabel = new JLabel("Time:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,5,5,5);
        add(timeLabel, gbc);

        final JComboBox hourBox = new JComboBox(hours);
        hourBox.setEditable(true);
        gbc.insets = new Insets(0,5,0,0);
        gbc.gridx = 1;
        if(calendar.get(Calendar.HOUR) == 0) {
            hourBox.setSelectedItem(12);
        } else {
            hourBox.setSelectedItem(calendar.get(Calendar.HOUR));
        }
        add(hourBox, gbc);

        final JLabel colonLabel = new JLabel(":");
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        add(colonLabel, gbc);

        final JComboBox minuteBox = new JComboBox(minutes);
        minuteBox.setEditable(true);
        gbc.insets = new Insets(0,5,0,0);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        if(calendar.get(Calendar.MINUTE) < 10) {
            String minute = "0";
            minute += Integer.toString(calendar.get(Calendar.MINUTE));
            minuteBox.setSelectedItem(minute);
        } else {
            minuteBox.setSelectedItem(calendar.get(Calendar.MINUTE));
        }
        add(minuteBox, gbc);

        final JComboBox pmOrAmBox = new JComboBox(pmOrAm);
        gbc.insets = new Insets(0,0,0,5);
        gbc.gridx = 4;
        if(calendar.get(Calendar.AM_PM) == Calendar.AM) {
            pmOrAmBox.setSelectedItem("AM");
        } else {
            pmOrAmBox.setSelectedItem("PM");
        }
        add(pmOrAmBox, gbc);

        /****** RUN INFO ******/
        final JLabel distanceLabel = new JLabel("Distance:");
        gbc.insets = new Insets(0,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(distanceLabel, gbc);

        final JTextField distanceTextField = new JTextField();
        gbc.insets = new Insets(5,5,5,0);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(distanceTextField, gbc);

        final JComboBox distanceUnitsBox = new JComboBox(distanceUnits);
        distanceUnitsBox.setSelectedItem(distanceUnits[0]);
        gbc.insets = new Insets(5,0,5,0);
        gbc.gridx = 3;
        gbc.gridwidth = 3;
        add(distanceUnitsBox, gbc);

        // TODO: add duration field right here, add submit button, save to file

        final JLabel titleLabel = new JLabel("Title:");
        gbc.insets = new Insets(0,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(titleLabel, gbc);

        final JTextArea titleTextArea = new JTextArea();
        titleTextArea.setLineWrap(true);
        titleTextArea.setWrapStyleWord(true);
        titleTextArea.setRows(2);
        titleTextArea.setColumns(30);
        titleTextArea.setBorder(distanceTextField.getBorder());
        gbc.gridx = 1;
        gbc.gridwidth = 4;
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if(currentHour > 6 && currentHour < 12) {
            titleTextArea.setText("Morning Run");
        } else if(currentHour < 18) {
            titleTextArea.setText("Afternoon Run");
        } else {
            titleTextArea.setText("Night Run");
        }
        add(titleTextArea, gbc);

        final JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(descriptionLabel, gbc);

        final JTextArea descriptionTextArea = new JTextArea();
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setRows(4);
        descriptionTextArea.setColumns(30);
        descriptionTextArea.setBorder(distanceTextField.getBorder());
        gbc.gridx = 1;
        gbc.gridwidth = 4;
        add(descriptionTextArea, gbc);

        pack();
        setLocationRelativeTo(null);
    }
}
