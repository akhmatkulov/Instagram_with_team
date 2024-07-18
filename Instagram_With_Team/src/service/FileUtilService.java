package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileUtilService<T> {
    private static final String PATH = "Desktop/json/";
    private Class<T> type;
    private final Gson gson = new Gson();

    public FileUtilService(Class<T> type) {
        this.type = type;
    }

    public void write(ArrayList<T> T, String LAST_PATH) {
        Writer writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(PATH + LAST_PATH);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(gson.toJson(T));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (writer != null) {
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
            File file = new File(PATH + LAST_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            StringBuilder s = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                s.append(temp);
            }

            Type listType = TypeToken.getParameterized(ArrayList.class, type).getType();
            return gson.fromJson(s.toString(), listType);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
