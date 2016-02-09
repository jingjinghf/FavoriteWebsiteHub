package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
    private String emailAddress;
    private String password;
    private String action;
    
    public LoginForm(HttpServletRequest request) {
        emailAddress = sanitize(request.getParameter("emailAddress"));
        password = sanitize(request.getParameter("password"));
        action = sanitize(request.getParameter("button"));
    }
    
    public String getEmailAddress()  { return emailAddress; }
    public String getPassword()  { return password; }
    
    public void setUserName(String s) { emailAddress = trimAndConvert(s,"<>\"");  }
    public void setPassword(String s) { password = s.trim();                  }
    
    public boolean isPresent() {
        return !(action == null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (emailAddress == null || emailAddress.length() == 0) {
            errors.add("Email-address is required");
        }
        
        if (password == null || password.length() == 0) {
            errors.add("Password is required");
        }
        
        if (action != null && !action.equals("Login")) {
            errors.add("Action not exists");
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
