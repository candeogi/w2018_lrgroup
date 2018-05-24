package project.servlet;

import project.database.UpdateQuestionDatabase;
import project.resource.Question;
import project.resource.Message;
import project.database.CreateQuestionDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;


/**
 * Creates a new question into the database.
 * The title and body of the question is provided by the request through the form.
 * The user writing the question is obtained through the active session.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 */
public final class UpdateQuestionServlet extends SessionManagerServlet
{

    /**
     * Creates a new question into the database.
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

        // request parameters
        String title = null;
        String body = null;
        int questionid = 0;
        String IDUser = null;
        Timestamp ts = null;

        // model
        Question q  = null;
        Message m = null;

        try{
            // retrieves the request parameters


            title = req.getParameter("title");
            if(title.equals(""))
                title = req.getParameter("currentTitle");

            body = req.getParameter("body");
            if(body.equals(""))
                body = req.getParameter("currentBody");

            questionid = Integer.parseInt(req.getParameter("id"));

            // retrieves the user id (username) through the session parameter
            IDUser = (String) req.getSession().getAttribute("loggedInUser");

            // creates a new question from the request parameters
            q = new Question(questionid ,IDUser, title, body, new Timestamp(0), new Timestamp((Long)System.currentTimeMillis()));

            // creates a new object for accessing the database and stores the question
            new UpdateQuestionDatabase(getDataSource().getConnection(), q).updateQuestion();
        }catch (SQLException ex) {
            m = new Message("Cannot update the question: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

        }

        req.setAttribute("message", m);

        /*if(m!=null && m.isError())
        {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
        }
        else
        {
            res.sendRedirect(req.getContextPath() + "/?p=question&u=" + questionid);
        }*/


    }

}
