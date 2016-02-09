package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String uid = "";
	
	public UserForm(HttpServletRequest request) {
	    uid = request.getParameter("uid");
	}
	
	public String getUser_id()  { return uid; }
	public int getUser_idAsInt() { return Integer.parseInt(uid); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		try {
		    Integer.parseInt(uid);
		} catch (Exception e) {
		    errors.add("uid is not vaid");
		}

		if (uid == null || uid.length() == 0) {
			errors.add("uid is required");
		}
		
		return errors;
	}
}
