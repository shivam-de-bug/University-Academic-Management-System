# University Academic Management System

## Project Overview
This project is designed to manage student enrollments, schedules, academic records, and user interactions within a university environment. It utilizes object-oriented programming (OOP) concepts such as inheritance, polymorphism, encapsulation, and abstraction to provide a comprehensive academic management solution.

The project includes the following key features:
- Role-based user functionalities for Students, Professors, Administrators, and Teaching Assistants (TAs).
- Course registration and management system.
- Academic progress tracking and grade management.
- Feedback system for courses and exception handling.

---

## Functionalities

### Student Functionalities
1. **View Available Courses:**
    - Displays courses for the current semester with details such as code, title, professor, credits, prerequisites, and timings.

2. **Register for Courses:**
    - Students can register for courses in their current semester while ensuring prerequisites are met.
    - Enforces a credit limit of 20 credits.

3. **View Schedule:**
    - Displays the weekly schedule, including timings, locations, and professor names.

4. **Track Academic Progress:**
    - View grades, calculate SGPA and CGPA based on completed courses.

5. **Drop Courses:**
    - Drop a course within a set deadline. Exception handling ensures deadlines are respected.

6. **Submit Complaints:**
    - Submit and view the status of complaints (Pending or Resolved).

### Professor Functionalities
1. **Manage Courses:**
    - Update course details such as syllabus, timings, credits, and prerequisites.

2. **View Enrolled Students:**
    - Access the list of students, their academic standing, and contact details.

### Administrator Functionalities
1. **Manage Course Catalog:**
    - Add, view, or delete courses in the catalog.

2. **Manage Student Records:**
    - Update grades, personal information, and view student records.

3. **Assign Professors to Courses:**
    - Assign professors to courses based on availability and expertise.

4. **Handle Complaints:**
    - View and resolve complaints with filtering options by status or date.

### Teaching Assistant Functionalities
1. **Assist Professors:**
    - View and manage student grades for assigned courses.
    - Inherits all student functionalities with added capabilities.

---

## Enhancements
### Generic Feedback System
- Students can provide feedback on completed courses using numeric ratings and textual comments.
- Professors can view this feedback via their user interface.

### Exception Handling
- **CourseFullException:** Prevents registration if a course is full.
- **InvalidLoginException:** Handles invalid login attempts.
- **DropDeadlinePassedException:** Handles attempts to drop courses past the deadline.

### Teaching Assistant Role
- Implemented using inheritance, extending the Student class.
- Provides additional functionalities for grade management.

---

## Application Flow
1. **Start Application:**
    - Users select their role: Student, Professor, Administrator, or Teaching Assistant.

2. **Role-Based Interface:**
    - Each role has access to specific functionalities tailored to their responsibilities.

3. **Exit Application:**
    - Allows users to log out and safely exit.

---

## Submission Guidelines
- **Code Structure:**
    - Organized in separate directories for each role (Student, Professor, Administrator, TA).

- **Documentation:**
    - Includes a README explaining OOP concepts applied, project functionalities, and usage instructions.

- **UML Diagram:**
    - Provides a detailed overview of class relationships and system architecture.

---

## Assumptions
1. Students start from semester one and complete semesters sequentially.
2. A fixed credit limit (20) applies, with courses worth either 2 or 4 credits.
3. The administrator sets deadlines for course drops.
4. Login for administrators uses a fixed password.

---

## Installation and Usage
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory and run the main application file:
   ```bash
   cd project-directory
   java Main.java
   ```

3. Follow the terminal prompts to log in and use the system based on your role.

---

## Demonstration
1. Includes examples of usage with three students, two professors, one administrator, and five courses.
2. Showcases role-specific functionalities and exception handling scenarios.

---

## UML Diagram
A UML diagram is included to illustrate class relationships and the system architecture.

