package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {
    private String url;
    private String comment;
    private String action;
    
    public FavoriteForm(HttpServletRequest request) {
        url = sanitize(request.getParameter("url"));
        comment = sanitize(request.getParameter("comment"));
        action = sanitize(request.getParameter("button"));
    }

    public String getUrl()      {return url;}
    public String getComment()  {return comment;}
    public String getAction()   { return action; }
    
    public void setUrl(String s)    { url = s;           }
    public void setComment(String s) {comment = s;       }
    public void setAction(String s) { action = s;        }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (action == null || action.length() == 0) {
            errors.add("Action is required");
        }

        if (url == null || url.length() == 0) {
            errors.add("URL is required");
        }
        
        if (errors.size() > 0) return errors;

        if (url.matches(".*[<>\"].*")) errors.add("url may not contain angle brackets or quotes");
        
        if (comment.matches(".*[<>\"].*")) errors.add("url may not contain angle brackets or quotes");
        
        if (!action.equals("Add Favorite")) errors.add("Invalid action");

        return errors;
    }
    
    private String sanitize(String s) {
        if (s != null) {
            return s.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\"", "&quot;").replace("\'", "&apos");
        }
        else return null;        
    }
}
