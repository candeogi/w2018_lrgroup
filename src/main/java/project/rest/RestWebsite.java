package project.rest;

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

public class RestWebsite extends RestResource {
    public RestWebsite(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);

    }

    public void searchWebsiteByUser() throws IOException {
        List<WebSite> websites = null;
        Message message = null;
        try {
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("user") + 5);

            final String userID = path;

            websites = new SearchWebsiteByUser(con, userID).searchWebsite();

            if (websites != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(websites).toJSON(res.getOutputStream());
            } else {
                message = new Message("Cannot search website: unexpected error. path: "+path, "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(res.getOutputStream());
            }

        } catch (SQLException e) {
            message = new Message("Cannot search website: unexpected error. DB", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(res.getOutputStream());
        }

    }
}
