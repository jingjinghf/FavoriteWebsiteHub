package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.UserBean;
import formbean.ChangePasswordForm;

public class ChangePasswordAction extends Action {
    private FormBeanFactory<ChangePasswordForm> formBeanFactory = FormBeanFactory.getInstance(ChangePasswordForm.class);
    
    private UserDAO userDAO;

    public ChangePasswordAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() { return "changepassword.do"; }
    
    public String perform(HttpServletRequest request) {
        // Set up error list
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
            //get current user from session
            UserBean u = (UserBean) request.getSession().getAttribute("user");
            
            // Load the form parameters into a form bean
            ChangePasswordForm form = formBeanFactory.create(request);
            
            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "changepassword.jsp";
            }
    
            // Check for any validation errors
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "changepassword.jsp";
            }
    
            // Change the password
            userDAO.setPassword(u.getUser_id(), u.getEmailAddress(), form.getNewPassword());
    
            request.setAttribute("message","Password changed for " + u.getEmailAddress());
            return "success.jsp";
        } catch (RollbackException e) {
            errors.add(e.toString());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.toString());
            return "error.jsp";
        }
    }
}
