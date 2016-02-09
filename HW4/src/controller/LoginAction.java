package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.UserBean;
import formbean.LoginForm;

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested photo.  If there was no photo originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his photos.
 */
public class LoginAction extends Action {
    
    private UserDAO userDAO;

    public LoginAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() { return "login.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
            UserBean[] userList = userDAO.getUsers();
            request.setAttribute("userList", userList);
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
        
        try {
            LoginForm form = new LoginForm(request);
            request.setAttribute("form",form);

            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                System.out.println("a");
                return "login.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                System.out.println("b");
                return "login.jsp";
            }

            // Look up the user by emailAddress
            UserBean[] users = userDAO.readUserByEmail(form.getEmailAddress());
            
            
            if (users.length == 0) {               
                errors.add("Email-address not found");
                return "login.jsp";
            }
            
            UserBean user = users[0];

            // Check the password
            if (!user.checkPassword(form.getPassword())) {
                errors.add("Incorrect password");
                return "login.jsp";
            }
    
            // Attach (this copy of) the user bean to the session
            HttpSession session = request.getSession();
            session.setAttribute("user",user);

            return "guide.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}

