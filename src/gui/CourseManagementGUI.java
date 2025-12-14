package src.gui;

import src.courses.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CourseManagementGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea outputArea;
    private RecordList<Course> courseList;
    private DataStore<Course> dataStore;
    private JTable courseTable;
    private DefaultTableModel courseTableModel;

    public CourseManagementGUI() {
        setTitle("Course Management System - OOP Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        courseList = new RecordList<>();
        dataStore = new DataStore<>();

        // Create tabbed interface
        tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Dashboard", createDashboardTab());
        tabbedPane.addTab("Course Management", createCourseManagementTab());
        tabbedPane.addTab("Course Details", createCourseDetailsTab());
        tabbedPane.addTab("Data Operations", createDataOperationsTab());

        add(tabbedPane);
    }

    // ============ DASHBOARD TAB ============
    private JPanel createDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Course Management System Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));

        JButton runDemoBtn = new JButton("Run Full Demo");
        runDemoBtn.addActionListener(e -> runFullDemo());
        controlPanel.add(runDemoBtn);

        JButton refreshBtn = new JButton("Refresh Status");
        refreshBtn.addActionListener(e -> displaySystemStatus());
        controlPanel.add(refreshBtn);

        JButton clearBtn = new JButton("Clear Output");
        clearBtn.addActionListener(e -> outputArea.setText(""));
        controlPanel.add(clearBtn);

        JButton initBtn = new JButton("Initialize Sample Data");
        initBtn.addActionListener(e -> initializeSampleData());
        controlPanel.add(initBtn);

        JButton viewStatsBtn = new JButton("View Statistics");
        viewStatsBtn.addActionListener(e -> displayStatistics());
        controlPanel.add(viewStatsBtn);

        JButton aboutBtn = new JButton("About");
        aboutBtn.addActionListener(e -> displayAbout());
        controlPanel.add(aboutBtn);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ============ COURSE MANAGEMENT TAB ============
    private JPanel createCourseManagementTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Course Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table for courses
        String[] columns = {"Course Code", "Title", "Credits", "Instructor", "Action"};
        courseTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable = new JTable(courseTableModel);
        courseTable.setFont(new Font("Arial", Font.PLAIN, 12));
        courseTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Course"));

        JLabel codeLabel = new JLabel("Course Code:");
        JTextField codeField = new JTextField();
        inputPanel.add(codeLabel);
        inputPanel.add(codeField);

        JLabel titleLabel2 = new JLabel("Course Title:");
        JTextField titleField = new JTextField();
        inputPanel.add(titleLabel2);
        inputPanel.add(titleField);

        JLabel creditsLabel = new JLabel("Credit Hours:");
        JTextField creditsField = new JTextField();
        inputPanel.add(creditsLabel);
        inputPanel.add(creditsField);

        JLabel instrLabel = new JLabel("Instructor Name:");
        JTextField instrField = new JTextField();
        inputPanel.add(instrLabel);
        inputPanel.add(instrField);

        JLabel qualLabel = new JLabel("Instructor Qualification:");
        JTextField qualField = new JTextField();
        inputPanel.add(qualLabel);
        inputPanel.add(qualField);

        JButton addBtn = new JButton("Add Course");
        addBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addBtn.addActionListener(e -> {
            try {
                String code = codeField.getText().trim();
                String title = titleField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                String instrName = instrField.getText().trim();
                String qual = qualField.getText().trim();

                if (code.isEmpty() || title.isEmpty() || instrName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CourseInstructor instr = new CourseInstructor(instrName, qual);
                Course course = new Course(code, title, credits, instr);
                courseList.addItem(course);

                // Refresh table
                refreshCourseTable();
                codeField.setText("");
                titleField.setText("");
                creditsField.setText("");
                instrField.setText("");
                qualField.setText("");

                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Credit hours must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton refreshTableBtn = new JButton("Refresh Table");
        refreshTableBtn.addActionListener(e -> refreshCourseTable());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(addBtn);
        buttonPanel.add(refreshTableBtn);
        inputPanel.add(buttonPanel);

        panel.add(inputPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ============ COURSE DETAILS TAB ============
    private JPanel createCourseDetailsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Course Details View");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton showAllBtn = new JButton("Show All Courses");
        showAllBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ALL COURSES IN SYSTEM ===\n\n");
            List<Course> courses = courseList.getAll();
            if (courses.isEmpty()) {
                sb.append("No courses added yet.\n");
            } else {
                for (int i = 0; i < courses.size(); i++) {
                    sb.append((i+1)).append(". ").append(courses.get(i).displayCourseDetails()).append("\n\n");
                }
                sb.append("Total Courses: ").append(courses.size());
            }
            detailsArea.setText(sb.toString());
        });
        controlPanel.add(showAllBtn);

        JButton searchBtn = new JButton("Search Course");
        searchBtn.addActionListener(e -> {
            String code = JOptionPane.showInputDialog(this, "Enter Course Code:");
            if (code != null && !code.trim().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                List<Course> courses = courseList.getAll();
                boolean found = false;
                for (Course c : courses) {
                    if (c.getCourseCode().equalsIgnoreCase(code.trim())) {
                        sb.append("=== COURSE FOUND ===\n\n");
                        sb.append(c.displayCourseDetails());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    sb.append("Course with code ").append(code).append(" not found.");
                }
                detailsArea.setText(sb.toString());
            }
        });
        controlPanel.add(searchBtn);

        JButton statsBtn = new JButton("Course Statistics");
        statsBtn.addActionListener(e -> {
            List<Course> courses = courseList.getAll();
            StringBuilder sb = new StringBuilder();
            sb.append("=== COURSE STATISTICS ===\n\n");
            sb.append("Total Courses: ").append(courses.size()).append("\n");
            if (!courses.isEmpty()) {
                int totalCredits = 0;
                for (Course c : courses) {
                    totalCredits += c.getCreditHours();
                }
                sb.append("Total Credit Hours: ").append(totalCredits).append("\n");
                sb.append("Average Credits/Course: ").append(String.format("%.2f", (double) totalCredits / courses.size()));
            }
            detailsArea.setText(sb.toString());
        });
        controlPanel.add(statsBtn);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ============ DATA OPERATIONS TAB ============
    private JPanel createDataOperationsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Data Persistence Operations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("File Operations"));

        JButton saveBtn = new JButton("Save Courses to File");
        saveBtn.addActionListener(e -> {
            try {
                List<Course> courses = courseList.getAll();
                dataStore.saveToFile("data/courses.dat", courses);
                logArea.append("✓ Successfully saved " + courses.size() + " courses to data/courses.dat\n");
            } catch (Exception ex) {
                logArea.append("✗ Error saving: " + ex.getMessage() + "\n");
            }
        });
        controlPanel.add(saveBtn);

        JButton loadBtn = new JButton("Load Courses from File");
        loadBtn.addActionListener(e -> {
            try {
                List<Course> loaded = dataStore.loadFromFile("data/courses.dat");
                logArea.append("✓ Successfully loaded " + loaded.size() + " courses from data/courses.dat\n");
                for (Course c : loaded) {
                    logArea.append("   - " + c.getCourseCode() + ": " + c.getTitle() + "\n");
                }
            } catch (Exception ex) {
                logArea.append("✗ Error loading: " + ex.getMessage() + "\n");
            }
        });
        controlPanel.add(loadBtn);

        JButton clearFileBtn = new JButton("Clear Log");
        clearFileBtn.addActionListener(e -> logArea.setText(""));
        controlPanel.add(clearFileBtn);

        JButton verifyBtn = new JButton("Verify File Exists");
        verifyBtn.addActionListener(e -> {
            java.io.File f = new java.io.File("data/courses.dat");
            if (f.exists()) {
                logArea.append("✓ data/courses.dat exists (Size: " + f.length() + " bytes)\n");
            } else {
                logArea.append("✗ data/courses.dat does not exist\n");
            }
        });
        controlPanel.add(verifyBtn);

        JButton sampleDataBtn = new JButton("Load Sample Data");
        sampleDataBtn.addActionListener(e -> initializeSampleData());
        controlPanel.add(sampleDataBtn);

        JButton testBtn = new JButton("Run Full Test");
        testBtn.addActionListener(e -> runFullDemo());
        controlPanel.add(testBtn);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ============ HELPER METHODS ============
    private void refreshCourseTable() {
        courseTableModel.setRowCount(0);
        for (Course c : courseList.getAll()) {
            courseTableModel.addRow(new Object[]{
                c.getCourseCode(),
                c.getTitle(),
                c.getCreditHours(),
                c.getInstructor().getName(),
                "View"
            });
        }
    }

    private void initializeSampleData() {
        courseList = new RecordList<>();
        
        courseList.addItem(new Course("CS101", "Data Structures", 4, new CourseInstructor("Dr. Ahmed", "PhD Computer Science")));
        courseList.addItem(new Course("CS102", "Algorithms", 3, new CourseInstructor("Prof. Fatima", "MS Software Engineering")));
        courseList.addItem(new Course("CS201", "Object Oriented Programming", 4, new CourseInstructor("Dr. Hassan", "PhD OOP")));
        courseList.addItem(new Course("MATH101", "Calculus", 3, new CourseInstructor("Prof. Ali", "MS Mathematics")));
        courseList.addItem(new Course("PHY101", "Physics", 4, new CourseInstructor("Dr. Zainab", "PhD Physics")));

        refreshCourseTable();
        JOptionPane.showMessageDialog(this, "Sample data initialized with 5 courses!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void runFullDemo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COURSE MANAGEMENT SYSTEM - FULL DEMO ===\n\n");

        try {
            // Initialize sample data
            if (courseList.size() == 0) {
                initializeSampleData();
            }

            sb.append("1. COURSE DATA\n");
            sb.append("==============\n");
            for (Course c : courseList.getAll()) {
                sb.append(c.displayCourseDetails()).append("\n");
            }

            sb.append("\n2. STATISTICS\n");
            sb.append("=============\n");
            sb.append("Total Courses: ").append(courseList.size()).append("\n");
            int totalCredits = 0;
            for (Course c : courseList.getAll()) {
                totalCredits += c.getCreditHours();
            }
            sb.append("Total Credit Hours: ").append(totalCredits).append("\n");
            sb.append("Average Credits/Course: ").append(String.format("%.2f", (double) totalCredits / courseList.size())).append("\n");

            sb.append("\n3. DATA PERSISTENCE TEST\n");
            sb.append("========================\n");
            dataStore.saveToFile("data/courses.dat", courseList.getAll());
            sb.append("✓ Saved courses to data/courses.dat\n");

            List<Course> loaded = dataStore.loadFromFile("data/courses.dat");
            sb.append("✓ Loaded ").append(loaded.size()).append(" courses from file\n");

            sb.append("\n4. SYSTEM STATUS\n");
            sb.append("================\n");
            sb.append("✓ All operations completed successfully!\n");

        } catch (Exception e) {
            sb.append("✗ Error: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }

        outputArea.setText(sb.toString());
    }

    private void displaySystemStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SYSTEM STATUS ===\n\n");
        sb.append("Total Courses: ").append(courseList.size()).append("\n");
        sb.append("Total Students (OOP Support): Available in StudentManagement.java\n");
        sb.append("Data File Path: data/courses.dat\n");
        sb.append("System Ready: ").append(courseList.size() >= 0 ? "YES" : "NO").append("\n");
        outputArea.setText(sb.toString());
    }

    private void displayStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SYSTEM STATISTICS ===\n\n");
        List<Course> courses = courseList.getAll();
        sb.append("Total Courses: ").append(courses.size()).append("\n");
        if (!courses.isEmpty()) {
            int totalCredits = 0;
            for (Course c : courses) {
                totalCredits += c.getCreditHours();
            }
            sb.append("Total Credit Hours: ").append(totalCredits).append("\n");
            sb.append("Average Credits/Course: ").append(String.format("%.2f", (double) totalCredits / courses.size())).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void displayAbout() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COURSE MANAGEMENT SYSTEM ===\n\n");
        sb.append("Project: OOP-Project\n");
        sb.append("Author: daud-shahbaz\n");
        sb.append("Type: Educational Course Management System\n");
        sb.append("Features:\n");
        sb.append("  • Course Creation & Management\n");
        sb.append("  • Instructor Assignment\n");
        sb.append("  • Data Persistence (File I/O)\n");
        sb.append("  • Student Management\n");
        sb.append("  • Advanced OOP Concepts\n");
        sb.append("\nThis GUI provides easy interaction with all system components.\n");
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseManagementGUI gui = new CourseManagementGUI();
            gui.setVisible(true);
        });
    }
}
