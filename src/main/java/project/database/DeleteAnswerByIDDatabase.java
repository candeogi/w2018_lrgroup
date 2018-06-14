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
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 * Deletes answer by ID from the database.
 *
 * @author lrgroup
 * @author Alberto Pontini
 */
public final class DeleteAnswerByIDDatabase
{

    /**
     * The SQL statement to be executed
     */

    private static final String QUERY =
            "DELETE " +
                    "FROM lr_group.Answer " +
                    "WHERE id=? ";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final int id;


    /**
     * Creates a new object for a specific answer retrieval.
     *
     * @param con
     *            the connection to the database.
     * @param ID
     *            the ID of an answer.
     */
    public DeleteAnswerByIDDatabase(final Connection con, final int ID)
    {
        this.con = con;
        this.id = ID;
    }

    /**
     * Deletes a specific answer in the database.
     ** @return true when the deleting is correctly done
     * @throws SQLException
     *             if any error occurs while deleting the answer.
     */
    public boolean deleteAnswerByID() throws SQLException
    {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) return true;
            else return false;

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }
    }
}