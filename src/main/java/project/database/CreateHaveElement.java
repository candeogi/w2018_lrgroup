package project.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Creates an item in the "have" table in the database.
 *
 * @author Andrea Ziggiotto
 * @version 1.00
 */
public class CreateHaveElement {

    private static final String STATEMENT = "INSERT INTO lr_group.Have (idQuestion,idAnswer) VALUES (?,?)";

    Connection con;

    private final int idQuestion;

    private final int idAnswer;

    public CreateHaveElement(Connection con, int idQuestion, int idAnswer) {
        this.con = con;
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
    }


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
