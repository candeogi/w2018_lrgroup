package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a user-website association in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 * @author Davide Storato
 */

public class CreateOwnDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.Own(address, username) " +
            "VALUES (?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;


    private final String utente;
    private final String webSite;

    /**
     * @param con the reference to the connection to the database
     * @param utente  the id of the user (username)
     * @param website the id of the website (address)
     */
    public CreateOwnDatabase(final Connection con, final String utente, final String website) {
        this.con = con;
        this.utente = utente;
        this.webSite = website;
    }

    /**
     * Creates a user-website association in the database.
     *
     * @throws SQLException if any error occurs while storing the association.
     */
    public void createUserWebSiteAssociation() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, webSite);
            pstmt.setString(2, utente);

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
