package project.database;

import project.resource.WebSite;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    //private static final String STATEMENT = " IF NOT EXISTS ( SELECT * FROM lr_group.Website WHERE address = ? )  BEGIN INSERT INTO lr_group.Website (address, addrType) VALUES (?, CAST ( ? AS  lr_group.websitetype )) END ";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The category to be created in the database
     */
    private final WebSite webSite;

    /**
     * @param con
     * @param webSite
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

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, webSite.getAddress());
            pstmt.setString(2, webSite.getType());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }

}




