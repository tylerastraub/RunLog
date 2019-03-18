// Creates a JTable that displays runs by date.

package com.straub.runlog.log;

import com.straub.runlog.data.RunData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class LogTable extends JTable {
    private DefaultTableModel dtm = new DefaultTableModel();
    private HashMap<String, HashMap<String, String>> runFiles = new HashMap<>();

    public LogTable() {
        setFillsViewportHeight(true);
        setEnabled(false);
        setShowVerticalLines(true);

        String[] columnNames = {"Date",
                    "Title",
                    "Distance",
                    "Duration",
                    "Run Type",
                    "Description"};

        dtm.setColumnIdentifiers(columnNames);
        setModel(dtm);

        setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);
        getColumn("Date").setPreferredWidth(90);
        getColumn("Date").setMaxWidth(90);
        getColumn("Run Type").setPreferredWidth(70);
        getColumn("Run Type").setMaxWidth(70);
        getColumn("Duration").setPreferredWidth(90);
        getColumn("Duration").setMaxWidth(100);
        getColumn("Distance").setPreferredWidth(100);
        getColumn("Distance").setMaxWidth(90);
        getColumn("Title").setPreferredWidth(200);
        getColumn("Title").setMaxWidth(200);
    }

    public void updateTable() {
        // clear table then repopulate it
        for(int i = 0; i < dtm.getRowCount(); ++i) {
            dtm.removeRow(i);
        }

        new RunData().loadRunData(runFiles);
        HashMap<String, String>[] sortedTableRows = new HashMap[runFiles.size()];
        sortRowsByDate(runFiles, sortedTableRows);

        for(int i = 0; i < runFiles.size(); ++i) {
            System.out.println(sortedTableRows[i].get("DATE"));
            addRow(sortedTableRows[i]);
        }
    }

    private void addRow(HashMap<String, String> data) {
        String[] runInfo = {data.get("DATE"),
                data.get("TITLE"),
                data.get("DISTANCE") + " " + data.get("DISTANCEUNITS"),
                durationCalculator(data.get("DURATION")),
                data.get("RUNTYPE"),
                data.get("DESCRIPTION")};

        dtm.addRow(runInfo);
    }

    private void sortRowsByDate(HashMap<String, HashMap<String, String>> data,
                                HashMap<String, String>[] rowIndexes) {
        int[][] dates = new int[data.size()][3];

        int loopIndex = 0;
        for(HashMap<String, String> value : runFiles.values()) {
            String date = value.get("DATE");
            rowIndexes[loopIndex] = value;

            // month
            if(date.substring(0, 1).equals("0")) {
                dates[loopIndex][0] = Integer.parseInt(date.substring(1, 2));
            } else {
                dates[loopIndex][0] = Integer.parseInt(date.substring(0, 2));
            }

            // day
            if(date.substring(3, 4).equals("0")) {
                dates[loopIndex][1] = Integer.parseInt(date.substring(4, 5));
            } else {
                dates[loopIndex][1] = Integer.parseInt(date.substring(3, 5));
            }

            // year
            dates[loopIndex][2] = Integer.parseInt(date.substring(6));

            ++loopIndex;
        }

        // sort by year, then month, then day
        for(int i = 0; i < data.size(); ++i) {
            for(int j = i + 1; j < data.size(); ++j) {
                if(dates[j][2] > dates[i][2]) {
                    swapRows(dates, i, j);
                    swapRows(rowIndexes, i, j);
                } else if(dates[j][2] == dates[i][2]) {
                    if(dates[j][0] > dates[i][0]) {
                        swapRows(dates, i, j);
                        swapRows(rowIndexes, i, j);
                    } else if(dates[j][0] == dates[i][0]) {
                        if(dates[j][1] > dates[i][1]) {
                            swapRows(dates, i, j);
                            swapRows(rowIndexes, i, j);
                        }
                    }
                }
            }
        }
    }

    // swap rows for the dates (purely for the sort algorithm)
    private void swapRows(int[][] array, int index1, int index2) {
        int[] temp; // used for swaps of dates

        temp = array[index1].clone();
        array[index1] = array[index2];
        array[index2] = temp.clone();
    }

    // swap rows for the HashMap (actually sort the entries)
    private void swapRows(HashMap<String, String>[] array, int index1,
                          int index2) {

        HashMap<String, String> tempMap; // used for swap of HashMap indexes

        tempMap = array[index1];
        array[index1] = array[index2];
        array[index2] = tempMap;
    }

    private String durationCalculator(String duration) {
        if (duration == null) {
            System.out.println("ERROR: Null value for duration. Possible " +
                    "corrupt file");
            return "null";
        }

        int durationAsInt = Integer.parseInt(duration);
        int hours = durationAsInt / 3600;
        durationAsInt %= 3600;
        int minutes = durationAsInt / 60;
        durationAsInt %= 60;
        int seconds = durationAsInt;

        if(hours > 0) {
            return hours + "h " + minutes + "m " + seconds + "s";
        } else {
            return minutes + "m " + seconds + "s";
        }
    }
}
