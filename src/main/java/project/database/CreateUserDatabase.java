package project.database;

import project.resource.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an user in the database.
 *
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateUserDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "" +
            "INSERT INTO Utente (email, name, surname, username, photoProfile, password, registrationDate, birthday, description) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The user to be updated in the database
     */
    private final User user;

    /**
     * Creates a new object for creating an user.
     *
     * @param con
     *            the connection to the database.
     * @param user
     *            the user to be created in the database.
     */
    public CreateUserDatabase(final Connection con, final User user)
    {
        this.con = con;
        this.user = user;
    }

    /**
     * Creates a user in the database.
     * @throws SQLException
     *             if any error occurs while storing the user.
     */
    public void createUser() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, user.getPhotoProfile());
            pstmt.setString(6, user.getPassword());
            pstmt.setDate(7, user.getRegistrationDate()); //TODO check date format on database
            pstmt.setDate(8, user.getBirthday()); //TODO check date format on database
            pstmt.setString(9, user.getDescription());

            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }
}
