import java.util.ArrayList;
import java.util.Scanner;
public class TA extends Student {
    private Course[] Courseta = new Course[1];
    
    public TA(String email, String password) {
        super(email, password);
    }
    private Course courseassit=Courseta[0];
    Scanner scanner = new Scanner(System.in);
    @Override
    public String getRole() {
        return "Teaching Assistant";
    }
    public void setcourse(Course Course){
        
        Courseta[0]= Course;
        courseassit=Courseta[0];
    }
    public Course gettacourse() {
        if(courseassit==null){
            printString("No course assigned to you.\n");
            return null;
        }
        return courseassit;
    }
    @Override
    public void viewProfile() {
        printString("Student Profile: \n" +
                     "Email: " + getEmail() + "\n" +
                     "Role: " + getRole() + "\n" +"Course you assist: "+gettacourse().getTitle());   
    }
    public void viewEnrolledStudents() {
        Course course=gettacourse();
        printString("Students enrolled in " + course.getTitle() + ":");
        int i = 0;
        while (i < course.getEnrolledStudents().size()) {
            Student student = course.getEnrolledStudents().get(i);
            printString((i + 1) + ". Email: " + student.getEmail() +
                "\n");
            i++;
        }
    }
    public void assignGrade() {
        
        

        Course selectedCourse = gettacourse();

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

        Grade newGrade = new Grade(selectedCourse, grade, selectedStudent.getSemester());
        selectedStudent.addGrade(newGrade);
        selectedStudent.addgradededcourse(selectedCourse);
        selectedStudent.checkAndUpgradeSemester();
        printString("Grade assigned successfully.");
       
    }
    public void viewFeedback() {
        Course course=gettacourse();
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
    @Override
    public void updatePassword(String newPassword) {
        setPassword(newPassword);
        printString("Password updated successfully for professor: " + getEmail());
    }

    private void printString(String message) {
        System.out.println(message);
    }
}
