package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes website by username and website.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */


public class DeleteWebsiteDatabase {

    private static final String QUERY =
            "DELETE " +
                    "FROM lr_group.Own " +
                    "WHERE username=? AND address=?";


    private final Connection con;
    private final String username;
    private final String website;

    public DeleteWebsiteDatabase(final Connection con, final String username, final String website) {
        this.con = con;
        this.username = username;
        this.website = website;
    }

    public int deleteWebsite() throws SQLException {
        PreparedStatement preparedStatement = null;
        int id = 0;
        try {
            preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, website);
            id = preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            con.close();
        }
        return id;
    }


}
