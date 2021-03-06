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
 * Retrieves question by ID in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class SearchQuestionByIDDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT * " +
                    "FROM lr_group.Question " +
                    "WHERE id=?";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final int id;


    /**
     * Creates a new object for a specific question retrieval.
     *
     * @param con the connection to the database.
     * @param ID  the ID of a question.
     */
    public SearchQuestionByIDDatabase(final Connection con, final int ID) {
        this.con = con;
        this.id = ID;
    }

    /**
     * Retrieves a specific question in the database.
     *
     * @return a list of {@code Question} (must be one) object matching the ID.
     * @throws SQLException if any error occurs while retrieving the question.
     */
    public List<Question> SearchQuestionByID() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Question> questList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1, id);


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