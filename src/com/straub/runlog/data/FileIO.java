package com.straub.runlog.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileIO {

    public FileIO() {

    }

    public String readFile(String filePath){
        File f = new File(filePath);
        if (!f.isFile()) {
            return null;
        }

        String fullFile;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            StringBuilder sb = new StringBuilder();
            String currentLine = br.readLine();

            while (currentLine != null) {
                sb.append(currentLine);
                sb.append(System.lineSeparator());
                currentLine = br.readLine();
            }

            fullFile = sb.toString();
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try{
                br.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }

        return fullFile;
    }

    public HashMap<String, String> createHashMapFromFile(String filePath,
                                                         String seperator) {
        HashMap<String, String> map = new HashMap<String, String>();

        String currentLine;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            while ((currentLine = br.readLine()) != null)
            {
                String[] parts = currentLine.split(seperator, 2);
                if (parts.length >= 2)
                {
                    String key = parts[0];
                    String value = parts[1];
                    map.put(key, value);
                } else {
                    System.out.println("line is not of valid key/value format: "
                            + currentLine);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public void writeToFile(String filePath, String text){
        File file = new File(filePath);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text + "\n");
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String filePath, HashMap<String, String> values) {
        File file = new File(filePath);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(Map.Entry<String, String> entry : values.entrySet()) {
                bw.write(entry.getKey() + entry.getValue() + "\n");
            }
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
