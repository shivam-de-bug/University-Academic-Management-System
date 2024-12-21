public class Grade {
    private Course course;
    private String grade;
    private int semester;  

    public Grade(Course course, String grade, int semester) {
        this.course = course;
        this.grade = grade;
        this.semester = semester;
    }

   

    public Course getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }
}
