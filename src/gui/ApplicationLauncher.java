package src.gui;

import src.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ApplicationLauncher extends JFrame {
    private JPanel mainPanel;

    public ApplicationLauncher() {
        setTitle("OOP-Project - Application Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("OOP-Project Application Suite");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel subtitleLabel = new JLabel("Course & Student Management System with GUI");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setForeground(new Color(100, 100, 100));
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center: Application selection buttons
        JPanel appPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        appPanel.setBackground(new Color(240, 240, 240));
        appPanel.setBorder(BorderFactory.createTitledBorder("Select Application"));

        JButton courseBtn = createAppButton(
            "Course Management",
            "Manage courses, instructors, and course data",
            new Color(52, 152, 219)
        );
        courseBtn.addActionListener(e -> launchCourseManagement());
        appPanel.add(courseBtn);

        JButton studentBtn = createAppButton(
            "Student Management",
            "Manage students, grades, and transcripts",
            new Color(46, 204, 113)
        );
        studentBtn.addActionListener(e -> launchStudentManagement());
        appPanel.add(studentBtn);

        JButton systemBtn = createAppButton(
            "System Utilities",
            "Configuration, diagnostics, and documentation",
            new Color(155, 89, 182)
        );
        systemBtn.addActionListener(e -> launchSystemUtilities());
        appPanel.add(systemBtn);

        JButton consoleBtn = createAppButton(
            "Console Demo",
            "Run the original console-based demonstration",
            new Color(230, 126, 34)
        );
        consoleBtn.addActionListener(e -> launchConsoleDemo());
        appPanel.add(consoleBtn);

        mainPanel.add(appPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 12));
        exitBtn.addActionListener(e -> System.exit(0));
        footerPanel.add(exitBtn);

        JButton aboutBtn = new JButton("About");
        aboutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        aboutBtn.addActionListener(e -> showAbout());
        footerPanel.add(aboutBtn);

        JButton helpBtn = new JButton("Help");
        helpBtn.setFont(new Font("Arial", Font.BOLD, 12));
        helpBtn.addActionListener(e -> showHelp());
        footerPanel.add(helpBtn);

        JButton infoBtn = new JButton("System Info");
        infoBtn.setFont(new Font("Arial", Font.BOLD, 12));
        infoBtn.addActionListener(e -> showSystemInfo());
        footerPanel.add(infoBtn);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createAppButton(String title, String description, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(10, 10));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(new Color(240, 240, 240));

        button.add(titleLabel, BorderLayout.NORTH);
        button.add(descLabel, BorderLayout.CENTER);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void launchCourseManagement() {
        SwingUtilities.invokeLater(() -> {
            CourseManagementGUI gui = new CourseManagementGUI();
            gui.setVisible(true);
        });
    }

    private void launchStudentManagement() {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI();
            gui.setVisible(true);
        });
    }

    private void launchSystemUtilities() {
        SwingUtilities.invokeLater(() -> {
            SystemUtilitiesGUI gui = new SystemUtilitiesGUI();
            gui.setVisible(true);
        });
    }

    private void launchConsoleDemo() {
        new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error running demo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private void showAbout() {
        StringBuilder sb = new StringBuilder();
        sb.append("OOP-PROJECT\n");
        sb.append("Version 2.0 (GUI Enhanced)\n\n");
        sb.append("A comprehensive Course Management and Student Information System\n");
        sb.append("built with Java and featuring Object-Oriented Programming principles.\n\n");
        sb.append("Author: Daud Shahbaz\n");
        sb.append("Repository: github.com/daud-shahbaz/OOP-Project\n\n");
        sb.append("Features:\n");
        sb.append("• Complete Course Management System\n");
        sb.append("• Multi-program Student Management\n");
        sb.append("• Automatic Grade & GPA Calculation\n");
        sb.append("• Transcript Generation\n");
        sb.append("• Data Persistence (File I/O)\n");
        sb.append("• Comprehensive GUI Interface\n");
        sb.append("• System Diagnostics & Utilities\n\n");
        sb.append("License: MIT\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "About OOP-Project", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("QUICK START GUIDE\n\n");
        sb.append("1. COURSE MANAGEMENT\n");
        sb.append("   • Click 'Course Management' button\n");
        sb.append("   • Use Dashboard tab to initialize sample data\n");
        sb.append("   • Add new courses in 'Course Management' tab\n\n");
        sb.append("2. STUDENT MANAGEMENT\n");
        sb.append("   • Click 'Student Management' button\n");
        sb.append("   • Initialize sample students from Dashboard\n");
        sb.append("   • Add students by program\n\n");
        sb.append("3. SYSTEM UTILITIES\n");
        sb.append("   • Check system status\n");
        sb.append("   • Run diagnostics\n\n");
        sb.append("4. CONSOLE DEMO\n");
        sb.append("   • Click to see original functionality\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSystemInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("SYSTEM INFORMATION\n\n");
        sb.append("Java Version: " + System.getProperty("java.version") + "\n");
        sb.append("Java Vendor: " + System.getProperty("java.vendor") + "\n");
        sb.append("OS Name: " + System.getProperty("os.name") + "\n");
        sb.append("OS Version: " + System.getProperty("os.version") + "\n");
        sb.append("OS Architecture: " + System.getProperty("os.arch") + "\n\n");
        sb.append("Available Processors: " + Runtime.getRuntime().availableProcessors() + "\n");
        sb.append("Max Memory: " + (Runtime.getRuntime().maxMemory() / (1024 * 1024)) + " MB\n");
        sb.append("Free Memory: " + (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " MB\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "System Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationLauncher launcher = new ApplicationLauncher();
            launcher.setVisible(true);
        });
    }
}
