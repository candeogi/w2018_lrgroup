package project.servlet;

import project.resource.User;
import project.resource.Message;
import project.database.SearchUserByUsernameAndPasswordDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Login user
 *
 * @author lrgroup
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 */
public final class LoginServlet extends AbstractDatabaseServlet
{

    /**
     * Checks user login
     *
     * @param req
     *            the HTTP request from the client.
     * @param res
     *            the HTTP response from the server.
     *
     * @throws ServletException
     *             if any error occurs while executing the servlet.
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameter
        String username;
        String password;

        // model
        // userlist should contain only one user if correct login, otherwise 0.
        List<User> userlist = null;
        Message m = null;

        //get user session
        HttpSession session= req.getSession();  //TODO getSession(false) maybe

        try {

            // retrieves the request parameters
            username = req.getParameter("username");
            password = req.getParameter("password");

            // creates a new object for accessing the database and searching the employees
            userlist = new SearchUserByUsernameAndPasswordDatabase(getDataSource().getConnection(), username, password)
                    .searchUserByUsernameAndPassword();

            //if i find a Username with given username and password do
            if(!userlist.isEmpty())
            {
                //m = new Message("Login success!");
                session.setAttribute("loggedInUser",userlist.get(0).getUsername());
            }
            else
            {
                m = new Message("Login failed!", "SomeCode", "User/Password is not correct");
                session.removeAttribute("loggedInUser");
            }

        }catch (SQLException ex) {
            m = new Message("Cannot login: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }

        // gets back to the page where the user was headed to before being asked for his/her login
        if(m==null || !m.isError())
        {
            String from = req.getParameter("from");
            if(from != null && !from.equals(""))
            {
                res.sendRedirect(req.getContextPath() + "/?p=" + req.getParameter("from"));
            }
            else
            {
                res.sendRedirect(req.getContextPath() + "/");
            }
        }
        else
        {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/login-result.jsp").forward(req,res);
        }
    }

}
