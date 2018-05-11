package project.servlet;

import project.resource.User;
import project.resource.Message;
import project.database.SearchUserByEmailAndPasswordDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

/**
 * Checks user login
 *
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 * @version 1.0.0
 *
 */
public final class LoginServlet extends AbstractDatabaseServlet {

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
        String email;
        String password;

        // model
        // userlist should contain only one user if correct login, otherwise 0.
        List<User> userlist = null;
        Message m = null;

        //get user session
        HttpSession session= req.getSession();  //TODO getSession(false) maybe

        try {

            // retrieves the request parameters
            email = req.getParameter("email");
            password = req.getParameter("password");

            // creates a new object for accessing the database and searching the employees
            userlist = new SearchUserByEmailAndPasswordDatabase(getDataSource().getConnection(), email,password)
                    .searchUserByEmailAndPassword();

            //if i find a Username with given email and password do
            if(!userlist.isEmpty()){
                m = new Message("Login success!");
                session.setAttribute("loggedInUser",userlist.get(0).getUsername());
            }
            else{
                m = new Message("Login failed!");
                session.removeAttribute("loggedInUser");
            }

        }catch (SQLException ex) {
            m = new Message("Cannot login: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }

        // stores the user list and the message as a request attribute
//      req.setAttribute("userList", userlist);
        req.setAttribute("message", m);

        // forwards the control to the search-employee-result JSP
        req.getRequestDispatcher("/jsp/login-result.jsp").forward(req, res);

    }

}
