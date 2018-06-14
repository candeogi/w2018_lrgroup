package project.database;

import project.resource.WebSite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves website by user ID in the database
 *
 * @author lrgroup
 * @author Andrea Ziggiotto
 */
public class SearchWebsiteByUser {


    /**
     * The SQL statement to be executed
     */

    private static final String QUERY =
            "SELECT web.address,o.username,web.addrtype FROM " +
                    "lr_group.website AS web INNER JOIN lr_group.own AS o" +
                    " ON web.address=o.address" +
                    " WHERE o.username=?";


    private final Connection con;
    private final String user;

    /**
     * Create a new instance of the object
     *
     * @param con  is the connection to the database
     * @param user is the ID of the question
     */
    public SearchWebsiteByUser(final Connection con, final String user) {
        this.con = con;
        this.user = user;
    }

    /**
     * Retrieves thw website related to a specific user
     *
     * @return a list of {@code Website}
     * @throws SQLException if any error occurs while retrieving the website
     */
    public List<WebSite> searchWebsite() throws SQLException {
        PreparedStatement pstmt = null;
        List<WebSite> webSiteList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, user);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WebSite webSite = new WebSite(rs.getString(1), rs.getString(3));
                webSiteList.add(webSite);
            }
            rs.close();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }

        return webSiteList;

    }
}
