package project.servlet;

import project.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class LogoutServlet extends AbstractDatabaseServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.getSession().invalidate();

        Message m = null;

        res.sendRedirect(req.getContextPath()+"/jsp/index.jsp");
        /*if(m==null || !m.isError())
        {
            String from = req.getParameter("from");
            if(from != null || !from.equals(""))
            {
                res.sendRedirect(req.getContextPath() + "/?p=" + req.getParameter("from"));
            }
            else
            {
                res.sendRedirect(req.getContextPath() + "/");
            }
        }
        else
        {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/login-result.jsp").forward(req,res);
        }*/

    }
}