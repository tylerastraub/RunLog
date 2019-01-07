package com.straub.runlog.data;

import java.io.*;

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

    public void writeToFile(String filePath, String text){
        File file = new File(filePath);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
