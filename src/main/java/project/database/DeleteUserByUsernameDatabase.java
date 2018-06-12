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


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Deletes user by username from the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class DeleteUserByUsernameDatabase
{

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "DELETE " +
                    "FROM lr_group.utente " +
                    "WHERE EXISTS " +
                    "(SELECT 1 " +
                    " FROM lr_group.have" +
                    " WHERE idanswer = lr_group.answer.id " +
                    " AND idquestion = ?) ";

    private static final String QUERY_2 =
            "DELETE " +
                    "FROM lr_group.Question " +
                    "WHERE id=? "; //TODO query CASCADE?

    /**
     * The connection to the database
     */
    private final Connection con;
    private final String username;


    /**
     * Creates a new object for a specific user retrieval.
     *
     * @param con
     *            the connection to the database.
     * @param username
     *            the username of an user.
     */
    public DeleteUserByUsernameDatabase(final Connection con, final String username)
    {
        this.con = con;
        this.username = username;
    }

    /**
     * Deletes a specific user in the database.
     **
     * @throws SQLException
     *             if any error occurs while deleting the user.
     */
    public int DeleteUserByUsername() throws SQLException
    {

        PreparedStatement pstmt = null;
        int rs = 0;

        try {
            /*pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1, u);
            rs = pstmt.executeUpdate();


            pstmt = con.prepareStatement(QUERY_2);
            pstmt.setInt(1, id);
            rs = pstmt.executeUpdate();*/



        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return rs;

    }
}