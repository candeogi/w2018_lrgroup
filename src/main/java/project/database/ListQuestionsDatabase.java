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
 * Lists all the questions in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class ListQuestionsDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lr_group.Question";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for listing all the questions.
     *
     * @param con the connection to the database.
     */
    public ListQuestionsDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Lists all the questions in the database.
     *
     * @return a list of {@code questions} object.
     * @throws SQLException if any error occurs while searching for questions.
     */
    public List<Question> listQuestions() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Question> questions = new ArrayList<Question>();

        try {
            pstmt = con.prepareStatement(STATEMENT);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                questions.add(new Question(rs.getInt("id"), rs.getString("idUser"),
                        rs.getString("title"), rs.getString("body"),
                        rs.getTimestamp("ts"), rs.getTimestamp("lastModified")));
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

        return questions;
    }
}