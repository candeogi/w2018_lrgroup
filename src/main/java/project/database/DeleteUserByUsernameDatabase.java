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
public final class DeleteUserByUsernameDatabase {

    /**
     * The SQL statement to be executed
     */
//    private static final String QUERY =
//            "DELETE " +
//                    "FROM lr_group.utente " +
//                    "WHERE EXISTS " +
//                    "(SELECT 1 " +
//                    " FROM lr_group.have" +
//                    " WHERE idanswer = lr_group.answer.id " +
//                    " AND idquestion = ?) ";
//
//    private static final String QUERY_2 =
//            "DELETE " +
//                    "FROM lr_group.U " +
//                    "WHERE id=? "; //TODO query CASCADE?


    private static final String QUERY_1 = "DELETE FROM lr_group.Utente WHERE username=? "; //TODO query CASCADE?

    private static final String STATEMENT = " UPDATE lr_group.Question SET idUser = 'userDeleted' WHERE idUser=? ";
    private static final String STATEMENT1 = " UPDATE lr_group.Answer SET idUser = 'userDeleted' WHERE idUser=? ";
    private static final String STATEMENT2 = " DELETE FROM lr_group.HaveCertificate WHERE idUser=? ";
    private static final String STATEMENT3 = " DELETE FROM lr_group.Own WHERE idUser=? ";
    /**
     * The connection to the database
     */
    private final Connection con;
    private final String username;


    /**
     * Creates a new object for a specific user retrieval.
     *
     * @param con      the connection to the database.
     * @param username the username of an user.
     */
    public DeleteUserByUsernameDatabase(final Connection con, final String username) {
        this.con = con;
        this.username = username;
    }

    /**
     * Deletes a specific user in the database.
     * *
     *
     * @throws SQLException if any error occurs while deleting the user.
     */
    public int DeleteUserByUsername() throws SQLException {

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;

        int rs = 0;

        try {

            pstmt1 = con.prepareStatement(STATEMENT);
            pstmt1.setString(1, username);
            pstmt1.executeUpdate();

            pstmt2 = con.prepareStatement(STATEMENT1);
            pstmt2.setString(1, username);
            pstmt2.executeUpdate();

            pstmt3 = con.prepareStatement(STATEMENT2);
            pstmt3.setString(1, username);
            pstmt3.executeUpdate();

            pstmt4 = con.prepareStatement(STATEMENT3);
            pstmt4.setString(1, username);
            pstmt4.executeUpdate();

            pstmt = con.prepareStatement(QUERY_1);
            pstmt.setString(1, username);
            rs = pstmt.executeUpdate();


        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (pstmt1 != null) {
                pstmt1.close();
            }
            if (pstmt2 != null) {
                pstmt2.close();
            }
            if (pstmt3 != null) {
                pstmt3.close();
            }
            if (pstmt4 != null) {
                pstmt4.close();
            }

            con.close();
        }

        return rs;

    }
}