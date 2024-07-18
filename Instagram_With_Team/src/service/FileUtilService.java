package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileUtilService<T> {
    private static final String PATH = "E:\\Java\\FileResources\\";
    private Class<T> type;
    private final Gson gson = new Gson();

    public FileUtilService(Class<T> type) {
        this.type = type;
    }

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
//            TypeToken<ArrayList<T>> typeToken = new TypeToken<>() {};
//            return gson.fromJson(s, typeToken);
            Type listType = TypeToken.getParameterized(ArrayList.class, type).getType();
            return gson.fromJson(s, listType);

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
