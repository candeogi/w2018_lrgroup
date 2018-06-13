package project.database;

import project.resource.Category;

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
            "SELECT * " +
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
    public List<Category> getCategories() throws SQLException
    {

        PreparedStatement pstmt = null;
        List<Category> categoriesList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(QUERY);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Category category = new Category(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getBoolean("isCompany"));
                categoriesList.add(category);
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