package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 * Counts votes for an answer in the database
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public class CountAnswerVotesDatabase {

    private static final String STATEMENT = "SELECT SUM(vote) from lr_group.VoteAnswer WHERE answer=?";

    Connection con;

    private final int idAnswer;


    /**
     * Creates a new object to count votes of an answer in the database
     *
     * @param con
     *            the connection to the database.
     * @param idAnswer
     *            the answer which votes have to be counted.
     */
    public CountAnswerVotesDatabase(Connection con, int idAnswer) {
        this.con = con;
        this.idAnswer = idAnswer;
    }

    /**
     * Counts votes for an answer in the database.
     * @throws SQLException
     *             if any error occurs while creating the relation. 
     */

    public int countAnswerVotes() throws SQLException {
        PreparedStatement pstmt = null;
        int c = 0;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idAnswer);
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
