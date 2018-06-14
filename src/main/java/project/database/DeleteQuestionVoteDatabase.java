package project.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Deletes a question vote in the database.
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public final class DeleteQuestionVoteDatabase {

    /**
     * The SQL statements to be executed
     */

    private static final String STATEMENT = "" +
            "DELETE FROM lr_group.VoteQuestion" +
            "WHERE question=? AND idUser=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The question vote which has to be deleted in the database
     */
    private final int idQuestion;
    private final String user;

    /**
     * Creates a new object for deleting a question vote.
     *
     * @param con
     *            the connection to the database.
     * @param user
     *            the user who voted the question.
     * @param idQuestion
     *            the question which vote has to be deleted in the database.
     */
    public DeleteQuestionVoteDatabase(final Connection con, final String user ,final int idQuestion) {
        this.con = con;
        this.idQuestion = idQuestion;
        this.user = user;
    }

    /**
     * @return return true id the question is correctly voted
     * Deletes a question vote in the database.
     * @throws SQLException
     *             if any error occurs while storing the answer.
     */
    public boolean deleteQuestionVote() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idQuestion);
            pstmt.setString(2,user);

            pstmt.execute();

            return true;
        }
        catch(Throwable t)
        {
            return false;
        }
        finally 
        {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
