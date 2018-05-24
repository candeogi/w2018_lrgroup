package project.database;

import project.resource.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Update a question in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class UpdateQuestionDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "UPDATE lr_group.question SET title=?, body=?, lastModified=? " +
            "WHERE id=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The question to be updated in the database
     */
    private final Question question;

    /**
     * Creates a new object for updating a question.
     *
     * @param con
     *            the connection to the database.
     * @param question
     *            the question to be updated in the database.
     */
    public UpdateQuestionDatabase(final Connection con, final Question question)
    {
        this.con = con;
        this.question = question;
    }

    /**
     * Update a question in the database.
     * @throws SQLException
     *             if any error occurs while storing the user.
     */
    public void updateQuestion() throws SQLException {

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, question.getTitle());
            pstmt.setString(2, question.getBody());
            pstmt.setTimestamp(3, question.getLastModified());
            pstmt.setInt(4,question.getID());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
