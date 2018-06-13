package project.rest;

import project.database.*;
import project.resource.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.io.*;


public class RestCertificate extends RestResource {

    public RestCertificate(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    public void searchCertificateByUserName() throws IOException {

        List<Certificate> c = null;
        Message m = null;
        Message message;
        try {

            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("user") + 4);

            final String idUser = path.substring(1);

            // creates a new object for accessing the database and search the question
            c = new SearchCertificateByUsernameDatabase(con, idUser).SearchCertificateByUser();

            if (c != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(c).toJSON(res.getOutputStream());
            } else {
                // it should not happen
                m = new Message("Cannot search question: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            m = new Message("Cannot search question: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

}
