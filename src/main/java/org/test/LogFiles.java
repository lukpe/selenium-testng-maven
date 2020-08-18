package org.test;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFiles {
    private final String logDir = System.getProperty("user.dir") + "\\target\\logs";

    public void merge() {
        try {
            File dir = new File(logDir);
            File[] files = dir.listFiles();
            PrintWriter pw = new PrintWriter(logDir + "\\" + "testRun_" + getDate() + "_suite.log");

            if (files != null) {
                for (File file : files) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = br.readLine();
                    while (line != null) {
                        pw.println(line);
                        line = br.readLine();
                    }
                    pw.flush();
                    br.close();
                }
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HHmmssSSS");
        Date date = new Date();
        return df.format(date);
    }
}

