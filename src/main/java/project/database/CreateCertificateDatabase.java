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

import java.sql.*;


/**
 * Creates a certificate in the database.
 *
 * @author Luca Rossi
 * @author Davide Storato
 */
public final class CreateCertificateDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO lr_group.certificate ( id ,name, organization) " +
            "VALUES ( DEFAULT ,?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The certificate to be created in the database
     */
    private final Certificate certificate;

    /**
     * Creates a new object for creating a certificate.
     *
     * @param con         the reference to the connection to the database
     * @param certificate the instance of the certificate to add to the database
     */
    public CreateCertificateDatabase(final Connection con, final Certificate certificate) {
        this.con = con;
        this.certificate = certificate;

    }

    /**
     * Creates a certification in the database.
     *
     * @throws SQLException if any error occurs while storing the question.
     */
    public void createCertificate() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, certificate.getName());
            pstmt.setString(2, certificate.getOrganization());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
