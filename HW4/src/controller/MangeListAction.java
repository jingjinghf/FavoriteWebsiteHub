package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.FavoriteDAO;
import databean.UserBean;
import model.Model;
import model.UserDAO;

public class MangeListAction extends Action {
    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public MangeListAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
        userDAO = model.getUserDAO();
    }

    public String getName() { return "managelist.do"; }
    
    public String perform(HttpServletRequest request) {
        UserBean u = (UserBean) request.getSession().getAttribute("user");
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
            request.setAttribute("favorites",  favoriteDAO.getFavorites(u.getUser_id()));
            return ("managelist.jsp");
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}

