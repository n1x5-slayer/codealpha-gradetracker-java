import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassGradeManager extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private Student currentStudent;
    private JPanel mainPanel, inputPanel, resultPanel, studentPanel;
    private JButton addStudentButton, addCourseButton, calculateButton, clearButton, 
                    summaryButton, statsButton, nextStudentButton;
    private JTextField studentNameField, courseNameField, creditField, gradeField, numCoursesField;
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    
    public ClassGradeManager() {
        // Set up main frame
        setTitle("Class Grade Manager");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Apply dark theme
        applyDarkTheme();

        // Create panels
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(new Color(30, 30, 30));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(169, 46, 34)), 
            "Student Information",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(230, 230, 230)
        ));

        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBackground(new Color(30, 30, 30));
        resultPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(169, 46, 34)), 
            "Results",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(230, 230, 230)
        ));
        
        // Create input fields
        studentNameField = new JTextField();
        studentNameField.setBackground(new Color(50, 50, 50));
        studentNameField.setForeground(Color.WHITE);
        studentNameField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        numCoursesField = new JTextField();
        numCoursesField.setBackground(new Color(50, 50, 50));
        numCoursesField.setForeground(Color.WHITE);
        numCoursesField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        courseNameField = new JTextField();
        courseNameField.setBackground(new Color(50, 50, 50));
        courseNameField.setForeground(Color.WHITE);
        courseNameField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        creditField = new JTextField();
        creditField.setBackground(new Color(50, 50, 50));
        creditField.setForeground(Color.WHITE);
        creditField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        gradeField = new JTextField();
        gradeField.setBackground(new Color(50, 50, 50));
        gradeField.setForeground(Color.WHITE);
        gradeField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        
        // Create buttons
        addStudentButton = createStyledButton("Add Student");
        addCourseButton = createStyledButton("Add Course");
        calculateButton = createStyledButton("Calculate GPA");
        clearButton = createStyledButton("Clear All");
        summaryButton = createStyledButton("Show Summary");
        statsButton = createStyledButton("Class Statistics");
        nextStudentButton = createStyledButton("Next Student");
        
        // Set button actions
        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        addCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateStudentGPA();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });

        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStudentSummary();
            }
        });

        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showClassStatistics();
            }
        });

        nextStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextStudent();
            }
        });
        
        // Create result area
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(40, 40, 40));
        resultArea.setForeground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        scrollPane = new JScrollPane(resultArea);
        scrollPane.setBackground(new Color(30, 30, 30));

        // Add components to input panel
        inputPanel.add(new JLabel("Student Name:", SwingConstants.RIGHT) {{
            setForeground(Color.WHITE);
        }});
        inputPanel.add(studentNameField);
        inputPanel.add(new JLabel("Number of Courses:", SwingConstants.RIGHT) {{
            setForeground(Color.WHITE);
        }});
        inputPanel.add(numCoursesField);
        
        // Student panel for course input (hidden initially)
        studentPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        studentPanel.setBackground(new Color(30, 30, 30));
        studentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(169, 46, 34)), 
            "Add Course for Current Student",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(230, 230, 230)
        ));
        studentPanel.add(new JLabel("Course Name:", SwingConstants.RIGHT) {{
            setForeground(Color.WHITE);
        }});
        studentPanel.add(courseNameField);
        studentPanel.add(new JLabel("Credit Hours:", SwingConstants.RIGHT) {{
            setForeground(Color.WHITE);
        }});
        studentPanel.add(creditField);
        studentPanel.add(new JLabel("Grade (A-F):", SwingConstants.RIGHT) {{
            setForeground(Color.WHITE);
        }});
        studentPanel.add(gradeField);
        studentPanel.setVisible(false);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(addStudentButton);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(summaryButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(nextStudentButton);
        addCourseButton.setVisible(false);
        nextStudentButton.setVisible(false);

        // Add panels to main panel
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(studentPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main panel and result panel to frame
        add(mainPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    private void applyDarkTheme() {
        UIManager.put("control", new Color(30, 30, 30));
        UIManager.put("info", new Color(30, 30, 30));
        UIManager.put("nimbusBase", new Color(18, 30, 49));
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(100, 100, 100));
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
        UIManager.put("nimbusGreen", new Color(176, 179, 50));
        UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
        UIManager.put("nimbusLightBackground", new Color(30, 30, 30));
        UIManager.put("nimbusOrange", new Color(191, 98, 4));
        UIManager.put("nimbusRed", new Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
        UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
        UIManager.put("text", new Color(230, 230, 230));
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus theme not available, using default");
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(169, 46, 34));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 0, 0), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addStudent() {
        String name = studentNameField.getText().trim();
        String numCoursesStr = numCoursesField.getText().trim();

        if (name.isEmpty() || numCoursesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int numCourses = Integer.parseInt(numCoursesStr);
            if (numCourses <= 0) {
                JOptionPane.showMessageDialog(this, "Number of courses must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentStudent = new Student(name, numCourses);
            students.add(currentStudent);
            
            resultArea.append("\n=== ADDED STUDENT: " + name + " ===\n");
            resultArea.append("Please add " + numCourses + " courses for this student\n");
            
            // Switch to course input mode
            studentPanel.setVisible(true);
            addCourseButton.setVisible(true);
            nextStudentButton.setVisible(true);
            addStudentButton.setEnabled(false);
            
            // Clear and focus on course name
            courseNameField.setText("");
            creditField.setText("");
            gradeField.setText("");
            courseNameField.requestFocus();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number of courses. Enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourse() {
        if (currentStudent == null) return;
        
        String name = courseNameField.getText().trim();
        String creditStr = creditField.getText().trim();
        String grade = gradeField.getText().trim().toUpperCase();

        if (name.isEmpty() || creditStr.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double credits = Double.parseDouble(creditStr);
            if (credits <= 0) {
                JOptionPane.showMessageDialog(this, "Credit hours must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidGrade(grade)) {
                JOptionPane.showMessageDialog(this, "Invalid grade. Use A, B, C, D, or F", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentStudent.addCourse(new Course(name, credits, grade));
            resultArea.append(String.format("Added: %s - %s credits - Grade %s\n", name, creditStr, grade));
            
            // Check if all courses added
            if (currentStudent.getCourses().size() >= currentStudent.getNumCourses()) {
                addCourseButton.setEnabled(false);
                resultArea.append("\nAll courses added for " + currentStudent.getName() + "\n");
                resultArea.append("Click 'Next Student' to add another student or 'Calculate GPA' for results\n");
            }
            
            // Clear input fields
            courseNameField.setText("");
            creditField.setText("");
            gradeField.setText("");
            courseNameField.requestFocus();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid credit hours. Enter a number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nextStudent() {
        if (currentStudent == null) return;
        
        // Clear fields for next student
        studentNameField.setText("");
        numCoursesField.setText("");
        courseNameField.setText("");
        creditField.setText("");
        gradeField.setText("");
        
        // Reset UI
        studentPanel.setVisible(false);
        addCourseButton.setVisible(false);
        addCourseButton.setEnabled(true);
        nextStudentButton.setVisible(false);
        addStudentButton.setEnabled(true);
        
        studentNameField.requestFocus();
    }

    private boolean isValidGrade(String grade) {
        return grade.matches("[A-F][+-]?") || grade.equalsIgnoreCase("F");
    }

    private void calculateStudentGPA() {
        if (currentStudent == null || currentStudent.getCourses().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No courses to calculate", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Course> courses = currentStudent.getCourses();
        double totalCredits = 0;
        double totalQualityPoints = 0;
        double bestGrade = 0;
        double worstGrade = 4.0;
        String bestCourse = "";
        String worstCourse = "";

        for (Course course : courses) {
            double gradePoints = convertGradeToPoints(course.getGrade());
            totalQualityPoints += gradePoints * course.getCredits();
            totalCredits += course.getCredits();
            
            if (gradePoints > bestGrade) {
                bestGrade = gradePoints;
                bestCourse = course.getName();
            }
            if (gradePoints < worstGrade) {
                worstGrade = gradePoints;
                worstCourse = course.getName();
            }
        }

        double gpa = totalQualityPoints / totalCredits;
        currentStudent.setGpa(gpa);

        resultArea.append("\n=== GPA RESULTS FOR " + currentStudent.getName() + " ===\n");
        resultArea.append(String.format("• Total Courses: %d\n", courses.size()));
        resultArea.append(String.format("• Total Credits: %.2f\n", totalCredits));
        resultArea.append(String.format("• GPA: %.2f\n", gpa));
        resultArea.append(String.format("• Best Grade: %s (%.2f)\n", bestCourse, bestGrade));
        resultArea.append(String.format("• Worst Grade: %s (%.2f)\n", worstCourse, worstGrade));
        resultArea.append("=============================\n");
    }

    private double convertGradeToPoints(String grade) {
        switch (grade.toUpperCase()) {
            case "A+": return 4.0;
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "D-": return 0.7;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    private void clearAll() {
        students.clear();
        currentStudent = null;
        resultArea.setText("");
        
        studentNameField.setText("");
        numCoursesField.setText("");
        courseNameField.setText("");
        creditField.setText("");
        gradeField.setText("");
        
        studentPanel.setVisible(false);
        addCourseButton.setVisible(false);
        addCourseButton.setEnabled(true);
        nextStudentButton.setVisible(false);
        addStudentButton.setEnabled(true);
        
        studentNameField.requestFocus();
    }

    private void showStudentSummary() {
        if (currentStudent == null || currentStudent.getCourses().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No courses to display", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Course> courses = currentStudent.getCourses();

        resultArea.append("\n=== COURSE SUMMARY FOR " + currentStudent.getName() + " ===\n");
        resultArea.append("No.  Course Name       Credits  Grade  Points\n");
        resultArea.append("--------------------------------------------\n");

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            double points = convertGradeToPoints(course.getGrade());
            resultArea.append(String.format("%-4d %-17s %-7.1f %-6s %-6.2f\n", 
                i + 1, 
                course.getName(), 
                course.getCredits(), 
                course.getGrade(), 
                points));
        }
        resultArea.append("--------------------------------------------\n");
    }

    private void showClassStatistics() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students to display statistics", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalGPA = 0;
        double highestGPA = Double.MIN_VALUE;
        double lowestGPA = Double.MAX_VALUE;
        Student topStudent = null;
        Student bottomStudent = null;
        Map<String, Integer> gradeDistribution = new HashMap<>();

        // Initialize grade distribution
        String[] possibleGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"};
        for (String grade : possibleGrades) {
            gradeDistribution.put(grade, 0);
        }

        // Calculate statistics
        for (Student student : students) {
            double gpa = student.getGpa();
            totalGPA += gpa;

            if (gpa > highestGPA) {
                highestGPA = gpa;
                topStudent = student;
            }
            if (gpa < lowestGPA) {
                lowestGPA = gpa;
                bottomStudent = student;
            }

            // Count grade distribution
            for (Course course : student.getCourses()) {
                String grade = course.getGrade().toUpperCase();
                gradeDistribution.put(grade, gradeDistribution.getOrDefault(grade, 0) + 1);
            }
        }

        double averageGPA = totalGPA / students.size();

        // Display results
        resultArea.append("\n=== CLASS STATISTICS ===\n");
        resultArea.append(String.format("• Number of Students: %d\n", students.size()));
        resultArea.append(String.format("• Average GPA: %.2f\n", averageGPA));
        resultArea.append(String.format("• Highest GPA: %.2f (%s)\n", highestGPA, topStudent != null ? topStudent.getName() : "N/A"));
        resultArea.append(String.format("• Lowest GPA: %.2f (%s)\n", lowestGPA, bottomStudent != null ? bottomStudent.getName() : "N/A"));
        
        resultArea.append("\nGrade Distribution:\n");
        for (Map.Entry<String, Integer> entry : gradeDistribution.entrySet()) {
            if (entry.getValue() > 0) {
                resultArea.append(String.format("• %s: %d\n", entry.getKey(), entry.getValue()));
            }
        }
        resultArea.append("=======================\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClassGradeManager();
            }
        });
    }
}

class Student {
    private String name;
    private int numCourses;
    private ArrayList<Course> courses;
    private double gpa;

    public Student(String name, int numCourses) {
        this.name = name;
        this.numCourses = numCourses;
        this.courses = new ArrayList<>();
        this.gpa = 0.0;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public String getName() {
        return name;
    }

    public int getNumCourses() {
        return numCourses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}

class Course {
    private String name;
    private double credits;
    private String grade;

    public Course(String name, double credits, String grade) {
        this.name = name;
        this.credits = credits;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getCredits() {
        return credits;
    }

    public String getGrade() {
        return grade;
    }
}
