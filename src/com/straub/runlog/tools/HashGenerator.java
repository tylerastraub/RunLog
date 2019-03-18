// Creates a hash int based on a String input. Mainly used to create unique file
// names based on date and time run occurred.

package com.straub.runlog.tools;

public class HashGenerator {
    public int createHash(String text) {
        return text.hashCode();
    }
}
