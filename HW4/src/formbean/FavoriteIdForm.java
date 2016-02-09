package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class FavoriteIdForm extends FormBean {
    private String fid = "";
    
    public FavoriteIdForm(HttpServletRequest request) {
        fid = request.getParameter("fid");
    }
    
    public String getFavorite_id()  { return fid; }
    public int getFavorite_idAsInt() { return Integer.parseInt(fid); }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        
        try {
            Integer.parseInt(fid);
        } catch (Exception e) {
            errors.add("favorite id is not vaid");
        }

        if (fid == null || fid.length() == 0) {
            errors.add("uid is required");
        }
        
        return errors;
    }
}
