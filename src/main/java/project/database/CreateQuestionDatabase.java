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

import project.resource.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a question in the database.
 *
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateQuestionDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO Question (id, title, idUser, ts, lastModified, body) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The question to be updated in the database
     */
    private final Question question;

    /**
     * Creates a new object for creating a question.
     *
     * @param con
     *            the connection to the database.
     * @param question
     *            the user to be created in the database.
     */
    public CreateQuestionDatabase(final Connection con, final Question question) {
        this.con = con;
        this.question = question;
    }

    /**
     * Creates a question in the database.
     * @throws SQLException
     *             if any error occurs while storing the question.
     */
    public void createQuestion() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            //pstmt.setInt(1, question.getID());
            pstmt.setString(1, question.getTitle());
            pstmt.setString(2, question.getIDUser());
            pstmt.setTimestamp(3, question.getTimestamp());
            pstmt.setTimestamp(4, question.getLastModified());
            pstmt.setString(5, question.getBody());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
