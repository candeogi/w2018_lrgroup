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

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "DELETE " +
                    "FROM lr_group.Own " +
                    "WHERE username=? AND address=?";


    /**
     * The connection to the database
     */
    private final Connection con;
    private final String username;
    private final String website;

    /**
     * Creates a new object for a specific website retrieval
     * @param con the connection to the database.
     * @param username username of the user which website refer to
     * @param website website name
     */
    public DeleteWebsiteDatabase(final Connection con, final String username, final String website) {
        this.con = con;
        this.username = username;
        this.website = website;
    }

    /**
     * Delete a specific website of an user
     * @return 0 if cannot delete any entry, a number greater than 0 otherwise
     * @throws SQLException if any error occurs while deleting the user.
     */
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
