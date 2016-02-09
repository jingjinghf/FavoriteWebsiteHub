package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databean.UserBean;
import databean.FavoriteBean;
import model.Model;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        
        /*Actions goes here*/
        Action.add(new AddAction(model));
        Action.add(new ChangePasswordAction(model));
        Action.add(new MangeListAction(model));
        Action.add(new RegisterAction(model));
        Action.add(new UserClickAction(model));
        Action.add(new DeleteAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new VisitorClickAction(model));
        Action.add(new ViewFavoriteAction(model));
        
        //initial the table
        try {
            if (model.getUserDAO().isEmpty() && model.getFavoriteDAO().isEmpty()) {
                UserBean user1 = new UserBean();
                user1.setEmailAddress("president@whitehouse.gov");
                user1.setFirstName("Barack");
                user1.setLastName("Obama");
                user1.setPassword("1234");
                
                UserBean user2 = new UserBean();
                user2.setEmailAddress("jingjinh@andrew.cmu.edu");
                user2.setFirstName("Jingjing");
                user2.setLastName("Huangfu");
                user2.setPassword("1234");
                
                UserBean user3 = new UserBean();
                user3.setEmailAddress("eppinger@cmu.edu");
                user3.setFirstName("Jeffrey");
                user3.setLastName("Eppinger");
                user3.setPassword("1234");
                
                model.getUserDAO().create(user1);
                model.getUserDAO().create(user2);
                model.getUserDAO().create(user3);
                
                FavoriteBean f11 = new FavoriteBean();
                f11.setUser_id(1);
                f11.setUrl("www.whitehouse.gov");
                f11.setComment("white house official website.");
                f11.setClick(0);
                
                FavoriteBean f12 = new FavoriteBean();
                f12.setUser_id(1);
                f12.setUrl("www.google.com");
                f12.setComment("search");
                f12.setClick(0);
                
                FavoriteBean f13 = new FavoriteBean();
                f13.setUser_id(1);
                f13.setUrl("www.cnn.com");
                f13.setComment("news website");
                f13.setClick(0);
                
                FavoriteBean f14 = new FavoriteBean();
                f14.setUser_id(1);
                f14.setUrl("www.nba.com");
                f14.setComment("basketball");
                f14.setClick(0);
                
                FavoriteBean f21 = new FavoriteBean();
                f21.setUser_id(2);
                f21.setUrl("www.amazon.com");
                f21.setComment("online shopping");
                f21.setClick(0);
                
                FavoriteBean f22 = new FavoriteBean();
                f22.setUser_id(2);
                f22.setUrl("www.facebook.com");
                f22.setComment("socail network");
                f22.setClick(0);
                
                FavoriteBean f23 = new FavoriteBean();
                f23.setUser_id(2);
                f23.setUrl("www.cmu.edu/blackboard");
                f23.setComment("course blackboard");
                f23.setClick(0);
                
                FavoriteBean f24 = new FavoriteBean();
                f24.setUser_id(2);
                f24.setUrl("www.apple.com");
                f24.setComment("apple's new trend");
                f24.setClick(0);
                
                FavoriteBean f31 = new FavoriteBean();
                f31.setUser_id(3);
                f31.setUrl("www.cmu.com");
                f31.setComment("education");
                f31.setClick(0);
                
                FavoriteBean f32 = new FavoriteBean();
                f32.setUser_id(3);
                f32.setUrl("www.google.com");
                f32.setComment("search");
                f32.setClick(0);
                
                FavoriteBean f33 = new FavoriteBean();
                f33.setUser_id(3);
                f33.setUrl("www.library.cmu.edu");
                f33.setComment("cmu library");
                f33.setClick(0);
                
                FavoriteBean f34 = new FavoriteBean();
                f34.setUser_id(3);
                f34.setUrl("www.piazza.com");
                f34.setComment("course forum");
                f34.setClick(0);
                
                model.getFavoriteDAO().create(f11);
                model.getFavoriteDAO().create(f12);
                model.getFavoriteDAO().create(f13);
                model.getFavoriteDAO().create(f14);
                model.getFavoriteDAO().create(f21);
                model.getFavoriteDAO().create(f22);
                model.getFavoriteDAO().create(f23);
                model.getFavoriteDAO().create(f24);
                model.getFavoriteDAO().create(f31);
                model.getFavoriteDAO().create(f32);
                model.getFavoriteDAO().create(f33);
                model.getFavoriteDAO().create(f34);

            }
        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        UserBean user = (UserBean) session.getAttribute("user");
        String action = getActionName(servletPath);
        System.out.println(servletPath);

//        if (user == null) {
//            // If the user hasn't logged in, so login is the only option
//            return Action.perform("otherfavorite.do", request);
//        }
        if (action.equals("register.do") || action.equals("login.do") || action.equals("viewfavorite.do") || action.equals("visitorclick.do")) {
            // Allow these actions without logging in
            return Action.perform(action, request);
        }

        if (user == null) {
            // If the user hasn't logged in, direct him to the login page
            return Action.perform("login.do", request);
        }
                
        //No matter user login or not, we can provide actions he request
        // Let the logged in user run his chosen action
        return Action.perform(action, request);
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        System.out.println(nextPage);
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }
        
        if (nextPage.startsWith("url:")) {
            System.out.println(nextPage.substring(4));
            response.sendRedirect("http://" + nextPage.substring(4));
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
