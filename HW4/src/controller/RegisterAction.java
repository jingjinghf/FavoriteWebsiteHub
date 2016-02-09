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
import formbean.RegisterForm;

/*
 * Processes the parameters from the form in register.jsp.
 * If successful:
 *   (1) creates a new User bean
 *   (2) sets the "user" session attribute to the new User bean
 *   (3) redirects to view the originally requested photo.
 * If there was no photo originally requested to be viewed
 * (as specified by the "redirect" hidden form value),
 * just redirect to manage.do to allow the user to add some
 * photos.
 */
public class RegisterAction extends Action {
    private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

    private UserDAO userDAO;
    
    public RegisterAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() { return "register.do"; }

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
            RegisterForm form = formBeanFactory.create(request);
            request.setAttribute("form",form);
            
            System.out.println("in register");
    
            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "register.jsp";
            }
            
            if (userDAO.isExist(form.getEmailAddress())) {
                errors.add("Email-address already exists");
            }
    
            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            
            if (errors.size() != 0) {
                return "register.jsp";
            }

            // Create the user bean
            UserBean user = new UserBean();
            user.setEmailAddress(form.getEmailAddress());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setPassword(form.getPassword());

            userDAO.create(user);
            UserBean[] cUsers = userDAO.readUserByEmail(form.getEmailAddress());
            UserBean cUser = cUsers[0];
            
            HttpSession session = request.getSession();
            session.setAttribute("user",cUser);
            
            try {
                UserBean[] userList = userDAO.getUsers();
                request.setAttribute("userList", userList);
            } catch (RollbackException e) {
                errors.add(e.getMessage());
                return "error.jsp";
            }
            
            return "guide.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "register.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "register.jsp";
        }
    }
}

