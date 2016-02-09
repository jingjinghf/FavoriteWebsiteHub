package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;
import model.FavoriteDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FavoriteBean;
import databean.UserBean;
import formbean.FavoriteIdForm;
import formbean.IdForm;
import formbean.UserForm;

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested photo.  If there was no photo originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his photos.
 */
public class VisitorClickAction extends Action {  
    private FormBeanFactory<IdForm> idFormFactory = FormBeanFactory.getInstance(IdForm.class);
    private UserDAO userDAO;
    private FavoriteDAO favoriteDAO;

    public VisitorClickAction(Model model) {
        userDAO = model.getUserDAO();
        favoriteDAO = model.getFavoriteDAO();
    }

    public String getName() { return "visitorclick.do"; }
    
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
        
        FavoriteIdForm form = new FavoriteIdForm(request);
        String favorite_id = form.getFavorite_id();
        int favorite_idInt = form.getFavorite_idAsInt();
        errors.addAll(form.getValidationErrors());
        if (errors.size() > 0) {
            return "error.jsp";
        }
        
        try {          
            FavoriteBean favorite = favoriteDAO.read(favorite_idInt);
            
            if (favorite == null) {
                errors.add("url not found");
                return "viewfavorite.jsp";
            }
            
            //update click count
            favoriteDAO.update(favorite.getFavorite_id());
            
            return "url:" + favorite.getUrl();

        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}


