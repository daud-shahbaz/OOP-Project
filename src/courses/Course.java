package src.courses;

import java.io.Serializable;

public class Course implements Serializable, Identifiable {
    private static int totalCourses = 0;
    
    // Has a Course Instructor
    private String courseCode;
    private String title;
    private int creditHours;
    private CourseInstructor instructor;

    public Course(){
        courseCode = null;
        title = null;
        creditHours = 0;
        instructor = new CourseInstructor();
    }

    public Course(String courseCode, String title, int creditHours) {
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.instructor = new CourseInstructor();
        totalCourses++;
    }

    public Course(String courseCode, String title, int creditHours, String instructorName, String qualification) {
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.instructor = new CourseInstructor(instructorName, qualification);
        totalCourses++;
    }

    public Course(String courseCode, String title, int creditHours, CourseInstructor instructor) {
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.instructor = instructor;
        totalCourses++;
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getTitle(){
        return title;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public CourseInstructor getInstructor() {
        return instructor;
    }

    public static int getTotalCourses() {
        return totalCourses;
    }

    public void setCourseCode(String courseCode){
         this.courseCode = courseCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public void setInstructor(CourseInstructor instructor) {
        this.instructor = instructor;
    }

    public String displayCourseDetails() {
        return "Course Code: " + courseCode + "Title: " + title + 
               " | Credit Hours: " + creditHours +
               " | Instructor: " + instructor.getName() + 
               " (" + instructor.getQualification() + ")";
    }

    @Override
    public String getId() {
        return courseCode;
    }

    @Override
    public String toString() {
        return displayCourseDetails();
    }
}
