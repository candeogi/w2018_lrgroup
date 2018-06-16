package project.servlet;

import project.database.CreateHaveCertificateDatabase;
import project.resource.Message;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Creates a user-certificate association in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 * @author Davide Storato
 */
public class CreateHaveCertificateServlet extends SessionManagerServlet {


    /**
     * Creates a new user-certificate relationship into the database.
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String name = null;
        String organization = null;
        String IDUser = null;
        String achievementDate;

        Message m = null;

        try {
            // retrieves the request parameters
            name = req.getParameter("certificate-name");
            getServletContext().log("certificate : "+name);
            organization = req.getParameter("organization");
            getServletContext().log("certificate : "+organization);
            achievementDate = req.getParameter("achievementDate");
            getServletContext().log("certificate : "+achievementDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date aDate = new Date(dateFormat.parse(achievementDate).getTime());

            IDUser = (String) req.getSession().getAttribute("loggedInUser");

            // creates a new object for accessing the database and stores the HaveCertificate
            new CreateHaveCertificateDatabase(getDataSource().getConnection(), IDUser, name, organization, aDate).createHaveCertificate();

            m = new Message(String.format("Answer successfully created."));

        }catch(ParseException pe) {
            //Should not happen
            m = new Message("Birthday not correct", "E300", pe.getMessage());
            getServletContext().log(pe.getMessage());

        }catch (SQLException ex) {
            m = new Message("Cannot create the Have Certificate: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
            getServletContext().log(ex.getMessage());
        }

        // stores the user and the message as a request attribute
        //req.setAttribute("answer", a);
        req.setAttribute("message", m);

        // forwards the control to the create-user-result JSP
        req.getRequestDispatcher("/jsp/user-information.jsp").forward(req, res);
    }


}
