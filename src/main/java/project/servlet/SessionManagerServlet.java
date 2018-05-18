package project.servlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
/**
 * Manage the user session.
 * If user session is not found, redirects to the login form.
 *
 * @author Giovanni Candeo
 * @version 1.00
 * @since 1.00
 */
public class SessionManagerServlet extends AbstractDatabaseServlet{

    private HttpSession session;

    /**
     * Overrides service method called by the servlet container.
     * If there is a session i allow the servlet to respond to the request; otherwise redirects to the login form.
     *
     * @param req
     *          the HTTP request from the client.
     * @param res
     *          the HTTP response from the server.
     * @throws ServletException
     *          if an exception has occurred that interferes with the servlet's normal operation
     * @throws IOException
     *          if an input or output error occurs while the servlet is handling the HTTP request
     */
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        session=req.getSession(false);  //returns null if not found

        if(session.getAttribute("loggedInUser")!=null){              //if session is found
            super.service(req,res);
        }
        else{                           //if session NOT found
            req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
        }
    }
}