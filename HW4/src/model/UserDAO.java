package model;

import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FavoriteBean;
import databean.UserBean;

public class UserDAO extends GenericDAO<UserBean> {
    public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(UserBean.class, tableName, cp);
    }
    
    public UserBean[] getUsers(int id) throws RollbackException  {
        // Calls GenericDAO's match() method.
        UserBean[] ulist = match(MatchArg.equals("user_id", String.valueOf(id)));
        
        return ulist;
    }
    
    public UserBean[] readUserByEmail(String email) throws RollbackException{
       
        UserBean[] user = match(MatchArg.equals("emailAddress", email));

        return user;
    }
    
    public UserBean[] getUsers() throws RollbackException  {
        // Calls GenericDAO's match() method.
        UserBean[] ulist = match();
        
        // Sort the list in position order
        java.util.Arrays.sort(ulist, new Comparator<UserBean>() {
            public int compare(UserBean u1, UserBean u2) {
                return u1.getUser_id() - u2.getUser_id();
            }
        });
        
        return ulist;
    }
    
    public void setPassword(int user_id, String emailAddress, String password) throws RollbackException {
        try {
            Transaction.begin();
            UserBean dbUser = read(user_id);

            if (dbUser == null) {
                throw new RollbackException("User " + emailAddress + " no longer exists");
            }
            

            dbUser.setPassword(password);

            update(dbUser);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
    
    public boolean isExist(String email) throws RollbackException {
        UserBean[] ulist = match(MatchArg.equals("emailAddress", email));
        if (ulist.length != 0) return true;
        return false;
    }
    
    public boolean isEmpty() throws RollbackException {
        UserBean[] ulist = match();
        if (ulist.length == 0) return true;
        else return false;
    }
}