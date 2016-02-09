package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("user_id")
public class UserBean {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private int user_id;
    
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

