package project.database;

import project.resource.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves answers to a specific question in the database.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class GetCategoriesDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String QUERY =
            "SELECT name " +
                    "FROM lr_group.category ";

    /**
     * The connection to the database
     */
    private final Connection con;


    /**
     * Creates a new object for answers retrieval.
     *
     * @param con
     *            the connection to the database.
     */
    public GetCategoriesDatabase(final Connection con)
    {
        this.con = con;
    }

    /**
     * Retrieves answers for specific question in the database.
     *
     * @return a list of {@code Answer} (must be one) object related to a specific answer.
     *
     * @throws SQLException
     *             if any error occurs while retrieving the answer.
     */
    public List<String> getCategories() throws SQLException
    {

        PreparedStatement pstmt = null;
        List<String> categoriesList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                categoriesList.add(rs.getString("name"));
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

        return categoriesList;

    }
}