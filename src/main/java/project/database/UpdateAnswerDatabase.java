package project.database;

import project.resource.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 * Updates a question in the database.
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public final class UpdateAnswerDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "UPDATE lr_group.Answer SET isFixed=?, body=?" +
            "WHERE id=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The answer to be updated in the database
     */
    private final Answer answer;

    /**
     * Creates a new object to update a question.
     *
     * @param con
     *            the connection to the database.
     * @param answer
     *            the answer to be updated in the database.
     */
    public UpdateAnswerDatabase(final Connection con, final Answer answer)
    {
        this.con = con;
        this.answer = answer;
    }

    /**
     * @return true if the updating is done correctly
     * Updates a question in the database.
     * @throws SQLException
     *             if any error occurs while updating the user.
     */
    public boolean updateAnswer() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setBoolean(1, answer.isFixed());
            pstmt.setString(2, answer.getText());

            rs = pstmt.executeQuery();

            if(rs.next()) return true;
            else return false;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
