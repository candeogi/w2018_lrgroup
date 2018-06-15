package project.database;

import project.resource.Category;
import project.resource.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes a category-question association in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 * @author Davide Storato
 */
public class DeleteCategoryQuestionFromBelowDatabase {
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            " DELETE FROM lr_group.below " +
            " WHERE lr_group.below.category = ? " + "AND" + " lr_group.below.question = ?";

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
     * Creates a new object for deleting a category.
     *
     * @param con      the connection to the database.
     * @param category the category in the database.
     * @param question the question in the database
     */
    public DeleteCategoryQuestionFromBelowDatabase(final Connection con, final Category category, final Question question) {
        this.con = con;
        this.category = category;
        this.question = question;
    }

    /**
     * Deletes a category-question association in the database.
     *
     * @throws SQLException if any error occurs while deleting the association.
     */
    public void deleteCategoryQuestionAssociation() throws SQLException {

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
