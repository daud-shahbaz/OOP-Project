package src.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class StudentManagementGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea outputArea;
    private JTable studentTable;
    private DefaultTableModel studentTableModel;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 750);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardTab());
        tabbedPane.addTab("Science Students", createScienceStudentsTab());
        tabbedPane.addTab("Business Students", createBusinessStudentsTab());
        tabbedPane.addTab("Engineering Students", createEngineeringStudentsTab());
        tabbedPane.addTab("Student Reports", createReportsTab());
        tabbedPane.addTab("Performance Analytics", createAnalyticsTab());

        add(tabbedPane);
    }

    private JPanel createDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Student Management Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));

        JButton initBtn = new JButton("Initialize Sample Students");
        initBtn.addActionListener(e -> initializeSampleStudents());
        controlPanel.add(initBtn);

        JButton displayAllBtn = new JButton("Display All Students");
        displayAllBtn.addActionListener(e -> displayAllStudents());
        controlPanel.add(displayAllBtn);

        JButton gradesBtn = new JButton("Show Grade Distribution");
        gradesBtn.addActionListener(e -> showGradeDistribution());
        controlPanel.add(gradesBtn);

        JButton performanceBtn = new JButton("Top Performers");
        performanceBtn.addActionListener(e -> showTopPerformers());
        controlPanel.add(performanceBtn);

        JButton transcriptBtn = new JButton("Generate Transcripts");
        transcriptBtn.addActionListener(e -> generateTranscripts());
        controlPanel.add(transcriptBtn);

        JButton clearBtn = new JButton("Clear Output");
        clearBtn.addActionListener(e -> outputArea.setText(""));
        controlPanel.add(clearBtn);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createScienceStudentsTab() {
        return createStudentCategoryTab("Science", new String[]{"Physics", "Chemistry", "Biology", "Mathematics"});
    }

    private JPanel createBusinessStudentsTab() {
        return createStudentCategoryTab("Business", new String[]{"Accounting", "Economics", "Management", "Finance"});
    }

    private JPanel createEngineeringStudentsTab() {
        return createStudentCategoryTab("Engineering", new String[]{"Electronics", "Mechanical", "Software", "Civil"});
    }

    private JPanel createStudentCategoryTab(String category, String[] courses) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(category + " Students Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Student ID", "Name", "Program", "Total Marks", "Percentage", "Grade", "GPA"};
        studentTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(studentTableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 11));
        studentTable.setRowHeight(25);
        studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New " + category + " Student"));

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField();
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        JLabel marksLabel = new JLabel("Marks (comma-separated):");
        JTextField marksField = new JTextField();
        inputPanel.add(marksLabel);
        inputPanel.add(marksField);

        JButton addBtn = new JButton("Add Student");
        addBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String marksStr = marksField.getText().trim();

                if (name.isEmpty() || marksStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] marksArray = marksStr.split(",");
                double[] marks = new double[marksArray.length];
                for (int i = 0; i < marksArray.length; i++) {
                    marks[i] = Double.parseDouble(marksArray[i].trim());
                }

                studentTableModel.addRow(new Object[]{
                    category.charAt(0) + String.format("%03d", studentTableModel.getRowCount() + 1),
                    name,
                    category,
                    String.format("%.2f", calculateTotal(marks)),
                    String.format("%.2f%%", calculatePercentage(marks)),
                    calculateGrade(marks),
                    String.format("%.2f", calculateGPA(marks))
                });

                nameField.setText("");
                marksField.setText("");
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid marks format. Use comma-separated numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton refreshBtn = new JButton("Refresh Table");
        refreshBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Table refreshed", "Success", JOptionPane.INFORMATION_MESSAGE));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        inputPanel.add(buttonPanel);

        panel.add(inputPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createReportsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Student Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Report Options"));

        JButton attendanceBtn = new JButton("Course Registration Report");
        attendanceBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== COURSE REGISTRATION REPORT ===\n\n");
            sb.append("Science Program:\n");
            sb.append("  • Physics (PHY101) - 4 credits\n");
            sb.append("  • Chemistry (CHEM101) - 4 credits\n");
            sb.append("  • Biology (BIO101) - 3 credits\n");
            sb.append("  • Mathematics (MATH101) - 3 credits\n");
            sb.append("  Total: 14 credit hours\n");
            reportArea.setText(sb.toString());
        });
        controlPanel.add(attendanceBtn);

        JButton acadBtn = new JButton("Academic Standing Report");
        acadBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ACADEMIC STANDING REPORT ===\n\n");
            sb.append("Grading Scale:\n");
            sb.append("  A+ : 90% - 100%\n");
            sb.append("  A  : 80% - 89%\n");
            sb.append("  B+ : 70% - 79%\n");
            sb.append("  B  : 60% - 69%\n");
            sb.append("  C  : 50% - 59%\n");
            sb.append("  F  : Below 50%\n");
            reportArea.setText(sb.toString());
        });
        controlPanel.add(acadBtn);

        JButton feeBtn = new JButton("Credit Hour Summary");
        feeBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== CREDIT HOUR SUMMARY ===\n\n");
            sb.append("Full-time Status: >= 12 credit hours\n");
            sb.append("Part-time Status: < 12 credit hours\n");
            reportArea.setText(sb.toString());
        });
        controlPanel.add(feeBtn);

        JButton transcriptBtn = new JButton("Transcript Sample");
        transcriptBtn.addActionListener(e -> reportArea.setText("Sample transcript preview"));
        controlPanel.add(transcriptBtn);

        JButton clearBtn = new JButton("Clear Report");
        clearBtn.addActionListener(e -> reportArea.setText(""));
        controlPanel.add(clearBtn);

        JButton aboutBtn = new JButton("System Info");
        aboutBtn.addActionListener(e -> reportArea.setText("Student Management System Info"));
        controlPanel.add(aboutBtn);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAnalyticsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Performance Analytics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea analyticsArea = new JTextArea();
        analyticsArea.setEditable(false);
        analyticsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(analyticsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Analytics Options"));

        JButton gpaBtn = new JButton("GPA Statistics");
        gpaBtn.addActionListener(e -> analyticsArea.setText("GPA statistics will appear here..."));
        controlPanel.add(gpaBtn);

        JButton programBtn = new JButton("Program Analytics");
        programBtn.addActionListener(e -> analyticsArea.setText("Program analytics will appear here..."));
        controlPanel.add(programBtn);

        JButton trendBtn = new JButton("Performance Trends");
        trendBtn.addActionListener(e -> analyticsArea.setText("Performance trends will appear here..."));
        controlPanel.add(trendBtn);

        JButton predictionBtn = new JButton("Predictive Analytics");
        predictionBtn.addActionListener(e -> analyticsArea.setText("Predictive analytics will appear here..."));
        controlPanel.add(predictionBtn);

        JButton clearBtn = new JButton("Clear Analytics");
        clearBtn.addActionListener(e -> analyticsArea.setText(""));
        controlPanel.add(clearBtn);

        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private double calculateTotal(double[] marks) {
        double total = 0;
        for (double m : marks) total += m;
        return total;
    }

    private double calculatePercentage(double[] marks) {
        return calculateTotal(marks) / (marks.length * 100) * 100;
    }

    private String calculateGrade(double[] marks) {
        double pct = calculatePercentage(marks);
        if (pct >= 90) return "A+";
        else if (pct >= 80) return "A";
        else if (pct >= 70) return "B+";
        else if (pct >= 60) return "B";
        else if (pct >= 50) return "C";
        else return "F";
    }

    private double calculateGPA(double[] marks) {
        String grade = calculateGrade(marks);
        switch (grade) {
            case "A+": return 4.0;
            case "A": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "C": return 2.0;
            default: return 0.0;
        }
    }

    private void initializeSampleStudents() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SAMPLE STUDENTS INITIALIZED ===\n\n");
        sb.append("Science Students (3):\n");
        sb.append("  S001 - Ali (Total: 380, Percentage: 95.0%, Grade: A+, GPA: 4.0)\n");
        sb.append("  S002 - Fatima (Total: 340, Percentage: 85.0%, Grade: A, GPA: 3.7)\n");
        sb.append("  S003 - Hassan (Total: 300, Percentage: 75.0%, Grade: B+, GPA: 3.3)\n\n");
        outputArea.setText(sb.toString());
    }

    private void displayAllStudents() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL REGISTERED STUDENTS ===\n\n");
        sb.append("Science Program:\n");
        sb.append("  1. Ali - S001 (GPA: 4.0)\n");
        sb.append("  2. Fatima - S002 (GPA: 3.7)\n");
        sb.append("  3. Hassan - S003 (GPA: 3.3)\n");
        outputArea.setText(sb.toString());
    }

    private void showGradeDistribution() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GRADE DISTRIBUTION ===\n\n");
        sb.append("A+ Grade: 33%\n");
        sb.append("A Grade: 45%\n");
        sb.append("B+ Grade: 22%\n");
        outputArea.setText(sb.toString());
    }

    private void showTopPerformers() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TOP PERFORMERS ===\n\n");
        sb.append("1. Ali (Science) - GPA: 4.0\n");
        sb.append("2. Zainab (Business) - GPA: 4.0\n");
        outputArea.setText(sb.toString());
    }

    private void generateTranscripts() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TRANSCRIPT GENERATION ===\n\n");
        sb.append("Sample Transcript:\n");
        sb.append("Student: Ali | ID: S001 | Program: Science\n");
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI();
            gui.setVisible(true);
        });
    }
}
