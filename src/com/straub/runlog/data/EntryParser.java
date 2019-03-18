// Takes data from the "new entry" window and parses it so that it can be stored
// into a local file. Each file generates a unique hash string based on the date
// and time of the run.

package com.straub.runlog.data;

import com.straub.runlog.tools.HashGenerator;

import java.util.HashMap;

public class EntryParser {
    public EntryParser(HashMap<String, String> entryData) {
        FileIO fileIO = new FileIO();
        HashGenerator hashGenerator = new HashGenerator();

        String dateAndTime = entryData.get("DATE:") + entryData.get("TIME:");
        int hash = hashGenerator.createHash(dateAndTime);

        fileIO.writeToFile("runs/" + hash + ".txt", entryData);
    }
}
