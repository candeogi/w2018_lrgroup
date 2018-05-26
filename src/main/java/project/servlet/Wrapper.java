package project.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Wrapper between question list and update question form
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class Wrapper extends SessionManagerServlet
{

    /**
     * Redirect from list question to update question
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

        int idquestion = Integer.parseInt(req.getParameter("IDquestion"));
        String oldtitle = (String) req.getParameter("oldtitle");
        String oldbody = (String) req.getParameter("oldbody");

        req.setAttribute("questionid",idquestion);
        req.setAttribute("oldtitle",oldtitle);
        req.setAttribute("oldbody",oldbody);

        req.getRequestDispatcher("/jsp/update-question-form.jsp").forward(req, res);

    }

}
