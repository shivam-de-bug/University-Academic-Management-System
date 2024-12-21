import java.util.ArrayList;

import java.util.Scanner;

public class Professor extends User implements Interface.Proffcourse {
    private ArrayList<Course> coursesTaught;
    Scanner scanner = new Scanner(System.in);

    public Professor(String email, String password) {
        super(email, password);
        coursesTaught = new ArrayList<>();
    }
    public void printString(String message) {
        System.out.println(message);
    }

    public void printInt(int number) {
        System.out.println(number);
    }
    @Override
    public String getRole() {
        return "Professor";
    }

    @Override
    public void viewProfile() {
        printString(" Email: " + getEmail());
        printString( "Role: " + getRole());

    }

    @Override
    public void updatePassword(String newPassword) {
        setPassword(newPassword);
        printString("Password updated successfully for professor: " + getEmail());
    }

    public ArrayList<Course> getCoursesTaught() {
        return coursesTaught;
    }
    @Override
    public void manageCourse(Course course) {
        printString("Managing Course: " + course.getTitle() + "\n" +
                    "1. Update Syllabus\n" +
                    "2. Update Class Timings and Days\n" +
                    "3. Update Credits\n" +
                    "4. Update Prerequisites\n" +
                    "5. Update Enrollment Limits\n" +
                    "6. Update Office Hours\n" +
                    "Choose an option : ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();  

        switch (choice) {
            case 1:
                printString("Enter new syllabus: ");
                String syllabus = scanner.nextLine();
                course.setSyllabus(syllabus);
                printString("Syllabus updated.");
                break;
            case 2:
                printString("Kindly follow the format: Day (e.g., Mon, Tue, Wed, etc.) and timing (e.g., 3:00PM-4:30PM).\n" +
                            "Example: Mon 3:00PM-4:30PM or Wed 1:00PM-2:30PM\n" +
                            "Enter new class timings and days: ");
                String timings = scanner.nextLine();
                course.setTimings(timings);
                printString("Class timings and Days updated.");
                break;
            case 3:
                printString("Enter new credits: ");
                int credits = scanner.nextInt();
                course.setCredits(credits);
                printString("Credits updated.");
                break;
            case 4:
                printString("1. Add course as prerequisite\n" +
                            "2. Remove course from prerequisites\n" +
                            "Choose an option: ");
                
                int option = scanner.nextInt();  
                scanner.nextLine();  
                switch (option) {  
                    case 1:
                        printString("Enter the course title to add as prerequisite: ");
                        String addTitle = scanner.nextLine();
                        printString("Enter the course code to add as prerequisite: ");
                        String addCode = scanner.nextLine();
                        Course addPrerequisite = new Course(addTitle, addCode);
                        course.addPrerequisite(addPrerequisite);
                        printString("Prerequisite added.");
                        break;
                    case 2:
                        printString("Enter the course title to remove from prerequisites: ");
                        String removeTitle = scanner.nextLine();
                        printString("Enter the course code to remove from prerequisites: ");
                        String removeCode = scanner.nextLine();
                        Course removePrerequisite = new Course(removeTitle, removeCode);
                        course.removePrerequisite(removePrerequisite);
                        printString("Prerequisite removed.");
                        break;
                    default:
                        printString("Invalid option.");
                        break;
                }
                break;
            case 5:
                printString("Enter new enrollment limit: ");
                int limit = scanner.nextInt();
                course.setEnrollmentLimit(limit);
                printString("Enrollment limit updated.");
                break;
            case 6:
            printString("Kindly follow the format: Day (e.g., Mon, Tue, Wed, etc.) and timing (e.g., 3:00PM-4:30PM).\n" +
            "Example: Mon 3:00PM-4:30PM or Wed 1:00PM-2:30PM\n" +
            "Enter new office hours: ");
               
                scanner.nextLine(); 
                String officeHours = scanner.nextLine();
                course.setOfficeHours(officeHours);
                printString("Office hours updated.");
                break;
            default:
                printString("Invalid choice.");
        }
    }
    @Override
    public void viewTaughtCourses() {
        printString("Courses you are teaching:");
        int index = 0;
        while (index < coursesTaught.size()) {
            printString(coursesTaught.get(index).toString());
            index++;
        }
    }

    public ArrayList<Course> getTaughtCourses(){
        return coursesTaught;
    }

    public void addCourse(Course course) {
        coursesTaught.add(course);
    }

    public void viewEnrolledta(Course course) {
        printString("TA enrolled in " + course.getTitle() + ":");
        int i = 0;
        while (i < course.getEnrolledTas().size()) {
            TA ta = course.getEnrolledTas().get(i);
            printString((i + 1) + ". Email: " + ta.getEmail() +
                "\n ");
            
            i++;
        }
    }
    public void viewEnrolledStudents(Course course) {
        printString("Students enrolled in " + course.getTitle() + ":");
        int i = 0;
        while (i < course.getEnrolledStudents().size()) {
            Student student = course.getEnrolledStudents().get(i);
            printString((i + 1) + ". Email: " + student.getEmail() +
                "\n   Academic Standing (GPA): ");
            student.calculateCGPA();
            i++;
        }
    }
    
    @Override
    public void assignGrade(int currentSemester) {
        printString("Courses you are teaching:");
        int i = 0;
        while (i < coursesTaught.size()) {
            printString((i + 1) + ". " + coursesTaught.get(i).getTitle());
            i++;
        }

        printString("Choose a course to assign grades (enter number): ");
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (courseIndex < 0 || courseIndex >= coursesTaught.size()) {
            printString("Invalid course selection.");
            return;
        }

        Course selectedCourse = coursesTaught.get(courseIndex);

        if (selectedCourse.getSemester() != currentSemester) {
            printString("You can only assign grades for courses in the current semester.");
            return;
        }

        printString("Students enrolled in " + selectedCourse.getTitle() + ":");
        ArrayList<Student> enrolledStudents = selectedCourse.getEnrolledStudents();

        if (enrolledStudents.isEmpty()) {
            printString("No students enrolled in this course.");
            return;
        }

        int j = 0;
        while (j < enrolledStudents.size()) {
            Student student = enrolledStudents.get(j);
            printString((j + 1) + ". " + student.getEmail());
            j++;
        }

        printString("Choose a student to assign a grade (enter number): ");
        int studentIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (studentIndex < 0 || studentIndex >= enrolledStudents.size()) {
            printString("Invalid student selection.");
            return;
        }

        Student selectedStudent = enrolledStudents.get(studentIndex);

        printString("Enter the grade (in alphabet) for " + selectedStudent.getEmail() + ": ");
        String grade = scanner.nextLine();

        Grade newGrade = new Grade(selectedCourse, grade, currentSemester);
        selectedStudent.addGrade(newGrade);
        selectedStudent.addgradededcourse(selectedCourse);
        selectedStudent.checkAndUpgradeSemester();
        printString("Grade assigned successfully.");
       
    }
  
    
    public void viewFeedback(Course course) {
        
        int courseIndex = -1;
        courseIndex=DataStore.courses.indexOf(course);
        
    
        if (courseIndex != -1) {
            printString("Feedback for " + course.getTitle() + ":");
            ArrayList<Feedback<?>> feedbackList = DataStore.getFeedbacks(courseIndex);
            
            if (feedbackList.isEmpty()) {
                printString("No feedback.");
            } else {
                for (Feedback<?> feedback : feedbackList) {
                    printString("Feedback: " + feedback.getFeedback());
                }
            }
        } else {
            printString("Course not found.");
        }
    }
    
   
    }

