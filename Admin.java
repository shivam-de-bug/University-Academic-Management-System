import java.util.ArrayList; 
import java.util.Scanner;

public class Admin extends User implements Interface.adminmanage {
    Scanner scanner = new Scanner(System.in);
    public void printString(String message) {
        System.out.println(message);
    }

    public void printInt(int number) {
        System.out.println(number);
    }
    public Admin(String email, String password) {
        super(email, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void viewProfile() {
        printString("Admin Email: " + getEmail());
        printString( "Role: " + getRole());

    }

    @Override
    public void updatePassword(String newPassword) {
        setPassword(newPassword);
        printString("Password updated successfully for admin: " + getEmail());
    }

    @Override
    public void addCourse(Course course) {
        DataStore.courses.add(course);
        printString("Course added: " + course.getTitle());
    }
    @Override
    public void removeCourse(Course course) {
        DataStore.courses.remove(course);
        printString("Course removed: " + course.getTitle());
    }
    @Override
    public void assignProfessor(Course course, Professor professor) {
        boolean conflict = false;
        int index = 0;
        while (index < professor.getTaughtCourses().size()) {
            Course taughtCourse = professor.getTaughtCourses().get(index);
            if (taughtCourse.getTimings().equalsIgnoreCase(course.getTimings())) {
                conflict = true;
                break;
            }
            index++;
        }

        if (conflict) {
            printString("Professor " + professor.getEmail() + " is already teaching a course at this time.\n" +
                        "Assignment failed due to scheduling conflict.");
            return;
        }

        course.setProfessor(professor);
        professor.addCourse(course);
        printString("Assigned Professor " + professor.getEmail() + " to " + course.getTitle());
    }

    public void assignta(Course course, TA ta) {
        course.addTA(ta);;
        ta.setcourse(course);
        printString("Assigned TA " + ta.getEmail() + " to " + course.getTitle());
    }

    public void viewCoursesByStudent() {
        ArrayList<Student> students = DataStore.students;

        if (students.isEmpty()) {
            printString("No students are currently registered.");
            return;
        }

        printString("Select a student to view their registered courses:");

        int index = 0;
        while (index < students.size()) {
            printString((index + 1) + ". " + students.get(index).getEmail());
            index++;
        }

        printString("Enter the student number to view their courses: ");
        int studentIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 
        if (studentIndex < 0 || studentIndex >= students.size()) {
            printString("Invalid student selection.");
            return;
        }

        Student selstudent = students.get(studentIndex);
        ArrayList<Course> registeredCourses = selstudent.getRegisteredCourses();

        if (registeredCourses.isEmpty()) {
            printString(selstudent.getEmail() + " has not registered for any courses.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Courses registered by ").append(selstudent.getEmail()).append(":\n");
        index = 0;
        while (index < registeredCourses.size()) {
            Course course = registeredCourses.get(index);
            sb.append("Course: ").append(course.getTitle())
            .append(" | Timings and Days: ").append(course.getTimings())
            .append(" | Location: ").append(course.getLocation())
            .append(" | Professor: ").append(course.getProfessor() != null ? course.getProfessor().getEmail() : "TBA")
            .append("\n");
            index++;
        }
        printString(sb.toString());
    }
    public void viewAllCourses() {
        ArrayList<Course> allcourses = DataStore.courses;
    
        
        if (allcourses.isEmpty()) {
            printString("No courses are currently available.");
            return;
        }
    
        StringBuilder sb = new StringBuilder();
        sb.append("Viewing all courses:\n");
    
        int index = 0;
        while (index < allcourses.size()) {
            Course course = allcourses.get(index);
            sb.append("Course: ").append(course.getTitle())
              .append(" | Timings and Days: ").append(course.getTimings())
              .append(" | Location: ").append(course.getLocation())
              .append(" | Professor: ").append(course.getProfessor() != null ? course.getProfessor().getEmail() : "TBA")
              .append("\n");
            index++;
        }
        sb.append("\n"); 
        printString(sb.toString());
    }
  
    @Override
    public void handleComplaints() {
        printString("Viewing all pending complaints:");
        boolean pendingFound = false;
        ArrayList<String> processedDescriptions = new ArrayList<>();
    
        int index = 0;
        while (index < DataStore.complaints.size()) {
            Complaint complaint = DataStore.complaints.get(index);
            if (complaint.getStatus().equalsIgnoreCase("Pending") && !processedDescriptions.contains(complaint.getDescription())) {
                pendingFound = true;
                printString(complaint.toString());
                printString("Would you like to mark this complaint as resolved? (y/n): ");
                String choice = scanner.nextLine();
                
                if (choice.equalsIgnoreCase("y")) {
                    int innerIndex = 0;
                    while (innerIndex < DataStore.complaints.size()) {
                        Complaint c = DataStore.complaints.get(innerIndex);
                        if (c.getDescription().equals(complaint.getDescription())) {
                            c.setStatus("Resolved");
                        }
                        innerIndex++;
                    }
                    printString("Complaint marked as resolved.");
                    processedDescriptions.add(complaint.getDescription());
                }
            }
            index++;
        }
        
        if (!pendingFound) {
            printString("No pending complaints found.");
        }
    }
    
public void printComplaints(String status) {
    int complaintNumber = 1;
    ArrayList<String> printedComplaints = new ArrayList<>();
    
    switch (status) {
        case "Resolved":
            printString("Viewing resolved complaints:");
            for (Complaint complaint : DataStore.complaints) {
                if (complaint.getStatus().equalsIgnoreCase("Resolved")) {
                    if (!printedComplaints.contains(complaint.getDescription())) {
                        printString(complaintNumber + ". " + complaint.toString());
                        printedComplaints.add(complaint.getDescription());
                        complaintNumber++;
                    }
                }
            }
            break;
        case "Pending":
            printString("Viewing pending complaints:");
            for (Complaint complaint : DataStore.complaints) {
                if (complaint.getStatus().equalsIgnoreCase("Pending")) {
                    if (!printedComplaints.contains(complaint.getDescription())) {
                        printString(complaintNumber + ". " + complaint.toString());
                        printedComplaints.add(complaint.getDescription());
                        complaintNumber++;
                    }
                }
            }
            break;
        case "All":
            printString("Viewing all complaints:");
            for (Complaint complaint : DataStore.complaints) {
                if (!printedComplaints.contains(complaint.getDescription())) {
                    printString(complaintNumber + ". " + complaint.toString() + "\n");
                    printedComplaints.add(complaint.getDescription());
                    complaintNumber++;
                }
            }
            break;
        default:
            printString("Invalid status.");
    }
}

    @Override
    public void changeGrade() {
        printString("Select a student to change or add their grade in Integer:");
        ArrayList<Student> students = DataStore.students;
        int index = 0;
        while (index < students.size()) {
            printString((index + 1) + ". " + students.get(index).getEmail());
            index++;
        }

        printString("Enter the student number: ");
        int studentIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (studentIndex < 0 || studentIndex >= students.size()) {
            printString("Invalid student selection.");
            return;
        }

        Student selectedStudent = students.get(studentIndex);

        printString("Courses registered by " + selectedStudent.getEmail() + ":");
        ArrayList<Course> registeredCourses = selectedStudent.getRegisteredCourses();
        index = 0;
        while (index < registeredCourses.size()) {
            printString((index + 1) + ". " + registeredCourses.get(index).getTitle() + " (Semester: " + registeredCourses.get(index).getSemester() + ")");
            index++;
        }

        printString("Select a course to change the grade: ");
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (courseIndex < 0 || courseIndex >= registeredCourses.size()) {
            printString("Invalid course selection.");
            return;
        }

        Course selectedCourse = registeredCourses.get(courseIndex);

        printString("Enter the new grade (in alphabate)for " + selectedStudent.getEmail() + " in " + selectedCourse.getTitle() + ": ");
        String newGrade = scanner.nextLine();

        ArrayList<Grade> grades = selectedStudent.getGrades();
        boolean gradeFound = false;
        index = 0;
        while (index < grades.size()) {
            Grade grade = grades.get(index);
            if (grade.getCourse().equals(selectedCourse)) {
                grade.setGrade(newGrade);
                printString("Grade updated successfully.");
                gradeFound = true;
                break;
            }
            index++;
        }
        
        if (!gradeFound) {
             
            Grade newGradeEntry = new Grade(selectedCourse, newGrade, selectedCourse.getSemester());
            selectedStudent.addGrade(newGradeEntry);
            selectedStudent.addgradededcourse(selectedCourse);
            printString("Grade assigned successfully.");
            

        }
        selectedStudent.checkAndUpgradeSemester();
    }
        public void setDropDeadline(Scanner scanner) {
        printString("Enter the Courses Drop Deadline (DD-MM-YYYY): ");
        String date = scanner.nextLine();
        DataStore.dropdeadline = date; 
        System.out.println("Drop deadline set to: " + DataStore.dropdeadline);
    }
    


    
}
