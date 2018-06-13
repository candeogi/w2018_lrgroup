package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes certificate.
 *
 * @author lrgroup
 * @author Luca Rossi
 */


public class DeleteHaveCertificateDatabase {

    private static final String QUERY =
            "DELETE " +
                    "FROM lr_group.HaveCertificate " +
                    "WHERE username=? AND id=?";


    private final Connection con;
    private final String username;
    private final int id;

    public DeleteHaveCertificateDatabase( Connection con, String username, int id) {
        this.con = con;
        this.username = username;
        this.id =id;
    }

    public int deleteCertificate() throws SQLException {

        PreparedStatement preparedStatement = null;
        int i = 0;

        try {
            preparedStatement = con.prepareStatement(QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2,id );
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
