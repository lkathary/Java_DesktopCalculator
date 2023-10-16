package com.example.calculator;

import java.io.*;
import java.util.ArrayList;

public class History {

    private static final String FILE_NAME = "history.out";
    private ArrayList<String> history = new ArrayList<>();

    public ArrayList<String> getHistory() {return history;}

    public void saveHistory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME, false))) {
            oos.writeObject(history);
        } catch (IOException ex) {
            System.out.println("Can not save " + FILE_NAME);
            System.out.println(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadHistory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            history = (ArrayList<String>) ois.readObject();
        } catch (IOException ignored) {
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clearHistory() {
        history.clear();
        saveHistory();
    }

    public void addSting(String str) {
        history.add(str);
    }

    @Override
    public String toString() {
        return "History{" +
                "history=" + history +
                '}';
    }
}