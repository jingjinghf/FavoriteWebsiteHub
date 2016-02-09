package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RegisterForm extends FormBean {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private String confirm ;
    
    public String getEmailAddress() {return sanitize(emailAddress);}
    public String getFirstName()    { return sanitize(firstName); }
    public String getLastName()     { return sanitize(lastName);  }
    public String getPassword()     { return sanitize(password);  }
    public String getConfirm()      { return sanitize(confirm);   }
    
    public void setEmailAddress(String s) {emailAddress = trimAndConvert(s, "<>\"");}
    public void setFirstName(String s) { firstName = trimAndConvert(s,"<>\"");  }
    public void setLastName(String s)  { lastName  = trimAndConvert(s,"<>\"");  }
    public void setPassword(String s)  { password  = s.trim();                  }
    public void setConfirm(String s)   { confirm   = s.trim();                  }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        
        if (emailAddress == null || emailAddress.length() == 0) {
            errors.add("Email-address is required");
        }

        if (firstName == null || firstName.length() == 0) {
            errors.add("First Name is required");
        }

        if (lastName == null || lastName.length() == 0) {
            errors.add("Last Name is required");
        }

        if (password == null || password.length() == 0) {
            errors.add("Password is required");
        }

        if (confirm == null || confirm.length() == 0) {
            errors.add("Confirm Password is required");
        }
        
        if (errors.size() > 0) {
            return errors;
        }
        
        if (!password.equals(confirm)) {
            errors.add("Passwords are not the same");
        }
        
        return errors;
    }
    
    private String sanitize(String s) {
        if (s != null) {
            return s.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\"", "&quot;").replace("\'", "&apos");
        }
        else return null;        
    }
}

