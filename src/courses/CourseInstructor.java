package src.courses;

import java.io.Serializable;

public class CourseInstructor implements Serializable {
    private String name;
    private String qualification;

    public CourseInstructor() {
        name = null;
        qualification = null;
    }

    public CourseInstructor(String name, String qualification) {
        this.name = name;
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Qualification: " + qualification;
    }
}