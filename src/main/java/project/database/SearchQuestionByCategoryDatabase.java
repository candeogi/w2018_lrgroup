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
 * Retrieves questions with a specified category in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class SearchQuestionByCategoryDatabase
{

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT Q.* " +
                    "FROM (lr_group.Category AS C " +
                    "INNER JOIN lr_group.Below AS B " +
                    "ON C.id = B.idcategory) AS T " +
                    "INNER JOIN lr_group.Question AS Q " +
                    "ON T.question = Q.id " +
                    "WHERE T.id=?";


    /**
     * The connection to the database
     */
    private final Connection con;
    private final int categoryID;


    /**
     * Creates a new object for questions with a specified category.
     *
     * @param con
     *            the connection to the database.
     * @param categoryID
     *            the categoryID of a question.
     */
    public SearchQuestionByCategoryDatabase(final Connection con, final int categoryID)
    {
        this.con = con;
        this.categoryID = categoryID;
    }

    /**
     * Retrieve questions with a specified category in the database.
     *
     * @return An ArrayList of questions.
     *
     * @throws SQLException
     *             if any error occurs while retrieving the questions.
     */
    public List<Question> searchQuestionByCategory() throws SQLException
    {

        PreparedStatement pstmt = null;
        List<Question> questList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1, categoryID);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Question q = new Question(rs.getInt("id"), rs.getString("idUser"), rs.getString("title"), rs.getString("body"), rs.getTimestamp("ts"), rs.getTimestamp("lastModified"));
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