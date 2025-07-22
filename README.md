🧾 Introduction
Purpose
This project provides a comprehensive, standalone desktop solution for managing student grades and calculating academic performance metrics, built with Java Swing.

Scope
Covers core grade management functionality, GUI implementation, GPA calculations, student/course management, and user interaction—using only standard Java libraries.

Target Audience
🧑‍💻 Developers: For extension and debugging.

🔧 Maintainers: For updates and performance enhancements.

🧪 QA Testers: For functionality verification.

👩‍🏫 End-Users: Educators or students for academic tracking.

🧠 System Overview
Application Description
ClassGradeManager simplifies student record management, providing a user-friendly GUI for:

Enrolling students

Recording course details

GPA computation

Generating detailed academic reports

Class-wide statistics

Key Features
✅ Clean and intuitive GUI
✅ Multi-student enrollment with course input
✅ GPA computation with grade distribution
✅ Tabular summaries of academic performance
✅ Dark theme with Nimbus Look and Feel
✅ Input validation and error handling
✅ Class-wide performance overview
✅ One-click data reset functionality

🏗️ Architecture and Design
Design Pattern
Follows a simplified MVC architecture:

ClassGradeManager = View + Controller

Student & Course = Model

UML (Conceptual)
pgsql
Copy code
+-----------------------+       +-----------------+       +-----------------+
| ClassGradeManager     |       | Student         |       | Course          |
+-----------------------+       +-----------------+       +-----------------+
| - students: ArrayList |       | - name: String  |       | - name: String  |
| - currentStudent      |       | - numCourses: int|      | - credits: double|
| - UI Components       |       | - courses: List  |      | - grade: String |
+-----------------------+       | - gpa: double   |       +-----------------+
| + addStudent()        |       | + addCourse()   |
| + addCourse()         |       | + getGpa()      |
| + calculateStudentGPA()|      +-----------------+
| + showClassStatistics()|
| + clearAll()          |
+-----------------------+
💻 Technical Implementation
GPA Calculation Logic
Converts letter grades to quality points (A+ = 4.0, F = 0.0)

Computes:
GPA = Total Quality Points / Total Credits

Tracks best and worst course based on quality points

Grade Mapping Table
Grade	Points
A+, A	4.0
A−	3.7
B+	3.3
B	3.0
B−	2.7
C+	2.3
C	2.0
C−	1.7
D+	1.3
D	1.0
D−	0.7
F	0.0

Validation & Input Handling
Validates grade format using regex: [A-F][+-]?

Parses and validates numeric fields with try-catch

Error feedback via JOptionPane

UI Theming
Nimbus Look and Feel

Custom dark mode (Color(30, 30, 30))

Styled buttons with red-accented borders

📘 User Guide
➕ Add Student
Input student name and number of courses

Click Add Student

Begin course entry for that student

🧾 Add Course
Enter course name, credit hours, and grade

Click Add Course

Repeat for all courses

📊 GPA Calculation
Click Calculate GPA after adding courses to see:

GPA

Total credits

Best and worst course grades

📑 Student Summary
Click Show Summary to display all courses and calculated points.

📈 Class Statistics
Click Class Statistics to view:

Number of students

Class average GPA

Grade distribution

Top/lowest GPAs

🧹 Clear All Data
Click Clear All to reset the system to initial state.

🚀 Future Enhancements
🗂️ Persistent storage (JSON/CSV/File)

👥 Student selector (ComboBox)

✏️ Edit/Delete course functionality

🧪 More precise error messaging

📄 Exportable PDF/printable reports

🔐 User authentication system

📉 Advanced statistics (e.g., standard deviation)

📬 Contact Information
Shehryar Ahmed Khan
CodeAlpha Internship Program
📁 GitHub: n1x5-slayer
