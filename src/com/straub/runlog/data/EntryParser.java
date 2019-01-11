package com.straub.runlog.data;

import java.util.HashMap;

public class EntryParser {
    public EntryParser(HashMap<String, String> entryData) {
        FileIO fileIO = new FileIO();

        // TODO: create a unique random file name rather than use date
        fileIO.writeToFile("res/runs/" + entryData.get("DATE:"),
                entryData);
    }
}
