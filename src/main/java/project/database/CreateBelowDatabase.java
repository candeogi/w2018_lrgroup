package project.database;

import project.resource.Category;
import project.resource.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a category-question association in the database.
 *
 *  @author lrgroup
 *  @author Luca Rossi
 *  @author Davide Storato
 */

public class CreateBelowDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.below (category, question) " +
            "VALUES (?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The category and the question in the database
     */
    private final Category category;
    private final Question question;

    /**
     * Creates a new object for creating a category-question association.
     *
     * @param con
     *            the connection to the database.
     * @param category
     *            the category in the database.
     * @param question
     *            the question in the database
     */
    public CreateBelowDatabase (final Connection con, final Category category, final Question question) {
        this.con = con;
        this.category = category;
        this.question = question;
    }

    /**
     * Creates a category-question association in the database.
     * @throws SQLException
     *             if any error occurs while storing the association.
     */
    public void createCategoryQuestionAssociation () throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, category.getName());
            pstmt.setInt(2, question.getID());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
