package project.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import project.database.*;
import project.resource.*;

/**
 * Shows list of user
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */

public class ShowUsersServlet extends SessionManagerServlet
{
    /**
     * Shows list of users.
     *
     * @param req the HTTP request;
     * @param res the HTTP response.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException if any error occurs in the client/server communication.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<User> u = null;
        //String iduser = (String) req.getSession().getAttribute("loggedInUser");
        Message m = null;
        try {
            // creates a new object for accessing the database and searching the users
            u= new SearchUserDatabase(getDataSource().getConnection()).searchUser();
            m = new Message("Users successfully searched.");

        } catch (SQLException ex) {
            m = new Message("Cannot search for users: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }
        req.setAttribute("users", u);
        req.getRequestDispatcher("/jsp/admin-tools.jsp").forward(req, res);


    }
}