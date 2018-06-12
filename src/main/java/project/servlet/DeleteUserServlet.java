package project.servlet;


import project.database.DeleteQuestionByIDDatabase;
import project.database.DeleteUserByUsernameDatabase;
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
 * @author Luca Rossi
 */
public class DeleteUserServlet extends SessionManagerServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = null;
        Message m = null;

        try {

            username = req.getParameter("username to delete");
            new DeleteUserByUsernameDatabase(getDataSource().getConnection(), username).DeleteUserByUsername();
            m = new Message("Correctly deleted the user");


        } catch (SQLException ex) {
            m = new Message("Cannot delete the user: unexpected error while accessing the database.",
                    "E403", ex.getMessage());

        }

        if (m != null && m.isError()) {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
        } else {
            res.sendRedirect(req.getContextPath() + "/");
        }
    }


}
