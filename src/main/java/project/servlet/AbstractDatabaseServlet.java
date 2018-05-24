package project.servlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import javax.servlet.ServletContext;


/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 *
 * @author lrgroup
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 */
public abstract class AbstractDatabaseServlet extends HttpServlet
{

	/**
	 * The connection pool to the database.
	 */
	private DataSource ds;
    private ServletContext sc;

	/**
     * Gets the {@code DataSource} for managing the connection pool to the database.
     *
	 * @param config
     *          a {@code ServletConfig} object containing the servlet's
     *          configuration and initialization parameters.
	 *
	 * @throws ServletException
     *          if an exception has occurred that interferes with the servlet's normal operation
	 */
    public void init(ServletConfig config) throws ServletException {

        // the JNDI lookup context
        InitialContext cxt;
        sc = config.getServletContext();

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/user-webapp");
        } catch (NamingException e) {
            ds = null;

            throw new ServletException(
                    String.format("Impossible to access the connection pool to the database: %s",
                                  e.getMessage()));
        }
    }

    /**
     * Releases the {@code DataSource} for managing the connection pool to the database.
     */
    public void destroy() {
        ds = null;
    }

    /**
     * Returns the {@code DataSource} for managing the connection pool to the database.
     *
     * @return the {@code DataSource} for managing the connection pool to the database
     */
    protected final DataSource getDataSource() {
        return ds;
    }

    public final ServletContext getServletContext() {
        return sc;
    }

}
