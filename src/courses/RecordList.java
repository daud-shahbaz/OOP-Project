package src.courses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordList<T extends Identifiable> implements Serializable {
    // Stores objects for students, courses, transcripts
    private List<T> records;

    public RecordList() {
        this.records = new ArrayList<>();
    }

    public RecordList(List<T> records) {
        this.records = records;
    }

    public List<T> getRecordList() {
        return records;
    }

    public void setRecordList(List<T> records) {
        this.records = records;
    }

    public void addItem(T item) {
        records.add(item);
    }

    public void removeItem(String id) {
        records.removeIf(item -> item.getId().equals(id));
    }

    public T getById(String id) {
        for (T item : records) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<T> getAll() {
        return new ArrayList<>(records);
    }

    public int size() {
        return records.size();
    }

    public boolean isEmpty() {
        return records.isEmpty();
    }
}


