package src.students;

import java.io.Serializable;
import java.util.*;
import src.courses.*;

//ABSTRACT CLASS OF STUDENT AS BASE
abstract class Student implements ResultCalculator, Serializable{
    protected String studentId;
    protected String name;
    protected String program;
    protected List<Course> courses;
    protected Transcript transcript;
    protected double[] marks;
    public static int totalStudents = 0;

    public Student(String name, String program, List<Course> courses, double[] marks) {
        this.studentId = "S" + (++totalStudents);
        this.name = name;
        this.program = program;
        this.courses = courses;
        this.marks = marks;
        this.transcript = new Transcript();
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getProgram() { return program; }
    public List<Course> getCourses() { return courses; }
    public double[] getMarks() { return marks; }

    @Override
    public abstract double calculateTotal();
    @Override
    public abstract double calculatePercentage();
    @Override
    public abstract String calculateGrade();
    @Override
    public abstract double calculateGPA();

    public void displayStudentDetails() {
        System.out.println("\nStudent Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Program: " + program);

        System.out.println("Courses & Marks:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("  " + courses.get(i).getTitle() + " (" + courses.get(i).getCourseCode() + "): " + marks[i]);
        }

        System.out.println("Total Marks: " + calculateTotal());
        System.out.println("Percentage: " + calculatePercentage() + "%");
        System.out.println("Grade: " + calculateGrade());
        System.out.println("GPA: " + calculateGPA());
    }

    // FINDS STUDENT MARKS KISI SPECIFIC COURSE K
    public double getMarkByCourseCode(String code) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equalsIgnoreCase(code)) {
                return marks[i];
            }
        }
        return -1;
    }
}

//SCIENCE STUDENT KI CLASS
class ScienceStudent extends Student implements Serializable {
    public ScienceStudent(String name, String program, double[] marks) {
        super(name, program, getScienceCourses(), marks);
    }

    public static List<Course> getScienceCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("PHY101", "Physics", 4));
        courses.add(new Course("CHEM101", "Chemistry", 4));
        courses.add(new Course("BIO101", "Biology", 3));
        courses.add(new Course("MATH101", "Mathematics", 3));
        return courses;
    }

    @Override
    public double calculateTotal() {
        double total = 0;
        for (double m : marks) total += m;
        return total;
    }

    @Override
    public double calculatePercentage() {
        return calculateTotal() / (marks.length * 100) * 100;
    }

    @Override
    public String calculateGrade() {
        double pct = calculatePercentage();
        if (pct >= 90) return "A+";
        else if (pct >= 80) return "A";
        else if (pct >= 70) return "B+";
        else if (pct >= 60) return "B";
        else return "Fail";
    }

    @Override
    public double calculateGPA() {
        return calculateTotal() / (marks.length * 100) * 4.0;
    }
}

//ARTS STUDENT KI CLASS
class ArtsStudent extends Student implements Serializable {
    public ArtsStudent(String name, String program, double[] marks) {
        super(name, program, getArtsCourses(), marks);
    }

    public static List<Course> getArtsCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("HIST101", "History", 3));
        courses.add(new Course("ENG101", "English", 3));
        courses.add(new Course("PHIL101", "Philosophy", 3));
        return courses;
    }

    @Override
    public double calculateTotal() {
        double total = 0;
        for (double m : marks) total += m;
        return total;
    }

    @Override
    public double calculatePercentage() {
        return calculateTotal() / (marks.length * 100) * 100;
    }

    @Override
    public String calculateGrade() {
        double pct = calculatePercentage();
        if (pct >= 90) return "A+";
        else if (pct >= 80) return "A";
        else if (pct >= 70) return "B+";
        else if (pct >= 60) return "B";
        else return "Fail";
    }

    @Override
    public double calculateGPA() {
        return calculateTotal() / (marks.length * 100) * 4.0;
    }
}

//ENGINEERING STUDENT KI CLASS
class EngineeringStudent extends Student implements Serializable {
    public EngineeringStudent(String name, String program, double[] marks) {
        super(name, program, getEngineeringCourses(), marks);
    }

    public static List<Course> getEngineeringCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("MATH101", "Mathematics", 3));
        courses.add(new Course("ENGR101", "Engineering Fundamentals", 4));
        courses.add(new Course("PHY101", "Physics", 4));
        courses.add(new Course("CS101", "Computer Science", 3));
        return courses;
    }

    @Override
    public double calculateTotal() {
        double total = 0;
        for (double m : marks) total += m;
        return total;
    }

    @Override
    public double calculatePercentage() {
        return calculateTotal() / (marks.length * 100) * 100;
    }

    @Override
    public String calculateGrade() {
        double pct = calculatePercentage();
        if (pct >= 90) return "A+";
        else if (pct >= 80) return "A";
        else if (pct >= 70) return "B+";
        else if (pct >= 60) return "B";
        else return "Fail";
    }

    @Override
    public double calculateGPA() {
        return calculateTotal() / (marks.length * 100) * 4.0;
    }
}

// RESULTCALCULATOR INTERFACE
interface ResultCalculator {
    static final double passMarks = 50;
    double calculateTotal();
    double calculatePercentage();
    double calculateGPA();
    String calculateGrade();
}

// RESULT ENTRY CLASS
class ResultEntry implements Serializable {
    private final Course course;
    private final double marksObtained;

    public ResultEntry(Course course, double marks) {
        this.course = course;
        this.marksObtained = marks;
    }

    public Course getCourse() {
        return course;
    }

    public double getMarksObtained() {
        return marksObtained;
    }
}


// TRANSCRIPT CLASS
class Transcript implements Serializable {
    private final  List<ResultEntry> results = new ArrayList<>();

    // Add Result Entry
    public void addResultEntry(ResultEntry result) {
        results.add(result);
    }

    public double getTotalMarks() {
        double total = 0;
        for (ResultEntry result : results) {
            total += result.getMarksObtained();
        }
        return total;
    }

    public double getGPA() {
        return getTotalMarks() / results.size();
    }

    public List<ResultEntry> getResults() {
        return results;
    }
}

//RUNNER IDHR
public class StudentManagement {

    static Scanner input = new Scanner(System.in);
    static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        // PRESTORED BACHE
        students.add(new ArtsStudent("Hamna", "Arts", new double[]{80, 75, 88}));
        students.add(new EngineeringStudent("Daud", "Engineering", new double[]{85, 90, 92, 88}));
        students.add(new ScienceStudent("Wajeeh", "Science", new double[]{95, 90, 92, 89}));

        boolean running = true;

        while (running) {
            try {
                System.out.println("\n--- Student Management Menu ---");
                System.out.println("1. View student data");
                System.out.println("2. Edit student marks");
                System.out.println("3. Delete a student");
                System.out.println("4. Add new student");
                System.out.println("5. Search student by ID");
                System.out.println("6. Sort students by a course (descending order)");
                System.out.println("7. Exit");
                System.out.print("Choose option: ");

                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1: viewStudent(); break;
                    case 2: editStudentMarks(); break;
                    case 3: deleteStudent(); break;
                    case 4: addStudent(); break;
                    case 5: searchById(); break;
                    case 6: sortStudentsByCourse(); break;
                    case 7: running = false; System.out.println("Exiting System"); break;
                    default: System.out.println("OOPS! Invalid option, try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("OOPS! Invalid input, please enter a number.");
                input.nextLine();
            }
        }
    }

    static void listStudentsSimple() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " (" + s.getStudentId() + ") - " + s.getProgram());
        }
    }

    static int readStudentIndex(String msg) {
        if (students.isEmpty()) return -1;

        System.out.print(msg);
        int index = input.nextInt() - 1;
        input.nextLine();

        if (index < 0 || index >= students.size()) {
            System.out.println("OOPS! Invalid student number.");
            return -1;
        }
        return index;
    }

    static double readMarkSafe(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                double m = input.nextDouble();
                input.nextLine();
                if (m < 0 || m > 100) {
                    System.out.println("Marks should be between 0 and 100.");
                } else {
                    return m;
                }
            } catch (InputMismatchException e) {
                System.out.println("OOPS! Invalid mark, enter a number.");
                input.nextLine();
            }
        }
    }

    // MENU WALE METHODS
    static void viewStudent() {
        listStudentsSimple();
        if (students.isEmpty()) return;

        try {
            int index = readStudentIndex("Select student number to view: ");
            if (index == -1) return;
            students.get(index).displayStudentDetails();
        } catch (Exception e) {
            System.out.println("Error viewing student.");
        }
    }

    static void editStudentMarks() {
        listStudentsSimple();
        if (students.isEmpty()) return;

        try {
            int index = readStudentIndex("Select student number to edit: ");
            if (index == -1) return;

            Student s = students.get(index);
            System.out.println("\nEditing marks for: " + s.getName() + " (" + s.getStudentId() + ")");

            for (int i = 0; i < s.getCourses().size(); i++) {
                double newMark = readMarkSafe("Enter new mark for " + s.getCourses().get(i).getTitle() + ": ");
                s.getMarks()[i] = newMark;
            }

            System.out.println("Marks updated successfully.");
            s.displayStudentDetails();

        } catch (Exception e) {
            System.out.println("Error editing marks.");
        }
    }

    static void deleteStudent() {
        listStudentsSimple();
        if (students.isEmpty()) return;

        try {
            int index = readStudentIndex("Select student number to delete: ");
            if (index == -1) return;

            System.out.print("Are you sure you want to delete " + students.get(index).getName() + "? (yes/no): ");
            String ans = input.nextLine();

            if (ans.equalsIgnoreCase("yes")) {
                System.out.println(students.get(index).getName() + " deleted.");
                students.remove(index);
            } else {
                System.out.println("Delete cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting student.");
        }
    }

    static void addStudent() {
        try {
            System.out.print("Enter name: ");
            String name = input.nextLine();

            System.out.print("Enter program (Science/Arts/Engineering): ");
            String program = input.nextLine();

            List<Course> courses;
            if (program.equalsIgnoreCase("Science")) {
                courses = ScienceStudent.getScienceCourses();
            } else if (program.equalsIgnoreCase("Arts")) {
                courses = ArtsStudent.getArtsCourses();
            } else if (program.equalsIgnoreCase("Engineering")) {
                courses = EngineeringStudent.getEngineeringCourses();
            } else {
                System.out.println("Invalid program.");
                return;
            }

            double[] marks = new double[courses.size()];
            for (int i = 0; i < courses.size(); i++) {
                marks[i] = readMarkSafe("Enter marks for " + courses.get(i).getTitle() + ": ");
            }

            Student s;
            if (program.equalsIgnoreCase("Science")) s = new ScienceStudent(name, program, marks);
            else if (program.equalsIgnoreCase("Arts")) s = new ArtsStudent(name, program, marks);
            else s = new EngineeringStudent(name, program, marks);

            students.add(s);
            System.out.println("Student added: " + s.getName() + " (ID: " + s.getStudentId() + ")");

        } catch (Exception e) {
            System.out.println("Error adding student.");
        }
    }

    static void searchById() {
        System.out.print("Enter student ID: ");
        String id = input.nextLine();

        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                s.displayStudentDetails();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // EK COURSE K STUDENTS KI SORTING
    static void sortStudentsByCourse() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        // SHOW THE COURSES BEING TAUGHT
        System.out.println("\nAvailable course codes you can sort by:");
        System.out.println("PHY101, CHEM101, BIO101, MATH101, HIST101, ENG101, PHIL101, ENGR101, CS101");
        System.out.print("Enter course code (example: PHY101): ");
        String code = input.nextLine().trim();

        // FIND STUDENTS WHO TAKE SAID COURSE
        ArrayList<StudentMark> list = new ArrayList<>();

        for (Student s : students) {
            double mark = s.getMarkByCourseCode(code);
            if (mark != -1) {
                list.add(new StudentMark(s, mark));
            }
        }

        if (list.isEmpty()) {
            System.out.println("No student has this course code.");
            return;
        }

        // SORTING MARKS KI BASIS PR
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).mark < list.get(j).mark) {
                    StudentMark temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }

        System.out.println("\nStudents sorted by marks in " + code + " (highest first):");
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i).student;
            System.out.println((i + 1) + ". " + s.getName() + " (" + s.getStudentId() + ") - " + list.get(i).mark);
        }
    }

    // SORTING
    static class StudentMark {
        Student student;
        double mark;

        StudentMark(Student student, double mark) {
            this.student = student;
            this.mark = mark;
        }
    }
}
