package project.rest;

import project.database.CreateCategoryDatabase;
import project.resource.Message;
import project.resource.ResourceList;
import project.resource.Category;
import project.database.SearchCategoryDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RestCategory extends RestResource {
    public RestCategory(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);

    }

    public void searchCategory() throws IOException {
        List<Category> categories;
        Message message;
        try {

            categories = new SearchCategoryDatabase(con).searchCategory();

            if (categories != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(categories).toJSON(res.getOutputStream());
            } else {
                message = new Message("Cannot search category: unexpected error", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                message.toJSON(res.getOutputStream());
            }

        } catch (SQLException e) {
            message = new Message("Cannot search category: unexpected error. DB", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(res.getOutputStream());
        }

    }

    public void addCategory() throws IOException {
        Message m = null;
        Category ans = null;

        try{

            final Category category = Category.fromJSON(req.getInputStream());

            // creates a new object for accessing the database and stores the answer
            new CreateCategoryDatabase(con, category).createCategory();

        }
        catch (Throwable t)
        {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505"))
            {
                m = new Message("Cannot create the category: it already exists.", "E5A2", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            }
            else
            {
                m = new Message("Cannot create the category: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }
}

