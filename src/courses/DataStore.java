package src.courses;

import java.io.*;
import java.util.*;

public class DataStore<T> {
    // Saving and loading any type of records (Students, Courses, Transcripts) from .dat files
    
    public void saveToFile(String fileName, List<T> items) {
        new File("data").mkdirs();
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(items);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public List<T> loadFromFile(String fileName) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            List<T> items = (List<T>) input.readObject();
            System.out.println("Data loaded from " + fileName);
            return items;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("File deleted: " + fileName);
        }
    }
}
