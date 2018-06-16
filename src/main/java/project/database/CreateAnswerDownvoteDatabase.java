/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package project.database;


import java.sql.*;

/**
 * Creates an answer downvote in the database.
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public final class CreateAnswerDownvoteDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.VoteAnswer (answer, idUser, vote) " +
            "VALUES (?, ?, ?)";

    private static final String STATEMENT_2 = "" +
            "DELETE FROM lr_group.VoteAnswer " +
            "WHERE answer=? AND idUser=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The answer to be updated in the database
     */
    private final int idAnswer;
    private final String user;

    /**
     * Creates a new object for creating an answer downvote.
     *
     * @param con      the connection to the database.
     * @param user     the user who voted the question.
     * @param idAnswer the answer to be voted in the database.
     */
    public CreateAnswerDownvoteDatabase(final Connection con, final String user, final int idAnswer) {
        this.con = con;
        this.idAnswer = idAnswer;
        this.user = user;
    }

    /**
     * @return return true id the answer is correctly done
     * Creates an downvote in the database.
     * @throws SQLException if any error occurs while storing the answer.
     */
    public boolean createAnswerDownvote() throws SQLException {

        PreparedStatement pstmt = null;

        try {

            pstmt = con.prepareStatement(STATEMENT_2);
            pstmt.setInt(1, idAnswer);
            pstmt.setString(2, user);

            pstmt.execute();

            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idAnswer);
            pstmt.setString(2, user);
            pstmt.setInt(3, -1);

            pstmt.execute();

            return true;
        } catch (Throwable t) {
            return false;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
    }
}
