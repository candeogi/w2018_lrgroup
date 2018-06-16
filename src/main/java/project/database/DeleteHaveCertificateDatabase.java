package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes certificate in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 */


public class DeleteHaveCertificateDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "DELETE " +
                    " FROM lr_group.HaveCertificate " +
                    " WHERE username=? AND id=?";


    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The user which certificate refer to
     */
    private final String username;

    /**
     * ID of the certificate
     */
    private final int id;

    /**
     * Creates a new object for deleting a have-certificate relationship.
     *
     * @param con the connection to the database.
     * @param username the user which certificate refer to
     * @param id ID of the certificate
     */
    public DeleteHaveCertificateDatabase(Connection con, String username, int id) {
        this.con = con;
        this.username = username;
        this.id = id;
    }

    /**
     * Delete a have-certificate entry in the database
     *
     * @return 0 if cannot delete any entry, a number greater than 0 otherwise
     * @throws SQLException if any error occurs while deleting the have-certificate association.
     */
    public int deleteCertificate() throws SQLException {

        PreparedStatement preparedStatement = null;
        int i = 0;

        try {
            preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            i = preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            con.close();
        }
        return i;
    }


}
