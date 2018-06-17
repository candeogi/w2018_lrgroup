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

import project.resource.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Search user by email and password.
 * Used for the login.
 *
 * @author lrgroup
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 */
public final class SearchUserByUsernameAndPasswordDatabase {

    /**
     * The SQL statement to be executed
     */
    /*private static final String STATEMENT = "" +
            "SELECT *" +
            "FROM lr_group.Utente " +
            "WHERE username=? and password=?";*/
    private static final String STATEMENT = "" +
            "SELECT * " +
            "FROM lr_group.Utente " +
            "WHERE username=? and password=lr_group.crypt(?,password)";

    /**
     * STATEMENT FOR DBSTUD
     * private static final String STATEMENT = "" +
            "SELECT * " +
            "FROM lr_group.Utente " +
            "WHERE username=? and password=crypt(?,password)";**/
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * Username and Password of the User
     */
    private final String username;
    private final String password;


    /**
     * Creates a new object for searching user by username and password.
     *
     * @param con      the connection to the database.
     * @param username the username of the user.
     * @param password the password of the user.
     */
    public SearchUserByUsernameAndPasswordDatabase(final Connection con, final String username, final String password) {
        this.con = con;
        this.username = username;
        this.password = password;
    }

    /**
     * Searches user by username and password.
     *
     * @return a list of {@code User} object matching the username and password.
     * @throws SQLException if any error occurs while searching for users.
     */
    public List<User> searchUserByUsernameAndPassword() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<User> users = new ArrayList<User>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("photoProfile"),
                        rs.getString("password"),
                        rs.getBoolean("isAdmin"),
                        rs.getDate("registrationDate"),
                        rs.getDate("birthday"),
                        rs.getString("description")));
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

        return users;
    }
}