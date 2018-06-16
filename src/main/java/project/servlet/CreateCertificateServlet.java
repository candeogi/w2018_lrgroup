package project.servlet;

import project.database.CreateCertificateDatabase;
import project.resource.Certificate;
import project.resource.Question;
import project.resource.Message;
import project.database.CreateQuestionDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Creates data about a certificate
 *
 * @author Luca Rossi
 * @author Davide Storato
 */
public final class CreateCertificateServlet extends SessionManagerServlet {

    /**
     * Creates a new certificate into the database.
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String name;
        String organization;

        // model
        Certificate c = null;
        Message m = null;


        try {
            // retrieves the request parameters
            name = req.getParameter("name");
            organization = req.getParameter("organization");

            // creates a new certificate from the request parameters
            c = new Certificate(-1,name, organization);

            new CreateCertificateDatabase(getDataSource().getConnection(), c).createCertificate();

        } catch (SQLException ex) {
            m = new Message("Cannot create the certificate: unexpected error while accessing the database.",
                    "E200", ex.getMessage());

        }

        // stores the user and the message as a request attribute
        req.setAttribute("certificate", c);
        req.setAttribute("message", m);

        // forwards the control to the create-user-result JSP
        req.getRequestDispatcher("/jsp/create-certificate-result.jsp").forward(req, res);
    }

}
