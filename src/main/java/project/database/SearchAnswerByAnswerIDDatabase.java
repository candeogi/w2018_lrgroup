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
import project.resource.ResourceList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves answers to a specific question in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class SearchAnswerByAnswerIDDatabase
{

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT * " +
                    "FROM lr_group.answer " +
                    "WHERE parentID=?" +
                    "ORDER BY ts";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final int answerID;


    /**
     * Creates a new object for answers retrieval.
     *
     * @param con
     *            the connection to the database.
     * @param questionid
     *            the id of the related question.
     */
    public SearchAnswerByAnswerIDDatabase(final Connection con, final int answerid)
    {
        this.con = con;
        this.answerID = answerid;
    }

    /**
     * Retrieves answers for specific question in the database.
     *
     * @return a list of {@code Answer} (must be one) object related to a specific answer.
     *
     * @throws SQLException
     *             if any error occurs while retrieving the answer.
     */
    public List<Answer> searchAnswerByAnswerID() throws SQLException
    {

        PreparedStatement pstmt = null;
        List<Answer> answerList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1, answerID);


            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Answer a = new Answer(rs.getString("iduser"), rs.getBoolean("isfixed"), rs.getString("body"), rs.getInt("parentid"), rs.getTimestamp("ts"),-1);
                answerList.add(a);
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return answerList;

    }
}