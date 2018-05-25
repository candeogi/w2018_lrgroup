package project.database;

import project.resource.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Updates the user photo profile
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 */
public final class UpdateUserPhotoProfileDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "UPDATE lr_group.Utente SET photoprofile=? " +
            "WHERE username=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The user to be updated in the database
     */
    private final User user;

    /**
     * Creates a new object to update the user photo profile.
     *
     * @param con
     *            the connection to the database.
     * @param user
     *            the user to be updated in the database.
     */
    public UpdateUserPhotoProfileDatabase(final Connection con, final User user)
    {
        this.con = con;
        this.user = user;
    }

    /**
     * Update the user photo profile in the database.
     * @throws SQLException
     *             if any error occurs while updating the user.
     */
    public void updateUserPhotoProfile() throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, user.getPhotoProfile());
            pstmt.setString(2, user.getUsername());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
