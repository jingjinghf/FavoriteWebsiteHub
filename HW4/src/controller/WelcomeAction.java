package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import databean.UserBean;
import model.Model;
import model.UserDAO;

public class WelcomeAction extends Action {
    private UserDAO userDAO;

    public WelcomeAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() { return "welcome.do"; }
    
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
        
        return "guide.jsp";
    }
}

