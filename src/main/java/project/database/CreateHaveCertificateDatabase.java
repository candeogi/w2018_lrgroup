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

import java.sql.*;

public class CreateHaveCertificateDatabase {


    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.HaveCertificate (username, name, organization) " +
            "VALUES (?,?,?)";


    /**
     * The connection to the database
     */
    private final Connection con;

    private final String username;
    private final String name;
    private final String organization;


    public CreateHaveCertificateDatabase(final Connection con, String username, String name, String organization) {
        this.con = con;
        this.username = username;
        this.name = name;
        this.organization = organization;

    }

    /**
     * Creates a answer in the database.
     *
     * @throws SQLException if any error occurs while storing the answer.
     */
    public void createHaveCertificate() throws SQLException {

        PreparedStatement pstmt = null;
        int insertedKey = -1;

        try {
            pstmt = con.prepareStatement(STATEMENT, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3, organization);

            pstmt.execute();


        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }


    }


}
