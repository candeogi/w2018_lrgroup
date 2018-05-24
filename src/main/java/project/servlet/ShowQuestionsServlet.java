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
 * Show questions ordered by timestamp
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */

public class ShowQuestionsServlet extends AbstractDatabaseServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<Question> q = null;
        Message m = null;
        try {
            // creates a new object for accessing the database and searching the questions
            q= new SearchQuestionByTimestampDatabase(getDataSource().getConnection()).SearchQuestionByTimestamp();
            m = new Message("Questions successfully searched.");

        } catch (SQLException ex) {
            m = new Message("Cannot search for questions: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }
        req.setAttribute("questions", q);
        req.getRequestDispatcher("/jsp/show-questions-result.jsp").forward(req, res);


    }
}