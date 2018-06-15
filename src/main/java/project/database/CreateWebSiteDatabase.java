package project.database;

import project.resource.WebSite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents data about a website.
 *
 * @author Luca Rossi
 * @author Davide Storato
 */


public class CreateWebSiteDatabase {

    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.Website (address, addrType) " +
            "VALUES (?, CAST ( ? AS  lr_group.websitetype ))";

    private static final String STATEMENT2 = "" +
            "SELECT address FROM lr_group.Website " +
            "WHERE address = ? ";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The category to be created in the database
     */
    private final WebSite webSite;

    /**
     * @param con     the reference to the connection to the database
     * @param webSite the instance of Website to add  to the database
     */
    public CreateWebSiteDatabase(final Connection con, final WebSite webSite) {
        this.con = con;
        this.webSite = webSite;
    }

    /**
     * Creates a website in the database.
     *
     * @throws SQLException if any error occurs while storing the category
     */
    public void createWebSite() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            pstmt = con.prepareStatement(STATEMENT2);
            pstmt.setString(1, webSite.getAddress());
            rs = pstmt.executeQuery();


            if (!rs.next()) {
                pstmt = con.prepareStatement(STATEMENT);
                pstmt.setString(1, webSite.getAddress());
                pstmt.setString(2, webSite.getType());

                pstmt.execute();
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }

            con.close();
        }

    }

}




