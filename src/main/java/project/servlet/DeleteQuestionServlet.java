package project.servlet;

import project.database.DeleteQuestionByIDDatabase;
import project.database.ListQuestionsDatabase;
import project.resource.Message;
import project.resource.Question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Deletes a new question from the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class DeleteQuestionServlet extends SessionManagerServlet
{

    /**
     * Delete a question from the database.
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

        int questionid = 0;
        Message m = null;

        try{

            questionid = Integer.parseInt(req.getParameter("idquestion"));
            new DeleteQuestionByIDDatabase(getDataSource().getConnection(),questionid).DeleteQuestionByID();
            m = new Message("Correctly deleted the question");


        }catch (SQLException ex) {
            m = new Message("Cannot delete the question: unexpected error while accessing the database.",
                    "E403", ex.getMessage());

        }

        if(m!=null && m.isError())
        {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
        }
        else
        {
            String url = req.getParameter("from");
            if(url != null)
                res.sendRedirect(req.getContextPath() + "/?p=" + url);
            else
                res.sendRedirect(req.getContextPath() + "/");

        }
    }

}
