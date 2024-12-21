import java.util.Scanner;

public class Interface {

    

public interface managestucourse {
        void viewAvailableCourses();
        void registerCourse(Scanner scanner);
        void dropCourse(Scanner scanner);
        void viewRegisteredCourses();
        void viewSchedule();
        void calculateGPA(int semester);
        void calculateCGPA();
}
public interface Studentcomplain {
    void submitComplaint(String description);
    void viewComplaintStatus();
}
public interface Proffcourse {
    void viewTaughtCourses();
    void manageCourse(Course course);
    void assignGrade(int currentSemester);
}
public interface adminmanage {
    void addCourse(Course course);   
     void removeCourse(Course course) ;
     void assignProfessor(Course course, Professor professor);
     void changeGrade();
     void handleComplaints();

}



    
}
