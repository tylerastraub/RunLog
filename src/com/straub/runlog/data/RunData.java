package com.straub.runlog.data;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class RunData {
    public void loadRunData(HashMap<String, HashMap<String, String>> hashMap) {
        File folder = new File("runs/");
        File[] allRunFiles = folder.listFiles();
        FileIO fileIO = new FileIO();

        try {
            for(int i = 0; i < allRunFiles.length; i++) {
                if(allRunFiles[i].isFile()) {
                    hashMap.put("runs/" + allRunFiles[i].getName() + ".txt",
                            fileIO.createHashMapFromFile(allRunFiles[i].getPath(),
                                    ":"));
                    System.out.println(allRunFiles[i].getName());
                }
            }

        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public double getWeeklyDistance() {
        HashMap<String, HashMap<String, String>> runFiles = new HashMap<>();
        int weeklyTime = 0;
        double weeklyDistance = 0.0;

        loadRunData(runFiles);

        for(HashMap<String, String> value : runFiles.values()) {
            if(value.get("DATE") != null) {
                if(isWithinLastWeek(value.get("DATE"))) {
                    String distanceUnit = value.get("DISTANCEUNITS");
                    double distance = Double.parseDouble(value.get("DISTANCE"));
                    double convertedDistance = convertDistance(distance,
                            distanceUnit);

                    weeklyDistance += convertedDistance;
                    weeklyTime += Integer.parseInt(value.get("DURATION"));
                }
            }
        }

        return weeklyDistance;
    }

    private double convertDistance(double distance, String distanceUnit) {
        FileIO fileReader = new FileIO();
        String preferredUnit = fileReader.createHashMapFromFile("res/data.txt", ":").get("PREFERREDUNITS");

        if(preferredUnit.equals("miles")) {
            switch(distanceUnit) {
                case "kilometers":
                    return distance / 1.60934;
                case "meters":
                    return distance / 1000.0 / 1.60934;
                case "feet":
                    return distance / 5280.0;
                default:
                    return distance;
            }
        } else {
            // preferred unit is Kilometers
            switch(distanceUnit) {
                case "miles":
                    return distance * 1.60934;
                case "meters":
                    return distance / 1000.0;
                case "feet":
                    return distance / 5280.0 * 1.60934;
                default:
                    return distance;
            }
        }
    }

    private boolean isWithinLastWeek(String date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentYear = calendar.get(Calendar.YEAR);

        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));
        // month - 1 matches up with enum
        calendar.set(year, month - 1, day);

        if(year == currentYear) {
            return currentWeek == calendar.get(Calendar.WEEK_OF_YEAR);
        } else {
            return currentDay - day + 31 < 7 && month == 12;
        }
    }
}
