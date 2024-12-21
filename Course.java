import java.util.ArrayList;

public class Course {
    private String courseCode;
    private String title;
    private Professor professor;
    private int credits;
    private String syllabus;
    private int semester;
    private String officeHours;
    private ArrayList<Course> prerequisites = new ArrayList<>();
    private String timings;
    private String location;
    private int enrollmentLimit;
    private ArrayList<Student> enrolledStudents = new ArrayList<>();
    private ArrayList<TA> enrolledta = new ArrayList<>();

    public Course(String courseCode, String title, int credits, int semester, String timings, String location, int enrollmentLimit, String syllabus, String officehour) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.semester = semester;
        this.syllabus = syllabus;
        this.timings = timings;
        this.location = location;
        this.enrollmentLimit = enrollmentLimit;
        this.officeHours=officehour;
        
    }
    public Course(String courseCode, String title, int credits, int semester, String timings, String location, int enrollmentLimit) {
        this(courseCode, title, credits, semester, timings, location, enrollmentLimit, " "," "); 
    }
    public Course(String courseCode, String title, int credits, int semester, String location, int enrollmentLimit) {
        this(courseCode, title, credits, semester, " ", location, enrollmentLimit, " "," "); 
    }
    public Course(String title,String courseCode) {
        this(courseCode, title, 0, 0, " ", " ", 0, " "," "); 
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public Professor getProfessor() {
        return professor;
    }

    public int getCredits() {
        return credits;
    }

    public int getSemester() {
        return semester;
    }

    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }

    public String getTimings() {
        return timings;
    }

    public String getLocation() {
        return location;
    }
    public String getSyllabus() {
        return syllabus;
    }
    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public void setTimings(String timing){
        this.timings=timing;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public void addTA(TA ta) {
        enrolledta.add(ta);
    }
    public void removeTA(TA ta) {
        enrolledta.remove(ta);
    }

    
    public void setSyllabus(String Syllabus) {
        this.syllabus = Syllabus;
    }
    public void addPrerequisite(Course course) {
        prerequisites.add(course);
    }

    public boolean isFull() {
        return enrolledStudents.size() >= enrollmentLimit;
    }
    public void setEnrollmentLimit(int limit) {
        this.enrollmentLimit=  limit;
    }
    public void enrollStudent(Student student) {
        if (!isFull()) {
            enrolledStudents.add(student);
        }
    }
    public void removePrerequisite(Course course){
        prerequisites.remove(course);
        
    }
    public void setOfficeHours( String officeHous){
        this.officeHours=officeHous;
    }
    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
    public ArrayList<TA> getEnrolledTas() {
        return enrolledta;
    }

   
    @Override
    public String toString() {
    String profEmail = (professor != null) ? professor.getEmail() : "TBA";
    String prereqCodes = "";
    int i = 0;
    while (i < prerequisites.size()) {
        Course prereq = prerequisites.get(i);
        prereqCodes += prereq.getCourseCode() + " ";
        i++;
    }
    return "Course Code: " + courseCode + ", Title: " + title + ", Professor: " + profEmail + 
           ", Credits: " + credits + ", Semester: " + semester + 
           ", Prerequisites: [" + prereqCodes.trim() + "], Timings: " + timings + 
           ", Location: " + location + ", Enrollment Limit: " + enrollmentLimit + 
           ", Enrolled: " + enrolledStudents.size();
    }

}
