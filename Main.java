import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printString("\nWelcome to the University Course Registration System\n" +
                        "1. Login\n" +
                        "2. Sign Up\n" +
                        "3. Exit\n" +
                        "Enter your choice in Integer: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    signUp(scanner);
                    break;
                case 3:
                    printString("Exiting the application...");
                    running = false;
                    break;
                default:
                    printString("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

 

public static void login(Scanner scanner) {
    printString("\nLogin as:\n" +
                "1. Student\n" +
                "2. Professor\n" +
                "3. Administrator\n" +
                "4. Teaching Assistant\n" +
                "Enter your choice in Integer: ");

    int roleChoice = scanner.nextInt();
    scanner.nextLine();

    String role = "";
    switch (roleChoice) {
        case 1:
            role = "Student";
            break;
        case 2:
            role = "Professor";
            break;
        case 3:
            role = "Administrator";
            break;
        case 4:
            role = "Teaching Assistant";
            break;
        default:
            printString("Invalid choice!");
            return;
    }

    printString("Enter Email: ");
    String email = scanner.nextLine();
    printString("Enter Password: ");
    String password = scanner.nextLine();

    try {
        User user = null;
        switch (roleChoice) {
            case 1: 
                for (Student s : DataStore.students) {
                    if (s.getEmail().equals(email)) {
                        user = s;
                        break;
                    }
                }
                break;

            case 2:
                for (Professor p : DataStore.professors) {
                    if (p.getEmail().equals(email)) {
                        user = p;
                        break;
                    }
                }
                break;

            case 3: 
                for (Admin a : DataStore.admins) {
                    if (a.getEmail().equals(email)) {
                        user = a;
                        break;
                    }
                }
                break;

            case 4: 
                for (TA t : DataStore.tas) {
                    if (t.getEmail().equals(email)) {
                        user = t;
                        break;
                    }
                }
                break;
        }

        if (user == null) {
            throw  new InvalidLoginException("Email not found. Invalid login attempt.");
        } else if (!user.login(email, password)) {
            throw new InvalidLoginException("Invalid credentials. Incorrect password.");
        } else {
            printString("Login successful as " + role);

            
            if (user instanceof Student && !(user instanceof TA)) {
                studentMenu((Student) user, scanner);
            } else if (user instanceof Professor) {
                professorMenu((Professor) user, scanner);
            } else if (user instanceof Admin) {
                adminMenu((Admin) user, scanner);
            }else if (user instanceof TA) {
                printString("You are logged in as a Teaching Assistant (TA).\n" +
                            "As a TA, you have access to the following functionalities:\n" +
                            "1. Use functionalities as a Student\n" +
                            "2. Use functionalities specific to your TA role\n" +
                            "Please enter your choice in Integer (1 or 2): ");
                int ch = scanner.nextInt();
            
                switch (ch) {
                    case 1:
                        studentMenu((Student) user, scanner); 
                        break;
                    case 2:
                        TAMenu((TA) user, scanner); 
                        break;
                    default:
                        printString("Invalid choice. Please select either 1 or 2."); 
                        break;
                }
            }
            
        }

    } catch (InvalidLoginException e) {
        printString(e.getMessage());
    }
}



    public static void signUp(Scanner scanner) {
        printString("\nSign Up as:\n" +
                    "1. Student\n" +
                    "2. Professor\n" +
                    "3. Teaching Assistant\n" +
                    "Enter your choice in Integer: ");

        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        String role = "";
        switch (roleChoice) {
            case 1:
                role = "Student";
                break;
            case 2:
                role = "Professor";
                break;
            case 3:
                role = "Teaching Assistant";
                break;
            default:
                printString("Invalid choice!");
                return;
        }

        printString("Enter Email: ");
        String email = scanner.nextLine();

        if (DataStore.findUserByEmail(email) != null) {
            printString("Email already registered. Please try logging in.");
            return;
        }

        printString("Create Password: ");
        String password = scanner.nextLine();

        if (role.equals("Student")) {
            Student student = new Student(email, password);
            DataStore.addStudent(student);
            printString("Student registered successfully!");
        } else if (role.equals("Professor")) {
            Professor professor = new Professor(email, password);
            DataStore.addProfessor(professor);
            printString("Professor registered successfully!");
        }else if (role.equals("Teaching Assistant")) {
            TA ta = new TA(email, password);
            DataStore.addTA(ta);
            printString("TA registered successfully!");
        }
        
    }

    public static void studentMenu(Student student, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            printString("\nStudent Menu:\n" +
                        "1. View Available Courses\n" +
                        "2. Register for a Course\n" +
                        "3. View Registered Courses\n" +
                        "4. View Schedule\n" +
                        "5. Track Academic Progress\n" +
                        "6. Drop a Course\n" +
                        "7. Submit a Complaint\n" +
                        "8. View Complaint Status\n" +
                        "9. View Profile\n" +
                        "10. Update Password\n" +
                        "11. Submit Feedback for Completed Course\n" +
                        "12. Logout\n" +
                        "Enter your choice in Integer: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    student.viewAvailableCourses();
                    break;
                case 2:
                    student.registerCourse(scanner);
                    break;
                case 3:
                    student.viewRegisteredCourses();
                    break;
                case 4:
                    student.viewSchedule();
                    break;
                case 5:
                    student.calculateCGPA();
                    printString("Enter the semester to see GPA: ");
                    int sem = scanner.nextInt();
                    student.calculateGPA(sem);
                    break;
                case 6:
                    student.dropCourse(scanner);
                    break;
                case 7:
                    printString("Enter your complaint: ");
                    String description = scanner.nextLine();
                    student.submitComplaint(description);
                    DataStore.complaints.add(new Complaint(description));
                    printString("Complaint submitted successfully.");
                    break;
                case 8:
                    student.viewComplaintStatus();
                    break;
                case 9:
                    student.viewProfile();
                    break;
                case 10:
                    printString("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    student.updatePassword(newPassword);
                    break;
               
                case 11:
                    student.showGradedCourses();
                    printString("Enter the course code for feedback: ");
                    String courseCode = scanner.nextLine();
                    Course selectedCourse = null;

                    for (Course course : student.getgradedCourses()) {
                        if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                            selectedCourse = course;
                            break;
                        }
                    }

                    if (selectedCourse != null) {
                        printString("Enter numeric rating (1-5) or textual feedback: ");
                        String feedbackInput = scanner.nextLine();

                        try {
                            int rating = Integer.parseInt(feedbackInput);
                            if (rating >= 1 && rating <= 5) {
                                student.submitFeedback(selectedCourse, rating);
                            } else {
                                printString("Invalid rating. Please enter a number between 1-5.");
                            }
                        } catch (NumberFormatException e) {
                            student.submitFeedback(selectedCourse, feedbackInput);
                        }
                    } else {
                        printString("Course not found in your graded courses.");
                    }
                    break;

                case 12:
                    printString("Logging out...");
                    loggedIn = false;
                    break;
                default:
                    printString("Invalid option. Try again.");
            }
        }
    }
    

    public static void professorMenu(Professor professor, Scanner scanner) {
    boolean loggedIn = true;
    while (loggedIn) {
        printString("Professor Menu:\n" +
                    "1. View Profile\n" +
                    "2. View Taught Courses\n" +
                    "3. Update Course Details\n" +
                    "4. View Enrolled Students\n" +
                    "5. View Enrolled TAs\n" +
                    "6. Assign Grade\n" +
                    "7. View Feedback for Course\n" + 
                    "8. Update Password\n" +
                    "9. Logout\n" +
                    "Enter your choice in Integer: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                professor.viewProfile();
                break;
            case 2:
                professor.viewTaughtCourses();
                break;
            case 3:
                professor.viewTaughtCourses();
                printString("Enter course title to update: ");
                String courseTitle = scanner.nextLine();
                int index = 0;
                while (index < professor.getCoursesTaught().size()) {
                    Course course = professor.getCoursesTaught().get(index);
                    if (course.getTitle().equalsIgnoreCase(courseTitle)) {
                        professor.manageCourse(course);
                        break;
                    }
                    index++;
                }
                break;
            case 4:
                professor.viewTaughtCourses();
                printString("Enter course title to view students: ");
                String title = scanner.nextLine();
                index = 0;
                while (index < professor.getCoursesTaught().size()) {
                    Course course = professor.getCoursesTaught().get(index);
                    if (course.getTitle().equalsIgnoreCase(title)) {
                        professor.viewEnrolledStudents(course);
                        break;
                    }
                    index++;
                }
                break;
            case 5:professor.viewTaughtCourses();
                    printString("Enter course title to view students: ");
                    String titl = scanner.nextLine();
                    index = 0;
                    while (index < professor.getCoursesTaught().size()) {
                        Course course = professor.getCoursesTaught().get(index);
                        if (course.getTitle().equalsIgnoreCase(titl)) {
                            professor.viewEnrolledta(course);
                            break;
                        }
                        index++;
                    }
                    break;
            case 6:
                printString("Enter the semester of the course:");
                int sem = scanner.nextInt();
                professor.assignGrade(sem);
                break;
            case 7:

                professor.viewTaughtCourses();
                printString("Enter the course code to view feedback: ");
                String feedbackCourseCode = scanner.nextLine();
            
                
                Course feedbackCourse = null;
                for (Course course : professor.getCoursesTaught()) {
                    if (course.getCourseCode().equalsIgnoreCase(feedbackCourseCode)) {
                        feedbackCourse = course;
                        break;
                    }
                }
            
                if (feedbackCourse != null) {
                    professor.viewFeedback(feedbackCourse); 
                } else {
                    printString("Course not found or you do not teach this course.");
                }
                break;
            
            case 8:
                printString("Enter new password: ");
                String newPassword = scanner.nextLine();
                professor.updatePassword(newPassword);
                break;
            case 9:
                printString("Logging out...");
                loggedIn = false;
                break;
            default:
                printString("Invalid option. Try again.");
        }
    }
}

    
    
    public static void adminMenu(Admin admin, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            printString("Admin Menu:\n" +
                        "1. Add Course\n" +
                        "2. Remove Course\n" +
                        "3. Assign Professor to Course\n" +
                        "4. Assign TA to Course\n" +
                        "5. Manage Complaints\n" +
                        "6. Assign/Change Grade\n" +
                        "7. View Courses\n" +
                        "8. Set Courses Drop Deadline\n" +
                        "9. View Profile\n" +     
                        "10. Update Password\n" +  
                        "11. Logout\n" +
                        "Enter your choice in Integer: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    printString("Enter course code: ");
                    String code = scanner.nextLine();
                    printString("Enter course title: ");
                    String title = scanner.nextLine();
                    printString("Enter course credits: ");
                    int credits = scanner.nextInt();
                    printString("Enter course semester: ");
                    int semester = scanner.nextInt();
                    scanner.nextLine();
                    printString("Kindly follow the format: Day (e.g., Mon, Tue, Wed, etc.) and timing (e.g., 3:00PM-4:30PM).\n" +
            "Example: Mon 3:00PM-4:30PM or Wed 1:00PM-2:30PM\n");
                    printString("Enter course timings: ");
                    String timings = scanner.nextLine();
                    printString("Enter course location: ");
                    String location = scanner.nextLine();
                    printString("Enter course Enrollment Limit: ");
                    int enrollmentLimit = scanner.nextInt();
                    Course newCourse = new Course(code, title, credits, semester, timings, location, enrollmentLimit);
                    admin.addCourse(newCourse);
                    break;
                case 2:
                    printString("Enter course title to remove: ");
                    String removeTitle = scanner.nextLine();
                    int index = 0;
                    while (index < DataStore.courses.size()) {
                        Course course = DataStore.courses.get(index);
                        if (course.getTitle().equalsIgnoreCase(removeTitle)) {
                            admin.removeCourse(course);
                            break;
                        }
                        index++;
                    }
                    break;
                case 3:
                    
                    printString("Enter course Code: ");
                    String assignco= scanner.nextLine();
                    index = 0;
                    while (index < DataStore.courses.size()) {
                        Course course = DataStore.courses.get(index);
                        if (course.getCourseCode().equalsIgnoreCase(assignco)) {
                            printString("Enter professor email to assign: ");
                            String professorEmail = scanner.nextLine();
                            Professor professor = (Professor) DataStore.findUserByEmail(professorEmail);
                            if (professor != null) {
                                admin.assignProfessor(course, professor);
                            } else {
                                printString("Professor not found.");
                            }
                            break;
                        }
                        index++;
                    }
                    break;
                case 4:printString("Enter course Course Code: ");
                    String assigncode = scanner.nextLine();
                    index = 0;
                    while (index < DataStore.courses.size()) {
                        Course course = DataStore.courses.get(index);
                        if (course.getCourseCode().equalsIgnoreCase(assigncode)) {
                            printString("Enter TA email to assign: ");
                            String taEmail = scanner.nextLine();
                            TA ta = (TA) DataStore.findUserByEmail(taEmail);
                            if (ta != null) {
                                admin.assignta(course, ta);
                            } else {
                                printString("TA not found.");
                            }
                            break;
                        }
                        index++;
                    }
                        break;
                case 5:
                    printString("1. View Complaints\n" +
                                "2. Resolve Pending Complaints\n" +
                                "Enter your choice: ");
                    int complaintChoice = scanner.nextInt();
                    scanner.nextLine();
    
                    switch (complaintChoice) {
                        case 1:
                            printString("1. View Resolved Complaints\n" +
                                        "2. View Pending Complaints\n" +
                                        "3. View All Complaints\n" +
                                        "Enter your choice: ");
                            int viewChoice = scanner.nextInt();
                            scanner.nextLine();
    
                            switch (viewChoice) {
                                case 1:
                                    admin.printComplaints("Resolved");
                                    break;
                                case 2:
                                    admin.printComplaints("Pending");
                                    break;
                                case 3:
                                    admin.printComplaints("All");
                                    break;
                                default:
                                    printString("Invalid option. Returning to main menu.");
                            }
                            break;
                        case 2:
                            admin.handleComplaints();
                            break;
                        default:
                            printString("Invalid option. Returning to main menu.");
                    }
                    break;
                case 6:
                    admin.changeGrade();
                    break;
                case 7:
                    printString("1. View courses by student\n" +
                                "2. View all available courses\n" +
                                "Enter your choice: ");
                    int courseViewChoice = scanner.nextInt();
                    scanner.nextLine();
    
                    switch (courseViewChoice) {
                        case 1:
                            admin.viewCoursesByStudent();
                            break;
                        case 2:
                            admin.viewAllCourses();
                            break;
                        default:
                            printString("Invalid option. Returning to main menu.");
                    }
                    break;
                case 8:
                     admin.setDropDeadline(scanner);
                     break;
                case 9: 
                    admin.viewProfile();
                    break;
                case 10: 
                    printString("Enter new password: ");
                    String newPass = scanner.nextLine();
                    admin.updatePassword(newPass);
                    break;
                case 11:
                    printString("Logging out...");
                    loggedIn = false;
                    break;
                default:
                    printString("Invalid option. Try again.");
            }
        }
    }
    public static void TAMenu(TA ta, Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            printString("\nTA Menu:\n" +
                        "1. View Profile\n" +
                        "2. View Enrolled Students (for course where you are TA)\n" +
                        "3. Assign Grades (for course where you are TA)\n" +
                        "4. View Feedback (for the course where you are TA)\n" +
                        "5. Update Password\n" +
                        "6. Logout\n" +
                        "Enter your choice in Integer: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    ta.viewProfile();
                    break;
                case 2:
                    ta.viewEnrolledStudents(); 
                    break;
                case 3:
                    ta.assignGrade(); 
                    break;
                case 4:
                    ta.viewFeedback(); 
                    break;
                case 5:
                    printString("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    ta.updatePassword(newPassword);
                    break;
                case 6:
                    printString("Logging out...");
                    loggedIn = false;
                    break;
                default:
                    printString("Invalid option. Try again.");
            }
        }
    }
    
    


    public static void printString(String message) {
        System.out.println(message);
    }

    public static void printInt(int number) {
        System.out.println(number);
    }
}
