package project.servlet;

import project.resource.Message;
import project.database.*;
import project.resource.WebSite;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Represents data about a category.
 *
 * @author Luca Rossi
 * @author Davide Storato
 */

public final class CreateWebSiteServlet extends AbstractDatabaseServlet {

    /**
     * Creates a new website into the database.
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String address = null;
        String addrType = null;


        // model
        String IDUser = null;
        WebSite w = null;

        Message m = null;

        try {
            // retrieves the request parameters
            address = req.getParameter("address");
            addrType = req.getParameter("addrType");

            getServletContext().log("address: "+address);
            getServletContext().log("addrType: "+addrType);

            // retrieves the user id (username) through the session parameter
            IDUser = (String) req.getSession().getAttribute("loggedInUser");

            // creates a new website from the request parameters
            w = new WebSite(address, addrType);

            // creates a new object for accessing the database and stores the question
            new CreateWebSiteDatabase(getDataSource().getConnection(), w).createWebSite();
            new CreateOwnDatabase(getDataSource().getConnection(), IDUser, address).createUserWebSiteAssociation();

        } catch (SQLException ex) {
            m = new Message("Cannot create the WebSite: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

            getServletContext().log(ex.getMessage());

        }

        // stores the user and the message as a request attribute
        req.setAttribute("website", w);
        req.setAttribute("message", m);

        req.getRequestDispatcher("/jsp/user-information.jsp").forward(req, res);

        
    }

}
