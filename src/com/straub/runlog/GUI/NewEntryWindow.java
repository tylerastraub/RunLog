package com.straub.runlog.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;

import com.straub.runlog.data.EntryParser;
import com.straub.runlog.data.RunData;
import com.straub.runlog.tools.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class NewEntryWindow extends JDialog {
    // numOfEnteredFields starts equal to number of fields with default values
    private int numOfEnteredFields = 1;

    public NewEntryWindow(JLabel howFarLabel, HashMap<String, String> userInfo) {
        //super(frame, "Add New Entry");
        setAlwaysOnTop(true);
        setTitle("Add New Entry");
        setModalityType(ModalityType.APPLICATION_MODAL);

        Calendar calendar = GregorianCalendar.getInstance();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        JPanel[] panels = new JPanel[7];
        createPanels(panels, 7);

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
        String[] distanceUnits = {"miles", "kilometers", "feet", "meters"};
        String[] runTypes = {"Run", "Long Run", "Workout", "Race"};

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });

        /****** DATE ******/

        final JLabel dateLabel = new JLabel("Date:");

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
        panels[0].add(datePicker);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(dateLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[0], gbc);

        /****** TIME ******/

        final JLabel timeLabel = new JLabel("Time:");

        final JComboBox hourBox = new JComboBox(hours);
        hourBox.setEditable(true);
        if(calendar.get(Calendar.HOUR) == 0) {
            hourBox.setSelectedItem(12);
        } else {
            hourBox.setSelectedItem(calendar.get(Calendar.HOUR));
        }

        final JLabel colonLabel = new JLabel(":");

        final JComboBox minuteBox = new JComboBox(minutes);
        minuteBox.setEditable(true);
        if(calendar.get(Calendar.MINUTE) < 10) {
            String minute = "0";
            minute += Integer.toString(calendar.get(Calendar.MINUTE));
            minuteBox.setSelectedItem(minute);
        } else {
            minuteBox.setSelectedItem(calendar.get(Calendar.MINUTE));
        }

        final JComboBox pmOrAmBox = new JComboBox(pmOrAm);
        if(calendar.get(Calendar.AM_PM) == Calendar.AM) {
            pmOrAmBox.setSelectedItem("AM");
        } else {
            pmOrAmBox.setSelectedItem("PM");
        }

        panels[1].add(hourBox);
        panels[1].add(colonLabel);
        panels[1].add(minuteBox);
        panels[1].add(pmOrAmBox);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(timeLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[1], gbc);

        /****** RUN DISTANCE ******/

        final JLabel distanceLabel = new JLabel("Distance:");
        final JTextField distanceTextField = new JTextField();
        distanceTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(((c < '0') || (c > '9')) && (c != '.')
                        && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        distanceTextField.setColumns(6);
        final JComboBox distanceUnitsBox = new JComboBox(distanceUnits);
        distanceUnitsBox.setSelectedItem(distanceUnits[0]);

        panels[2].add(distanceTextField);
        panels[2].add(distanceUnitsBox);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(distanceLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[2], gbc);

        /****** RUN DURATION ******/

        final JLabel durationLabel = new JLabel("Duration:");
        final JTextField hoursTextField = new JTextField();
        hoursTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        hoursTextField.setColumns(3);
        final JLabel hoursLabel = new JLabel("hr");
        final JTextField minutesTextField = new JTextField();
        minutesTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        minutesTextField.setColumns(3);
        final JLabel minutesLabel = new JLabel("min");
        final JTextField secTextField = new JTextField();
        secTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        secTextField.setColumns(3);
        final JLabel secLabel = new JLabel("sec");

        panels[3].add(hoursTextField);
        panels[3].add(hoursLabel);
        panels[3].add(minutesTextField);
        panels[3].add(minutesLabel);
        panels[3].add(secTextField);
        panels[3].add(secLabel);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(durationLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[3], gbc);

        /****** RUN TITLE ******/

        final JLabel titleLabel = new JLabel("Title:");

        final JTextArea titleTextArea = new JTextArea();
        titleTextArea.setLineWrap(true);
        titleTextArea.setWrapStyleWord(true);
        titleTextArea.setRows(2);
        titleTextArea.setColumns(30);
        titleTextArea.setBorder(distanceTextField.getBorder());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if(currentHour > 6 && currentHour < 12) {
            titleTextArea.setText("Morning Run");
        } else if(currentHour < 18) {
            titleTextArea.setText("Afternoon Run");
        } else {
            titleTextArea.setText("Night Run");
        }

        panels[4].add(titleTextArea);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(titleLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[4], gbc);

        /****** RUN DESCRIPTION ******/

        final JLabel descriptionLabel = new JLabel("Description:");

        final JTextArea descriptionTextArea = new JTextArea();
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setRows(4);
        descriptionTextArea.setColumns(30);
        descriptionTextArea.setBorder(distanceTextField.getBorder());

        panels[5].add(descriptionTextArea);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(descriptionLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[5], gbc);

        /****** RUN TYPE ******/

        final JLabel runTypeLabel = new JLabel("Run Type:");

        final JComboBox runTypeComboBox = new JComboBox(runTypes);
        runTypeComboBox.setEditable(false);
        runTypeComboBox.setSelectedItem(runTypes[0]);

        panels[6].add(runTypeComboBox);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(runTypeLabel, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        add(panels[6], gbc);

        /****** SUBMIT BUTTON ******/

        final JButton submitButton = new JButton("Submit");
        submitButton.setEnabled(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this could be much more tidy by putting it in own method, but
                // that would mean redoing how every component is kept track of
                HashMap<String, String> entryData = new HashMap<>();
                entryData.put("DATE:",
                        datePicker.getJFormattedTextField().getText());
                String time = "";
                time += hourBox.getSelectedItem().toString();
                time += "-";
                time += minuteBox.getSelectedItem().toString();
                time += "-";
                time += pmOrAmBox.getSelectedItem().toString();
                entryData.put("TIME:", time);
                entryData.put("DISTANCE:", distanceTextField.getText());
                entryData.put("DISTANCEUNITS:",
                        distanceUnitsBox.getSelectedItem().toString());
                // duration is stored in seconds
                int hrs = Integer.parseInt(hoursTextField.getText()) * 3600;
                int min = Integer.parseInt(minutesTextField.getText()) * 60;
                int sec = Integer.parseInt(secTextField.getText());

                entryData.put("DURATION:", Integer.toString(hrs + min + sec));
                entryData.put("TITLE:", titleTextArea.getText());
                entryData.put("DESCRIPTION:", descriptionTextArea.getText());
                entryData.put("RUNTYPE:",
                        runTypeComboBox.getSelectedItem().toString());

                EntryParser entryParser = new EntryParser(entryData);
                howFarLabel.setText(String.format("%.2f", new RunData().getWeeklyDistance())
                        + " "  + userInfo.get("PREFERREDUNITS"));
                closeWindow();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        // used to check if enough fields are filled
        DocumentListener textAreaListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfFieldEntered(e, submitButton);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfFieldEmpty(e, submitButton);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };

        // used to check that enough fields are filled. also checks to make sure
        // only numbers are entered
        DocumentListener textFieldListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfFieldEntered(e, submitButton);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfFieldEmpty(e, submitButton);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // unused for text fields
            }
        };

        distanceTextField.getDocument().addDocumentListener(textFieldListener);
        hoursTextField.getDocument().addDocumentListener(textFieldListener);
        minutesTextField.getDocument().addDocumentListener(textFieldListener);
        secTextField.getDocument().addDocumentListener(textFieldListener);
        titleTextArea.getDocument().addDocumentListener(textAreaListener);

        pack();
        setLocationRelativeTo(null);
    }

    private void createPanels(JPanel[] panels, int numOfPanels) {
        final FlowLayout fl = new FlowLayout();
        fl.setHgap(2);
        fl.setVgap(2);
        fl.setAlignment(FlowLayout.LEADING);

        for(int i = 0; i < numOfPanels; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(fl);
        }
    }

    private void checkIfFieldEntered(DocumentEvent e, JButton button) {
        int len = e.getDocument().getLength();
        if(len == 1) {
            numOfEnteredFields++;
        }

        if(numOfEnteredFields == 5) {
            button.setEnabled(true);
        }
    }

    private void checkIfFieldEmpty(DocumentEvent e, JButton button) {
        int len = e.getDocument().getLength();
        if(len == 0) {
            numOfEnteredFields--;
        }

        if(numOfEnteredFields < 5) {
            button.setEnabled(false);
        }
    }

    private void closeWindow() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
