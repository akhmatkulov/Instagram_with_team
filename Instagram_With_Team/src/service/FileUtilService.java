package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class FileUtilService<T> {
    private static final String PATH = "E:\\Java\\FileResources\\";
    private final Gson gson = new Gson();

    public void write(ArrayList<T> T, String LAST_PATH) {
        Writer writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(PATH + LAST_PATH);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(gson.toJson(T));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    bufferedWriter.close();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<T> read(String LAST_PATH) {
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            reader = new FileReader(PATH + LAST_PATH);
            bufferedReader = new BufferedReader(reader);
            String s = "", temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                s += temp;
            }
            return gson.fromJson(s, new TypeToken<ArrayList<T>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.getCause();
            }
        }
        return null;
    }
}
