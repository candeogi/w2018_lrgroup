package project.rest;

import project.database.*;
import project.resource.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.io.*;

/**
 * Manages the REST API for the {@link Certificate} resource.
 *
 * @author lrgroup
 */
public class RestCertificate extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Category} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public RestCertificate(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    /**
     * Searches certificate of an user into the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
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
                m = new Message("Cannot search certificate: unexpected error. DB", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (SQLException e) {
            m = new Message("Cannot search certificate: unexpected error.", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    /**
     * Deletes certificate of an user into the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void deleteHaveCertificateByUserName() throws IOException{
        Message message = null;
        try {
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("user") + 5);
            int indexSlash = path.indexOf("/");


            final String userID = path.substring(0, indexSlash);
            path = path.substring(path.lastIndexOf("id") + 3);
            final String id = path;
            int idN = Integer.parseInt(path);
            int result=(new DeleteHaveCertificateDatabase(con, userID, idN)).deleteCertificate();


            if (result != 0) {
                message=new Message("Delete the selected element");
                res.setStatus(HttpServletResponse.SC_OK);
                message.toJSON(res.getOutputStream());
            } else {
                message = new Message("Cannot delete any HaveCertificate: unexpected error. path: "+path, "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(res.getOutputStream());
            }

        } catch (SQLException e) {
            message = new Message("Cannot delete website: unexpected error", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(res.getOutputStream());
        }

    }


}
