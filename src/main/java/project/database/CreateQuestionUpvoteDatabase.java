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
 * Creates a question upvote in the database.
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public final class CreateQuestionUpvoteDatabase {

    /**
     * The SQL statements to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.VoteQuestion (question, idUser, vote) " +
            "VALUES (?, ?, ?)";

    private static final String STATEMENT_2 = "" +
            "DELETE FROM lr_group.VoteQuestion" +
            "WHERE question=? AND idUser=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The question to be voted in the database
     */
    private final int idQuestion;
    private final String user;

    /**
     * Creates a new object for creating a question upvote.
     *
     * @param con
     *            the connection to the database.
     * @param user
     *            the user who voted the question.
     * @param idQuestion
     *            the question to be voted in the database.
     */
    public CreateQuestionUpvoteDatabase(final Connection con, final String user ,final int idQuestion) {
        this.con = con;
        this.idQuestion = idQuestion;
        this.user = user;
    }

    /**
     * @return return true id the question is correctly voted
     * Creates an upvote in the database.
     * @throws SQLException
     *             if any error occurs while storing the answer.
     */
    public boolean createQuestionUpvote() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT_2);
            pstmt.setInt(1, idQuestion);
            pstmt.setString(2,user);

            pstmt.execute();

            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, idQuestion);
            pstmt.setString(2, user);
            pstmt.setInt(3, 1);

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
