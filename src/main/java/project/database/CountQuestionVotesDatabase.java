package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 * Counts votes for a question in the database
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public class CountQuestionVotesDatabase {

    private static final String STATEMENT = "SELECT SUM(vote) from lr_group.VoteQuestion WHERE question=?";

    Connection con;

    private final int idQuestion;


    /**
     * Creates a new object to count votes of a question in the database
     *
     * @param con
     *            the connection to the database.
     * @param idQuestion
     *            the question which votes have to be counted.
     */
    public CountQuestionVotesDatabase(Connection con, int idQuestion) {
        this.con = con;
        this.idQuestion = idQuestion;
    }

    /**
     * Counts votes for a question in the database.
     * @throws SQLException
     *             if any error occurs while creating the relation. 
     */

    public int countQuestionVotes() throws SQLException {
        PreparedStatement pstmt = null;
        int c = 0;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idQuestion);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            c = rs.getInt(1); //NOT sure about return type
            rs.close();
            return c;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
    }
}
