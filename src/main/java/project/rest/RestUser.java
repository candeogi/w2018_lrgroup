package project.rest;

import project.database.*;
import project.resource.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.*;
import java.util.List;



/**
 * Manages the REST API for the {@link User} resource.
 *
 * @author lrgroup
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class RestUser extends RestResource
{

    /**
     * Creates a new REST resource for managing {@code User} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public RestUser(final HttpServletRequest req, final HttpServletResponse res, Connection con)
    {
        super(req, res, con);
    }

    /**
     * Creates a new user into the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void createUser() throws IOException
    {
        Message m = null;

        try{

            final User user = User.fromJSON(req.getInputStream());

            // creates a new object for accessing the database and stores the answer
            new CreateUserDatabase(con, user).createUser();

        }
        catch (Throwable t)
        {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505"))
            {
                m = new Message("Cannot create the user: it already exists.", "E5A2", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            }
            else
            {
                m = new Message("Cannot create the user: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }

    /**
     * Updates an user in the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */

    public void updateUser() throws IOException
    {
        Message m = null;

        try{

            final User user = User.fromJSON(req.getInputStream());

            new UpdateUserDatabase(con, user).updateUser();

        }
        catch (Throwable t)
        {
            m = new Message("Cannot update the user: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    /**
     * Deletes an user in the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */

    public void deleteUser() throws IOException
    {
        boolean a;
        Message m = null;

        try{

            final User user = User.fromJSON(req.getInputStream());

            new DeleteUserByUsernameDatabase(con, user.getUsername()).DeleteUserByUsername();

        }
        catch (Throwable t)
        {
            m = new Message("Cannot delete the user: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }




    /**
     * Searches user by an username.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void searchUserByUsername()  throws IOException
    {

        List<User> q  = null;
        Message m = null;

        try
        {
            // parse the URI path to extract the ID
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("id") +2);


            // creates a new object for accessing the database and search the user
            q = new SearchUserByUsernameDatabase(con,path.substring(1)).searchUserByUsername();

            if(q != null)
            {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(q).toJSON(res.getOutputStream());
            }
            else
            {
                // it should not happen
                m = new Message("Cannot search user: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
        catch (Throwable t)
        {
            m = new Message("Cannot search user: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    /**
     * Lists all user from the database
     *
     * @throws IOException
     * 				if any error occurs in the client/server communication.
     *
     */
    public void searchUser() throws IOException {

        List<User> u;
        Message m;

        try{
            // creates a new object for accessing the database and lists all the questions
            u = new SearchUserDatabase(con).searchUser();

            if(u != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(u).toJSON(res.getOutputStream());
            } else {
                // it should not happen
                m = new Message("Cannot list users: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            m = new Message("Cannot search users: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

}
