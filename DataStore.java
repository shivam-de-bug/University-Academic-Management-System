import java.util.ArrayList;

public class DataStore {
    public static ArrayList<Student> students = new ArrayList<>();
    public static ArrayList<Professor> professors = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<TA> tas = new ArrayList<>();
    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Complaint> complaints = new ArrayList<>();
    public static ArrayList<ArrayList<Feedback<?>>> feedbacks = new ArrayList<>(); 
    public static String dropdeadline="02-10-2024";



    static {
        
        for (int i = 0; i < 100; i++) {
            feedbacks.add(new ArrayList<>());
        }

        admins.add(new Admin("admin", "1"));

        Course cs101 = new Course("CS101", "Introduction to Programming", 4, 1, "Mon 9:00AM-11:00AM", "Room 101", 50);
        Course cs102 = new Course("CS102", "Data Structures and Algorithms", 4, 2, "Tue 10:00AM-12:00AM", "Room 102", 50);
        Course cs201 = new Course("CS201", "Computer Architecture", 4, 3, "Wed 1:00PM-3:00PM", "Room 201", 50);
        Course cs202 = new Course("CS202", "Operating Systems", 4, 3, "Thu 3:00PM-4:00PM", "Room 202", 50);
        Course cs301 = new Course("CS301", "DM", 4, 3, "Fri 11:00AM-1:00PM", "Room 301", 50);
        Course cs302 = new Course("CS302", "AP", 4, 3, "Mon 2:00PM-4:00PM", "Room 302", 50);

        // Course cs401 = new Course("CS401", "Advanced Programming", 4, 4, "Mon 9:00AM-11:00AM", "Room 401", 50);
        // Course cs402 = new Course("CS402", "Database Systems", 4, 4, "Tue 10:00AM-12:00PM", "Room 402", 50);
        // Course cs403 = new Course("CS403", "Computer Networks", 4, 4, "Wed 11:00AM-1:00PM", "Room 403", 50);
        // Course cs404 = new Course("CS404", "Artificial Intelligence", 4, 4, "Thu 12:00PM-2:00PM", "Room 404", 50);
        // Course cs405 = new Course("CS405", "Software Engineering", 4, 4, "Fri 9:00AM-11:00AM", "Room 405", 50);
        // Course cs406 = new Course("CS406", "Machine Learning", 4, 4, "Mon 1:00PM-3:00PM", "Room 406", 50);
        // Course cs407 = new Course("CS407", "Distributed Systems", 4, 4, "Tue 2:00PM-4:00PM", "Room 407", 50);
        // Course cs408 = new Course("CS408", "Mobile Computing", 4, 4, "Wed 9:00AM-11:00AM", "Room 408", 50);
        // Course cs409 = new Course("CS409", "Cloud Computing", 4, 4, "Thu 1:00PM-3:00PM", "Room 409", 50);
        // Course cs410 = new Course("CS410", "Cryptography", 4, 4, "Fri 10:00AM-12:00PM", "Room 410", 50);
        // cs402.addPrerequisite(cs401);
        // Course cs411 = new Course("CS411", "Cyber Security", 4, 4, "Mon 3:00PM-5:00PM", "Room 411", 50);
        // Course cs412 = new Course("CS412", "Compiler Design", 4, 4, "Tue 11:00AM-1:00PM", "Room 412", 50);
        // Course cs413 = new Course("CS413", "Data Mining", 4, 4, "Wed 2:00PM-4:00PM", "Room 413", 50);
        // Course cs414 = new Course("CS414", "Big Data Analytics", 4, 4, "Thu 3:00PM-5:00PM", "Room 414", 50);
        // Course cs415 = new Course("CS415", "Natural Language Processing", 4, 4, "Fri 12:00PM-2:00PM", "Room 415", 50);
        // Course cs416 = new Course("CS416", "Human-Computer Interaction", 4, 4, "Mon 10:00AM-12:00PM", "Room 416", 50);
        // Course cs417 = new Course("CS417", "Digital Image Processing", 4, 4, "Tue 12:00PM-2:00PM", "Room 417", 50);
        // Course cs418 = new Course("CS418", "Multimedia Systems", 4, 4, "Wed 1:00PM-3:00PM", "Room 418", 50);
        // Course cs419 = new Course("CS419", "Robotics", 4, 4, "Thu 9:00AM-11:00AM", "Room 419", 50);
        // Course cs420 = new Course("CS420", "Augmented Reality", 4, 4, "Fri 11:00AM-1:00PM", "Room 420", 50);



        cs102.addPrerequisite(cs101);  
        cs202.addPrerequisite(cs201);  
        cs301.addPrerequisite(cs202); 
        cs302.addPrerequisite(cs102);

        

        courses.add(cs101);
        courses.add(cs102);
        courses.add(cs201);
        courses.add(cs202);
        courses.add(cs301);
        courses.add(cs302);
    }
    
    public static void addFeedback(int courseIndex, Feedback<?> feedback) {
        if (courseIndex >= 0 && courseIndex < feedbacks.size()) { 
            feedbacks.get(courseIndex).add(feedback);
        } else {
            System.out.println("Invalid course index!");
        }
    }
    
    public static ArrayList<Feedback<?>> getFeedbacks(int courseIndex) {
        if (courseIndex >= 0 && courseIndex < feedbacks.size()) { 
            return feedbacks.get(courseIndex);
        } else {
            System.out.println("Invalid course index!");
            return new ArrayList<>(); 
        }
    }
    public static void addStudent(Student student) {
        students.add(student);
    }
    public static void addTA(TA ta) {
        tas.add(ta);
    }
    public static void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public static void addAdmin(Admin admin) {
        admins.add(admin);
    }
    public static String getDropDeadline() {
        return dropdeadline;
    }

    public static User findUserByEmail(String email) {
        int index = 0;
        while (index < students.size()) {
            Student student = students.get(index);
            if (student.getEmail().equals(email)) {
                return student;
            }
            index++;
        }
        
        index = 0;
        while (index < professors.size()) {
            Professor professor = professors.get(index);
            if (professor.getEmail().equals(email)) {
                return professor;
            }
            index++;
        }
        
        index = 0;
        while (index < admins.size()) {
            Admin admin = admins.get(index);
            if (admin.getEmail().equals(email)) {
                return admin;
            }
            index++;
        }
        index=0;
        while (index < tas.size()) {
            TA ta = tas.get(index);
            if (ta.getEmail().equals(email)) {
                return ta;
            }
            index++;
        }
        
        return null;
    }
    public  ArrayList<Course> getCourse(){
        return courses ;
    }
   
    public static void printString(String message) {
        System.out.println(message);
    }

    public static void printInt(int number) {
        System.out.println(number);
    }
    
    
}
