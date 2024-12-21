

public abstract class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public abstract String getRole();

    public abstract void viewProfile();

    public abstract void updatePassword(String newPassword);

    public String getEmail() {
        return email;
    }
    public void setPassword(String pass){
        this.password=pass;
    }

    public String getPassword() {
        return password;
    }
}

