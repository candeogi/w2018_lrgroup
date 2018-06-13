package project.servlet;

import project.database.GetCategoriesDatabase;
import project.resource.Category;
import project.resource.Message;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Show categories name saved in the database
 *
 * @author lrgroup
 * @author Davide Storato
 */

public class ShowCategoriesServlet extends AbstractDatabaseServlet{

    /**
     * Shows the categories saved in the database.
     *
     * @param req the HTTP request;
     * @param res the HTTP response.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException if any error occurs in the client/server communication.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<Category> q = null;
        Message m = null;
        try {
            // creates a new object for accessing the database and searching the categories
            q= new GetCategoriesDatabase(getDataSource().getConnection()).getCategories();
            m = new Message("Categories successfully searched.");

        } catch (SQLException ex) {
            m = new Message("Cannot search for categories: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }
        req.setAttribute("categories", q);
        req.getRequestDispatcher("/jsp/show-categories-name.jsp").forward(req, res);


    }
}
