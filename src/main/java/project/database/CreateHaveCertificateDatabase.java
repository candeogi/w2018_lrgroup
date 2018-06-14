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

/**
 * Create HaveCertificate.
 *
 * @author lrgroup
 * @author Luca Rossi
 */

public class CreateHaveCertificateDatabase {


    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.HaveCertificate (username, id, achievementDate) " +
            "VALUES (?, ?,?)";

    private static final String STATEMENT2 = "" +
            "INSERT INTO lr_group.Certificate ( id , name, organization) " +
            "VALUES (DEFAULT ,?,?)";


    private static final String QUERY = "" +
            "SELECT id FROM lr_group.Certificate WHERE name=? " + " AND organization=? ";


    /**
     * The connection to the database
     */
    private final Connection con;
    private final String username;
    private final String name;
    private final String organization;
    private final Date achievementDate;


    public CreateHaveCertificateDatabase(final Connection con, String username, String name, String organization, Date achievementDate) {
        this.con = con;
        this.username = username;
        this.name = name;
        this.organization = organization;
        this.achievementDate = achievementDate;
    }

    /**
     * Creates a answer in the database.
     *
     * @throws SQLException if any error occurs while storing the answer.
     */
    public void createHaveCertificate() throws SQLException {

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        ResultSet rs = null;
        int idCert = -1;


        try {

            pstmt1 = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt1.setString(1, name);
            pstmt1.setString(2, organization);
            rs = pstmt1.executeQuery();

            if (rs.next()) {
                idCert = rs.getInt("id");
            }

            if (idCert == -1) {

                pstmt2 = con.prepareStatement(STATEMENT2, Statement.RETURN_GENERATED_KEYS);
                pstmt2.setString(1, name);
                pstmt2.setString(2, organization);
                pstmt2.executeUpdate();

                ResultSet generatedKey = pstmt2.getGeneratedKeys();
                if (generatedKey.next()) {
                    idCert = generatedKey.getInt("id");
                }
                generatedKey.close();
            }

            pstmt = con.prepareStatement(STATEMENT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setInt(2, idCert);
            pstmt.setDate(3, achievementDate);
            pstmt.execute();


        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (pstmt1 != null) {
                pstmt1.close();
            }
            if (pstmt2 != null) {
                pstmt2.close();
            }

            con.close();
        }


    }


}
