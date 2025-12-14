package src;

import src.courses.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Course Management System ===\n");

        // Test Course & CourseInstructor
        CourseInstructor instructor1 = new CourseInstructor("Dr. Ahmed", "PhD Computer Science");
        Course course1 = new Course("CS101", "Data Structures", 4, instructor1);
        Course course2 = new Course("CS102", "Algorithms", 3, new CourseInstructor("Prof. Fatima", "MS Software Engineering"));

        System.out.println(course1.getTitle() + " - " + course1.getCourseCode() + " (" + course1.getCreditHours() + " credits)");
        System.out.println(course2.displayCourseDetails());
        System.out.println("Total Courses: " + Course.getTotalCourses() + "\n");

        // Test RecordList
        RecordList<Course> courseList = new RecordList<>();
        courseList.addItem(course1);
        courseList.addItem(course2);

        System.out.println("Courses in RecordList: " + courseList.size());
        for (Course c : courseList.getAll()) {
            System.out.println("  - " + c.getTitle());
        }

        // Test DataStore
        DataStore<Course> dataStore = new DataStore<>();
        dataStore.saveToFile("data/courses.dat", courseList.getAll());
        System.out.println("Courses saved to data/courses.dat");

        List<Course> loadedCourses = dataStore.loadFromFile("data/courses.dat");
        System.out.println("Loaded " + loadedCourses.size() + " courses from file\n");
    }
}