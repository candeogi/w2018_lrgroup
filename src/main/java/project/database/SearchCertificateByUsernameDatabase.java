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

import project.resource.Certificate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves question by ID in the database.
 *
 * @author lrgroup
 * @author Luca Rossi
 */

public class SearchCertificateByUsernameDatabase {


    /**
     * The SQL statement to be executed
     */


    private static final String QUERY = "SELECT C.name, C.organization, H.achievementDate ,C.id FROM " +
            "lr_group.HaveCertificate AS H INNER JOIN lr_group.Certificate AS C ON H.id = C.id "
            + "WHERE username=?";


    /**
     * The connection to the database
     */
    private final Connection con;
    private final String username;


    public SearchCertificateByUsernameDatabase(final Connection con, final String username) {
        this.con = con;
        this.username = username;
    }

    /**
     * Retrieve certificate obtained by an user in the database.
     *
     * @return An ArrayList of user's certificate.
     * @throws SQLException if any error occurs while retrieving the user's questions.
     */
    public List<Certificate> SearchCertificateByUser() throws SQLException {

        PreparedStatement pstmt = null;
        List<Certificate> questList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Certificate c = new Certificate(rs.getInt("id"),rs.getString("name"), rs.getString("organization"), (rs.getDate("achievementDate")).toString() );
                questList.add(c);
            }
            rs.close();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return questList;

    }

}

