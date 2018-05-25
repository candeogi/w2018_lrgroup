package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Creates an item in the "have" table in the database.
 *
 * @author lrgroup
 * @author Andrea Ziggiotto (andrea.ziggiotto@studenti.unipd.it)
 */
public class CreateHaveElement {

    private static final String STATEMENT = "INSERT INTO lr_group.Have (idQuestion,idAnswer) VALUES (?,?)";

    Connection con;

    private final int idQuestion;

    private final int idAnswer;


    /**
     * Creates a new object for creating a new relation between Question and Answer.
     *
     * @param con
     *            the connection to the database.
     * @param idQuestion
     *            the question to be added to the relation.
     * @param idAnswer
     *            the answer to be added to the relation.
     */
    public CreateHaveElement(Connection con, int idQuestion, int idAnswer) {
        this.con = con;
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
    }

    /**
     * Creates a new relation in the database.
     * @throws SQLException
     *             if any error occurs while creating the relation. 
     */

    public void createElement() throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idQuestion);
            pstmt.setInt(2, idAnswer);
            pstmt.execute();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
    }
}
