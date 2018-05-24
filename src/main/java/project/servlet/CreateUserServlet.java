package project.servlet;

import project.resource.User;
import project.resource.Message;
import project.database.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;



/**
 * Creates a new user into the database. 
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 */
public final class CreateUserServlet extends AbstractDatabaseServlet
{

	/**
	 * Creates a new user into the database. 
	 * 
	 * @param req
	 *            the HTTP request from the client.
	 * @param res
	 *            the HTTP response from the server.
	 * 
	 * @throws ServletException
	 *             if any error occurs while executing the servlet.
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {		

		// request parameters
		String username = null;
		String password = null;
		String password2 = null;
		String name = null;
		String surname = null;
		String email = null;
		String bdate;

		// model
		User u  = null;
		Message m = null;

		try
		{
			username = req.getParameter("username");
			password = req.getParameter("password");
			password2 = req.getParameter("password2");
			name = req.getParameter("name");
			surname = req.getParameter("surname");
			email = req.getParameter("email");
			bdate = req.getParameter("bdate");

			if(!password2.equals(password))
			{
				m = new Message("Passwords do not match","E300", "--");
			}
			else
			{
				m = new Message(String.format("User %s successfully created.", username));
			}
			Date regDate = new Date(((long)System.currentTimeMillis()*1000));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Date birthday = new Date(dateFormat.parse(bdate).getTime());

    		String photoProfile = "";
    		String str = "";
			ServletContext context = getServletContext();
    		InputStream is = context.getResourceAsStream("/resources/defaultBase64PhotoProfile");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if (is != null)
            {                            
                while ((str = reader.readLine()) != null)
                {    
                    photoProfile = photoProfile + str;
                }                
            }
			
			u = new User(email, name, surname, username, photoProfile, password, regDate, birthday, "");

			new CreateUserDatabase(getDataSource().getConnection(), u).createUser();
		}
		catch(NumberFormatException e)
		{
			m = new Message("Birthday not correct","E300", e.getMessage());
		}
		catch (SQLException ex)
		{
			if (ex.getSQLState().equals("23505")) {
				m = new Message(String.format("Cannot create the user: user %s already exists.", username),
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the user: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		catch(ParseException pe)
		{
			//Should not happen
			m = new Message("Birthday not correct","E300", pe.getMessage());
		}
		catch(IOException ioe)
		{
			m = new Message("Error occured while parsing default photo profile", "Qualche codice", ioe.getMessage());
		}
		
		req.setAttribute("user", u);
		req.setAttribute("message", m);
		
		req.getRequestDispatcher("/jsp/create-user-result.jsp").forward(req, res);
	}

}
