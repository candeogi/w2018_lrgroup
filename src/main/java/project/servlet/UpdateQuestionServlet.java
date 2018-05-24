package project.servlet;

import project.database.SearchQuestionByIDDatabase;
import project.database.UpdateQuestionDatabase;
import project.resource.Question;
import project.resource.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Creates a new question into the database.
 * The title and body of the question is provided by the request through the form.
 * The user writing the question is obtained through the active session.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class UpdateQuestionServlet extends SessionManagerServlet
{

    /**
     * Update a question into the database.
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

            // retrieves the user id (username) through the session parameter
            IDUser = (String) req.getSession().getAttribute("loggedInUser");

            questionid = Integer.parseInt(req.getParameter("id"));
            List<Question> q_searched = new SearchQuestionByIDDatabase(getDataSource().getConnection(),questionid)
                    .SearchQuestionByID();
            if(!q_searched.isEmpty() && IDUser.equals(q_searched.get(0).getIDUser())){
                q = new Question(questionid ,IDUser, title, body, new Timestamp(0), new Timestamp((Long)System.currentTimeMillis()));
                new UpdateQuestionDatabase(getDataSource().getConnection(), q).updateQuestion();
            }
            else {
                m = new Message("Cannot update a question from different user",
                        "E403", "you can't!");
            }


        }catch (SQLException ex) {
            m = new Message("Cannot update the question: unexpected error while accessing the database.",
                    "E403", ex.getMessage());

        }

        if(m!=null && m.isError())
        {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
        }
        else
        {
            try {
                req.setAttribute("question",
                        new SearchQuestionByIDDatabase(getDataSource().getConnection(), questionid).SearchQuestionByID().get(0));
            }catch (SQLException ex){
                m = new Message("Cannot find the question: unexpected error while accessing the database.",
                        "E403", ex.getMessage());
            }
            req.setAttribute("update",true);
            req.getRequestDispatcher("/jsp/create-question-result.jsp").forward(req, res);
        }
    }

}
