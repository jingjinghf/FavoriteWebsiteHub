package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.FavoriteDAO;
import databean.FavoriteBean;
import databean.UserBean;
import formbean.UserForm;
import model.Model;
import model.UserDAO;

public class ViewFavoriteAction extends Action {
    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public ViewFavoriteAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
        userDAO = model.getUserDAO();
    }

    public String getName() { return "viewfavorite.do"; }
    
    public String perform(HttpServletRequest request) {
        //UserBean u = (UserBean) request.getAttribute("user_id");
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
            UserBean[] userList = userDAO.getUsers();
            request.setAttribute("userList", userList);
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            System.out.println("1");
            return "error.jsp";
        }
        
        UserForm form = new UserForm(request);
        errors.addAll(form.getValidationErrors());
        if (errors.size() != 0) {
            return "viewfavorite.jsp";
        }
        
        String user_id = form.getUser_id();
        int user_idInt = form.getUser_idAsInt();
        
        
        if (user_id == null || user_id.length() == 0) {
            errors.add("User must be specified");
            System.out.println("2");
            return "error.jsp";
        }
        
        UserBean user;
        try {
            user = userDAO.read(user_idInt);
        } catch (RollbackException e1) {
            // TODO Auto-generated catch block
            System.out.println("3");

            return "error.jsp";
        }
        
        if (user == null) {
            errors.add("No such User_id: " + user_id);
            return "viewfavorite.jsp";
        }        

        try {
            FavoriteBean[] flist = favoriteDAO.getFavorites(user_idInt);
            System.out.println(flist);
        } catch (RollbackException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        try {
            request.setAttribute("favorites",  favoriteDAO.getFavorites(user_idInt));
            return ("viewfavorite.jsp");
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}

