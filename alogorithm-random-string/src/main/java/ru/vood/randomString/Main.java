package ru.vood.randomString;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        RandomString randomString = new RandomString(2000);

        FileWriter writeFile = null;
        try {
            File logFile = new File("C:\\Temp\\111error.txt");
            writeFile = new FileWriter(logFile);
            //for (long i = 0; i < 9223372036854775807L; i++) {
            for (long i = 0; i < 1000L; i++) {
                writeFile.write(randomString.nextString() + "\n");
                if (i % 10000 == 0) {
                    System.out.println("i = " + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
