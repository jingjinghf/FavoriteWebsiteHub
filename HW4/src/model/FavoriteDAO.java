package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FavoriteBean;
import databean.UserBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {
    public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(FavoriteBean.class, tableName, cp);
    }
    
    public FavoriteBean[] getFavorites(int id) throws RollbackException  {
        // Calls GenericDAO's match() method.
        FavoriteBean[] flist = match(MatchArg.equals("user_id", id));
        
        // Sort the list in position order
        java.util.Arrays.sort(flist, new Comparator<FavoriteBean>() {
            public int compare(FavoriteBean f1, FavoriteBean f2) {
                return f1.getFavorite_id() - f2.getFavorite_id();
            }
        });
        
        return flist;
    }
    
    public void add(FavoriteBean favorite) throws RollbackException {
        try {
            Transaction.begin();

            // Create a new FavoriteBean in the database with the next id number
            createAutoIncrement(favorite);

            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
    
    public void update(int fid) throws RollbackException {
        try {
            Transaction.begin();
            FavoriteBean favorite = read(fid);

            if (favorite == null) {
                throw new RollbackException("Favorite Site id=" + fid + " no longer exists");
            }

            favorite.setClick(favorite.getClick() + 1);

            update(favorite);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
    
    public boolean isEmpty() throws RollbackException {
        FavoriteBean[] flist = match();
        if (flist.length == 0) return true;
        else return false;
    }
}