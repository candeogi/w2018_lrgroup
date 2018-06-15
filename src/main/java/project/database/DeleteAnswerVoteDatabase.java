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
 * Deletes an answer vote in the database.
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public final class DeleteAnswerVoteDatabase {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT = "" +
            "DELETE FROM lr_group.VoteAnswer " +
            "WHERE answer=? AND idUser=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    private final int idAnswer;
    private final String user;

    /**
     * Deletes a new object for creating an answer.
     *
     * @param con      the connection to the database.
     * @param user     the user which vote hasto be deleted the question.
     * @param idAnswer the answer which vote has to be deleted in the database.
     */
    public DeleteAnswerVoteDatabase(final Connection con, final String user, final int idAnswer) {
        this.con = con;
        this.idAnswer = idAnswer;
        this.user = user;
    }

    /**
     * Deletes an upvote in the database.
     *
     * @return true id the deleting is correctly done
     * @throws SQLException if any error occurs while storing the answer.
     */
    public boolean deleteAnswerVote() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idAnswer);
            pstmt.setString(2, user);

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
