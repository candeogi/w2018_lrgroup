package project.servlet;

import project.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * End user session
 *
 * @author lrgroup
 * @author Andrea Ziggiotto (andrea.ziggiotto@studenti.unipd.it)
 */
public final class LogoutServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.getSession().invalidate();

        Message m = null;

        res.sendRedirect(req.getContextPath());

    }
}