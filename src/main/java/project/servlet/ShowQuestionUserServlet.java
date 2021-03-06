package project.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import project.database.*;
import project.resource.*;

/**
 * Show questions by user
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */

public class ShowQuestionUserServlet extends SessionManagerServlet
{
    /**
     * Shows the user's question.
     *
     * @param req the HTTP request;
     * @param res the HTTP response.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException if any error occurs in the client/server communication.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<Question> q = null;
        String IDUser;
        Message m = null;
        try {
                // creates a new object for accessing the database and searching the questions
                IDUser = (String) req.getSession().getAttribute("loggedInUser");
                q = new SearchQuestionByUserDatabase(getDataSource().getConnection(),IDUser).SearchQuestionByUser();
                m = new Message("Questions successfully searched.");

        } catch (SQLException ex) {
            m = new Message("Cannot search for questions: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }
        req.setAttribute("questions", q);
        req.getRequestDispatcher("/jsp/show-questions-result.jsp").forward(req, res);


    }
}