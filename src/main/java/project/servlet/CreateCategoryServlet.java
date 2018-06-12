package project.servlet;

import project.database.CreateCategoryDatabase;
import project.resource.Category;
import project.resource.Message;
import project.resource.Answer;
import project.database.CreateAnswerDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Creates a user-website association in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 * @author Davide Storato
 */

public final class CreateCategoryServlet extends SessionManagerServlet {

    /**
     * Creates a new category into the database.
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String name = null;
        String description = null;
        boolean isCompany = false;

        String IDUser = null;

        // model
        Category c = null;
        Message m = null;

        try {
            // retrieves the request parameters
            name = req.getParameter("name");
            description = req.getParameter("description");
            isCompany = Boolean.parseBoolean(req.getParameter("isCompany"));

            c = new Category(name, description, isCompany);

            // creates a new object for accessing the database and stores the answer
            new CreateCategoryDatabase(getDataSource().getConnection(), c).createCategory();

            m = new Message(String.format("category successfully created."));
        } catch (SQLException ex) {
            m = new Message("Cannot create the category: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }

        // stores the user and the message as a request attribute
        req.setAttribute("category", c);
        req.setAttribute("message", m);

        // forwards the control to the create-user-result JSP
        req.getRequestDispatcher("/jsp/create-category-result.jsp").forward(req, res);
    }

}
