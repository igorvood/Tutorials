package ru.vood.random.string;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static final String C_TEMP_111_ERROR_TXT = "C:\\Temp\\111error.txt";

    public static void main(String[] args) {
        RandomString randomString = new RandomString(2000);

        File logFile = new File(C_TEMP_111_ERROR_TXT);

        try (FileWriter writeFile = new FileWriter(logFile)) {

            for (long i = 0; i < 1000L; i++) {
                writeFile.write(randomString.nextString() + "\n");
                if (i % 10000 == 0) {
                    System.out.println("i = " + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
