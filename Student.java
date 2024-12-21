import java.util.ArrayList;

import java.util.Scanner;

public class Student extends User implements Interface.managestucourse, Interface.Studentcomplain{
    private ArrayList<Course> registeredCourses = new ArrayList<>();
    private int semester;
    private ArrayList<Grade> grades = new ArrayList<>();
    public  ArrayList<Course> gradedCourses = new ArrayList<>();
    private ArrayList<String> complai = new ArrayList<>();
    private int totalCredits;

    public Student(String email, String password) {
        super(email, password);
        this.semester = 1;
        this.totalCredits = 0;
    }
    

    private void printString(String message) {
        System.out.println(message);
    }
    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void viewProfile() {
        printString("Student Profile: \n" +
                     "Email: " + getEmail() + "\n" +
                     "Semester: " + semester + "\n" +
                     "Role: " + getRole() + "\n");
       

        if (registeredCourses.isEmpty()) {
            printString("No courses registered yet.");
        } else {
            printString("Registered Courses:");
            for (Course course : registeredCourses) {
                printString(" - " + course.getTitle());
            }
        }
    }

    @Override
    public void updatePassword(String newPassword) {
        setPassword(newPassword);
        System.out.println("Student password updated successfully!");
    }
    
    @Override
    public void viewAvailableCourses() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available courses for Semester ").append(semester).append(":\n");
        int index = 0;
        while (index < DataStore.courses.size()) {
            Course course = DataStore.courses.get(index);
            if (course.getSemester() <= semester && !registeredCourses.contains(course) ) {
                
                sb.append(course).append("\n");
            }
            index++;
        }
        printString(sb.toString());
    }


    @Override
public void registerCourse(Scanner scanner) {
    int sem = getSemester();
    
    int currCredits = 0;
    ArrayList<Course> ungradedCourses = new ArrayList<>();

    for (Course c : registeredCourses) {
        if (c.getSemester() == sem && !gradedCourses.contains(c)) {
            ungradedCourses.add(c);
            currCredits += c.getCredits();
        }
    }

    if (currCredits >= 20) {
        printString("Cannot register for additional courses. You have reached the credit limit of 20 for the current semester.");
        return;
    }

    int remCredits = 20 - currCredits;

    StringBuilder sb = new StringBuilder();
    sb.append("Available courses for Semester ").append(sem).append(":\n");
    ArrayList<Course> availCourses = new ArrayList<>();

    for (Course c : DataStore.courses) {
        if (c.getSemester() == sem && !registeredCourses.contains(c) && !gradedCourses.contains(c) && c.getCredits() <= remCredits) {
            availCourses.add(c);
            sb.append(c).append("\n\n");
        }
    }

    if (availCourses.isEmpty()) {
        printString("No available courses for this semester.");
        return;
    }

    printString(sb.toString());

    printString("Enter course code to register: ");
    String code = scanner.nextLine();
    Course selCourse = null;

    for (Course c : availCourses) {
        if (c.getCourseCode().equalsIgnoreCase(code)) {
            selCourse = c;
            break;
        }
    }

    if (selCourse == null) {
        printString("Course not found.");
        return;
    }
    try{
    if(selCourse.isFull()){
        throw new CourseFullException("The course is full. You cannot register for this course.");
    }
    boolean prereqMet = true;
    sb = new StringBuilder();
    int index = 0;
    while (index < selCourse.getPrerequisites().size()) {
        Course prereq = selCourse.getPrerequisites().get(index);
        boolean completed = false;
        int gradeIndex = 0;
        while (gradeIndex < grades.size()) {
            Grade grade = grades.get(gradeIndex);
            if (grade.getCourse().equals(prereq)) {
                completed = true;
                break;
            }
            gradeIndex++;
        }
        if (!completed) {
            prereqMet = false;
            sb.append("Missing prerequisite: ").append(prereq.getCourseCode()).append(" - ").append(prereq.getTitle()).append("\n");
        }
        index++;
    }

    if (!prereqMet) {
        printString("Cannot register for the course due to missing prerequisites.");
        printString(sb.toString());
        return;
    }

    registeredCourses.add(selCourse);
    selCourse.enrollStudent(this);
    totalCredits += selCourse.getCredits();
    printString("Successfully registered for: " + selCourse.getTitle());}

    catch (CourseFullException e) {
        printString(e.getMessage());
    }
}

    
    
    
    @Override
    public void viewRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            printString("No courses registered.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Registered courses:\n");
        int index = 0;
        while (index < registeredCourses.size()) {
            Course course = registeredCourses.get(index);
            sb.append(course).append("\n");
            index++;
        }
        printString(sb.toString());
    }
   
    @Override
    public void submitComplaint(String description) {
        DataStore.complaints.add(new Complaint(description)); 
        complai.add(description); 
        printString("Complaint submitted: " + description);
    }
    
    @Override
    public void viewComplaintStatus() {
        if (complai.isEmpty()) {
            printString("No complaints submitted.");
            return;
        }
    
        StringBuilder sb = new StringBuilder();
        sb.append("Your Complaints:\n");

        for (String description : complai) {
        boolean found = false;
        for (Complaint complaint : DataStore.complaints) {
            if (complaint.getDescription().equals(description)) {
                sb.append(complaint).append("\n");
                found = true;
                break;  
            }
        }
        if (!found) {
            sb.append("Complaint not found: ").append(description).append("\n");
        }
    }

    printString(sb.toString());
    }
    public static int comparedate(String curdate, String deadline) {
        String[] c = curdate.split("-");
        String[] d = deadline.split("-");
        
        int cd = Integer.parseInt(c[0]);
        int cm = Integer.parseInt(c[1]);
        int cy = Integer.parseInt(c[2]);
        
        int dd = Integer.parseInt(d[0]);
        int dm = Integer.parseInt(d[1]);
        int dy = Integer.parseInt(d[2]);
        
        if (cy != dy) return cy - dy;
        if (cm != dm) return cm - dm;
        return cd - dd;
    }
    
    @Override
    public void dropCourse(Scanner scanner) {
    if (registeredCourses.isEmpty()) {
        printString("No courses to drop.");
        return;
    }
    printString("Deadline to drop the course :"+ DataStore.getDropDeadline()+("\n"));
    printString("Enter the current date (DD-MM-YYYY): ");
    String date = scanner.nextLine();  
    StringBuilder sb = new StringBuilder();
    try {
        
        if (comparedate(date, DataStore.dropdeadline) > 0) {
            throw new DropDeadlinePassedException("The course drop deadline has passed. You cannot drop courses anymore.");
        }
    } catch (DropDeadlinePassedException e) {
        printString(e.getMessage());
        return;
    }

    sb.append("Registered Courses (which is not Graded):\n");
    int index = 0;
    ArrayList<Course> ungradedCourses = new ArrayList<>();

   
    while (index < registeredCourses.size()) {
        Course course = registeredCourses.get(index);
        if (!gradedCourses.contains(course)) {
            sb.append(course).append("\n");
            ungradedCourses.add(course);  
        }
        index++;
    }

    if (ungradedCourses.isEmpty()) {
        printString("No ungraded courses available to drop.");
        return;
    }

    printString(sb.toString());
    printString("Enter course code to drop: ");
    String courseCode = scanner.nextLine();
    Course selectedCourse = null;

    for (Course course : ungradedCourses) {
        if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
            selectedCourse = course;
            break;
        }
    }

    if (selectedCourse == null) {
        printString("Course not found in your ungraded registered courses.");
        return;
    }

    registeredCourses.remove(selectedCourse);
    selectedCourse.dropStudent(this);
    totalCredits -= selectedCourse.getCredits();
    printString("Successfully dropped: " + selectedCourse.getTitle());
}

    @Override
    public void viewSchedule() {
        if (registeredCourses.isEmpty()) {
            printString("No courses registered.");
            return;
        }
    
        StringBuilder sb = new StringBuilder();
        sb.append("Weekly Schedule:\n");
        int index = 0;
        while (index < registeredCourses.size()) {
            Course course = registeredCourses.get(index);
            
            // Only show courses that are registered but not yet graded
            if (!gradedCourses.contains(course)) {
                sb.append("Course: ").append(course.getTitle())
                  .append(" | Timings and Days: ").append(course.getTimings())
                  .append(" | Location: ").append(course.getLocation())
                  .append(" | Professor: ").append(course.getProfessor() != null ? course.getProfessor().getEmail() : "TBA")
                  .append("\n");
            }
            
            index++;
        }
    
        printString(sb.toString());
    }
    

    double cgpa = 0, gpa = 0;
    @Override
    public void calculateGPA(int semester) {
        double totalPoints = 0.0;
        int totalCredits = 0;

        int index = 0;
        while (index < grades.size()) {
            Grade grade = grades.get(index);
            if (grade.getSemester() == semester) {
                double points = gradeToPoints(grade.getGrade()) * grade.getCourse().getCredits();
                totalPoints += points;
                totalCredits += grade.getCourse().getCredits();
            }
            index++;
        }

        if (totalCredits == 0) {
            printString("No completed courses in semester " + semester + " to calculate GPA.");
            return;
        }

        gpa = totalPoints / totalCredits;
        printString(String.format("GPA for Semester %d: %.2f", semester, gpa));
    }
    @Override
    public void calculateCGPA() {
        double totalPoints = 0.0;
        int totalCredits = 0;

        int index = 0;
        while (index < grades.size()) {
            Grade grade = grades.get(index);
            double points = gradeToPoints(grade.getGrade()) * grade.getCourse().getCredits();
            totalPoints += points;
            totalCredits += grade.getCourse().getCredits();
            index++;
        }

        if (totalCredits == 0) {
            printString("No completed courses to calculate CGPA.");
            return;
        }

        cgpa = totalPoints / totalCredits;
        printString(String.format("CGPA: %.2f", cgpa));
    }
    
    private double gradeToPoints(String grade) {
        switch (grade.toUpperCase()) {
            case "A":
                return 10.0;
            case "A-":
                return 9.0;
            case "B":
                return 8.0;
            case "B-":
                return 7.0;
            case "C":
                return 6.0;
            case "C-":
                return 5.0;
            case "D":
                return 4.0;
            case "F":
                return 0.0;
            default:
                return -1;
        }
    }
    
    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }
    public ArrayList<Course> getgradedCourses(){
        return gradedCourses;
    }
    public void showGradedCourses() {
        if (gradedCourses.isEmpty()) {
            System.out.println("No graded courses available.");
            return;
        }
        for (Course course : gradedCourses) {
            System.out.println(course); 
        }
    }
    public void addgradededcourse(Course course){
        gradedCourses.add(course);
    }
    public int getSemester() {
        return semester;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }
    

    public void checkAndUpgradeSemester() {
       
        boolean allCoursesGraded = true;
    
        for (Course course : registeredCourses) {
            if (!gradedCourses.contains(course)) {
                allCoursesGraded = false;
                break;
            }
        }
    
        if (allCoursesGraded) {
            semester++;
            totalCredits = 0;
        } 
    }
    

    public void submitFeedback(Course course, Object feedbackData) {
        if (gradedCourses.contains(course)) {
            Feedback<Object> feedback = new Feedback<>(feedbackData);
            int courseIndex = (DataStore.courses).indexOf(course);
    
           
            if (courseIndex != -1) {
                DataStore.addFeedback(courseIndex, feedback);
                System.out.println("Feedback for " + course.getTitle() + " submitted successfully.");
            } else {
                System.out.println(" Course not found in the data store.");
            }
        } else {
            System.out.println("You cannot submit feedback for a course you haven't completed.");
        }
    }
    
}

  
    
    
    

    

