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

import project.resource.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves questions by user in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class SearchQuestionByUserDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT * " +
                    "FROM lr_group.Question " +
                    "WHERE iduser=?";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final String iduser;


    /**
     * Creates a new object for questions made by an user.
     *
     * @param con    the connection to the database.
     * @param IDUser the ID of an user.
     */
    public SearchQuestionByUserDatabase(final Connection con, final String IDUser) {
        this.con = con;
        this.iduser = IDUser;
    }

    /**
     * Retrieve questions made by an user in the database.
     *
     * @return An ArrayList of user's questions.
     * @throws SQLException if any error occurs while retrieving the user's questions.
     */
    public List<Question> SearchQuestionByUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Question> questList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, iduser);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Question q = new Question(rs.getInt("id"), rs.getString("idUser"), rs.getString("title"), rs.getString("body"), rs.getTimestamp("ts"), rs.getTimestamp("lastModified"));
                questList.add(q);
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

        return questList;

    }
}