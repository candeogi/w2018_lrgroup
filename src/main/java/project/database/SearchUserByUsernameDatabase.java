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
import project.resource.ResourceList;
import project.resource.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Search user by username.
 * Used for the login.
 *
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */
public final class SearchUserByUsernameDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "SELECT *" +
            "FROM lr_group.Utente " +
            "WHERE username=?";

    /**
     * The connection to the database
     */
    private final Connection con;

    private final String username;


    /**
     * Creates a new object for searching user by username.
     *
     * @param con
     *            the connection to the database.
     * @param username
     *            the username of the user.
     */
    public SearchUserByUsernameDatabase(final Connection con, final String username)
    {
        this.con = con;
        this.username = username;
    }

    /**
     * Searches user by username.
     *
     * @return a list of {@code User} (must be one) object matching the username.
     *
     * @throws SQLException
     *             if any error occurs while searching for users.
     */
    public List<User> searchUserByUsername() throws SQLException
    {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<User> users = new ArrayList<User>();

        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("photoProfile"),
                        rs.getString("password"),
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