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
import java.util.StringTokenizer;

/**
 * Retrieves questions by keyword in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class SearchQuestionByKeywordDatabase {

    /**
     * The SQL statement to be executed
     */
//    private static final String QUERY =
//            "SELECT * " +
//                    "FROM lr_group.Question " +
//                    "WHERE title " +
//                    "ILIKE ? ";

    private static final String QUERY =
            "SELECT * " +
                    "FROM lr_group.Question " +
                    "WHERE lower(title) " +
                    "SIMILAR TO ? ";


    /**
     * The connection to the database
     */
    private final Connection con;
    private String keyword;

    /**
     * Creates a new object for questions retrieval.
     *
     * @param con the connection to the database.
     * @param keyword the question searched
     */
    public SearchQuestionByKeywordDatabase(final Connection con, String keyword) {
        this.con = con;
        this.keyword = keyword;
    }

    /**
     * Retrieves questions by keyword in the database.
     *
     * @return An ArrayList of recent questions.
     * @throws SQLException if any error occurs while retrieving the questions.
     */
    public List<Question> searchQuestionByKeyword() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Question> questList = new ArrayList<>();

        keyword = keyword.toLowerCase();

        keyword = keyword.substring(1);
        keyword = keyword.substring(0, keyword.length() - 1);
        System.out.println(keyword);

        String[] parts = keyword.split("%20");

        if (parts.length > 0) {

            keyword = "%(";

            for (int i = 0; i < parts.length; i++) {

                keyword += parts[i];
                if (i == parts.length - 1) {
                    keyword += ")%";
                } else {
                    keyword += "|";
                }

            }

        }


        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, keyword);

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