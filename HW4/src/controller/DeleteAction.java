package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.UserBean;
import formbean.IdForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class DeleteAction extends Action {
    private FormBeanFactory<IdForm> idFormFactory = FormBeanFactory.getInstance(IdForm.class);
    
    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public DeleteAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
        userDAO = model.getUserDAO();
    }

    public String getName() { return "delete.do"; }
    
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
            IdForm form = idFormFactory.create(request);
            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
                return "error.jsp";
            }
            
            favoriteDAO.delete(form.getIdAsInt());
            
            request.setAttribute("favorites", favoriteDAO.getFavorites(u.getUser_id()));
            return "managelist.jsp";

        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}

