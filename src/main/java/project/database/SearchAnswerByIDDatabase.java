package project.database;

import project.resource.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca Rossi
 * @author Andrea Ziggiotto
 */
public class SearchAnswerByIDDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT * FROM lr_group.answer WHERE id=?";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final int answerID;


    /**
     * Creates a new object for answers retrieval.
     *
     * @param con      the connection to the database.
     * @param answerid the id of the related answer.
     */
    public SearchAnswerByIDDatabase(final Connection con, final int answerid) {
        this.con = con;
        this.answerID = answerid;
    }

    /**
     * Retrieves answers to a specific answer in the database.
     *
     * @return a list of {@code Answer} (must be one) object related to a specific answer.
     * @throws SQLException if any error occurs while retrieving the answer.
     */
    public Answer searchAnswerByID() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Answer> answerList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1, answerID);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Answer a = new Answer(rs.getInt("id"), rs.getString("iduser"), rs.getBoolean("isfixed"), rs.getString("body"), rs.getInt("parentid"), rs.getTimestamp("ts"), -1);
                answerList.add(a);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return answerList.get(0);

    }
}
