package project.database;

import project.resource.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a category in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 * @author Davide Storato
 */
public final class CreateCategoryDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.category (id, name, description, isCompany) " +
            "VALUES (DEFAULT,?, ?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The category to be created in the database
     */
    private final Category category;

    /**
     * Creates a new object for creating a category.
     *
     * @param con
     *            the connection to the database.
     * @param category
     *            the category to be created in the database.
     */
    public CreateCategoryDatabase (final Connection con, final Category category) {
        this.con = con;
        this.category = category;
    }

    /**
     * Creates a category in the database.
     * @throws SQLException
     *             if any error occurs while storing the category
     */
    public void createCategory () throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.setBoolean(3, category.isCompany());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
