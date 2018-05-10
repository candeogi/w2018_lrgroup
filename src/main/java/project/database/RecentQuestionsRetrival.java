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
import project.resource.ResourceList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrive recent questions in the database.
 *
 * @author Davide Storato
 * @version 1.00
 * @since 1.00
 */
public final class RecentQuestionsRetrival {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY = 
            "SELECT * " + 
            "FROM Question " +
            "ORDER BY TimeStamp " + "DESC";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Creates a new object for recent questions retrival.
     *
     * @param con
     *            the connection to the database.
     */
    public RecentQuestionsRetrival(final Connection con) {
        this.con = con;
    }

    /**
     * Retrive recent questions in the database.
     *
     * @return An ArrayList of recent questions.
     *
     * @throws SQLException
     *             if any error occurs while retive the questions.
     */
    public ArrayList<Question> findQuestion() throws SQLException {

        PreparedStatement pstmt = null;
        ArrayList<Question> questList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Question q = new Question(rs.getInt("id"), rs.getString("idUser"), rs.getString("title"), rs.getString("body"), rs.getString("ts"), rs.getString("lastModified"));
                questList.add(q);
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return questList;

    }
}