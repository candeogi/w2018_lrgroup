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

public class SessionManagerServlet extends AbstractDatabaseServlet{
    /**
     * Manage the user session.
     * If user session is not found, redirects to the login form.
     *
     * @author Giovanni Candeo
     * @version 1.00
     * @since 1.00
     */
    private HttpSession session;

    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        session=req.getSession(false);  //returns null if not found

        if(session!=null){              //if session is found
            super.service(req,res);
        }
        else{                           //if session NOT found
            req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
        }
    }
}