package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.FavoriteForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class AddAction extends Action {
    //private FormBeanFactory<FavoriteForm>  favoriteFormFactory  = FormBeanFactory.getInstance(FavoriteForm.class);
    
    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public AddAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
        userDAO = model.getUserDAO();
    }

    public String getName() { return "add.do"; }
    
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
            // Fetch the items now, so that in case there is no form or there are errors
            // We can just dispatch to the JSP to show the item list (and any errors)
            UserBean u = (UserBean) request.getSession().getAttribute("user");
            request.setAttribute("favorites", favoriteDAO.getFavorites(u.getUser_id()));
            
            FavoriteForm form = new FavoriteForm(request);
            request.setAttribute("form", form);

            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
                return "managelist.jsp";
            }
            
            FavoriteBean bean = new FavoriteBean();
            bean.setUrl(form.getUrl());
            bean.setComment(form.getComment());
            bean.setClick(0);
            bean.setUser_id(u.getUser_id());
            
            System.out.println(u.getUser_id());

            if (form.getAction().equals("Add Favorite")) {
                favoriteDAO.add(bean);
            } else {
                errors.add("Invalid action: " + form.getAction());
            }

            // Fetch the items again, since we modified the list
            request.setAttribute("favorites", favoriteDAO.getFavorites(u.getUser_id()));
            
            return "managelist.do";

        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
