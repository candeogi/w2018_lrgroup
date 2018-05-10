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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an answer in the database.
 *
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateAnswerDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO Answer (ID, IDUser, fixed, text, parentID, timestamp) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The answer to be updated in the database
     */
    private final Answer answer;

    /**
     * Creates a new object for creating an answer.
     *
     * @param con
     *            the connection to the database.
     * @param answer
     *            the answer to be created in the database.
     */
    public CreateAnswerDatabase(final Connection con, final Answer answer) {
        this.con = con;
        this.answer = answer;
    }

    /**
     * Creates a answer in the database.
     * @throws SQLException
     *             if any error occurs while storing the answer.
     */
    public void createAnswer() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, answer.getID());
            pstmt.setString(2, answer.getIDUser());
            pstmt.setBoolean(3, answer.isFixed());
            pstmt.setString(4, answer.getText());
            pstmt.setInt(5, answer.getParentID());
            pstmt.setString(6, answer.getTimestamp());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
