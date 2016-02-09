package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
    private UserDAO userDAO;
    private FavoriteDAO favoriteDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");
            
            ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
            
            userDAO  = new UserDAO(pool, "jingjinh_user");
            favoriteDAO = new FavoriteDAO(pool, "jingjinh_favorite");
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }
    
    public FavoriteDAO getFavoriteDAO()  { return favoriteDAO; }
    public UserDAO getUserDAO()  { return userDAO; }
}
