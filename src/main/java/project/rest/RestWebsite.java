package project.rest;

import project.database.DeleteWebsiteDatabase;
import project.resource.Message;
import project.resource.ResourceList;
import project.resource.WebSite;
import project.database.SearchWebsiteByUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Manages the REST API for the {@link WebSite} resource.
 *
 * @author lrgroup
 */
public class RestWebsite extends RestResource {
    /**
     * Creates a new REST resource for managing {@code Category} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public RestWebsite(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);

    }

    /**
     * Searches website of an user into the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void searchWebsiteByUser() throws IOException {
        List<WebSite> websites = null;
        Message message = null;
        try {
            String path = req.getRequestURI();
            path = path.substring(path.indexOf("user") + 5);

            final String userID = path;

            websites = new SearchWebsiteByUser(con, userID).searchWebsite();

            if (websites != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(websites).toJSON(res.getOutputStream());
            } else {
                message = new Message("Cannot search website: unexpected error. path: " + path, "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(res.getOutputStream());
            }

        } catch (SQLException e) {
            message = new Message("Cannot search website: unexpected error. DB", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(res.getOutputStream());
        }

    }

    /**
     * Removes website of an user into the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void removeWebsite() throws IOException {
        Message message = null;
        try {
            String path = req.getRequestURI();
            path = path.substring(path.indexOf("user") + 5);
            int indexSlash = path.indexOf("/");


            final String userID = path.substring(0, indexSlash);
            path = path.substring(path.indexOf("website") + 8);
            final String websiteID = path;

            int result=(new DeleteWebsiteDatabase(con, userID, websiteID)).deleteWebsite();




            if (result != 0) {
                message=new Message("Delete the selected element");
                res.setStatus(HttpServletResponse.SC_OK);
                message.toJSON(res.getOutputStream());
            } else {
                message = new Message("Cannot delete any website: unexpected error. path: "+path, "E5A1", null);
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
