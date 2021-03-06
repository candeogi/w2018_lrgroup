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

import project.resource.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves answers from an user in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class SearchAnswerByUserIDDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT * " +
                    "FROM lr_group.Answer " +
                    "WHERE iduser=?";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final String username;


    /**
     * Creates a new object for answers retrieval.
     *
     * @param con  the connection to the database.
     * @param user the user related to answers
     */
    public SearchAnswerByUserIDDatabase(final Connection con, final String user) {
        this.con = con;
        this.username = user;
    }

    /**
     * Retrieves answers of a specific user in the database.
     *
     * @return a list of {@code Answer} (must be one) object related to an user.
     * @throws SQLException if any error occurs while retrieving the answer.
     */
    public List<Answer> searchAnswerByUserID() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Answer> answerList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            while (rs.next()) {//TODO : verify this -1
                Answer a = new Answer(rs.getInt("id"), rs.getString("iduser"), rs.getBoolean("isfixed"), rs.getString("body"), rs.getInt("parentid"), rs.getTimestamp("ts"), -1);
                answerList.add(a);
            }


        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }

            con.close();
        }

        return answerList;

    }
}