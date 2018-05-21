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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;


/**
 * Updates a user's informations (except password)
 * 
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */
public final class UpdateUserServlet extends AbstractDatabaseServlet
{

	/**
	 * Updates a user's informations (except password)
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
		String name = null;
		String surname = null;
		String username = null;
		String email = null;
		String bdate = null;
		String description = null;
		Part photoProfile = null;

		// model
		User u  = null;
		Message m = null;

		try
		{
			// retrieves the request parameters
			username = req.getParameter("username");

			name = req.getParameter("name");
			if(name.equals("")) name = req.getParameter("currentName");

			surname = req.getParameter("surname");
			if(surname.equals("")) surname = req.getParameter("currentSurname");

			email = req.getParameter("email");
			if(email.equals("")) email = req.getParameter("currentEmail");

			bdate = req.getParameter("bdate");
			if(bdate.equals("") || bdate.equals("mm/dd/yyy")) bdate = req.getParameter("currentBdate");

			description = req.getParameter("description");
			if(description.equals("")) description = req.getParameter("currentDescription");

			/*photoProfile = req.getPart("photoProfile");
			if(photoProfile == null)
			{
					photoProfile = req.getParameter("currentPhotoProfile");
					byte[] picToSet = photoProfile.getBytes(); //Not too sure about this
			}
			else
			{
				InputStream fileContent = photoProfile.getInputStream();

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[51200]; //Max image size is 500KB

				while ((nRead = fileContent.read(data, 0, data.length)) != -1)
				{
				  buffer.write(data, 0, nRead);
				}

				buffer.flush();

				byte[] picToSet = buffer.toByteArray();
			}*/

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Date birthday = new Date(dateFormat.parse(bdate).getTime());
			
			u = new User(email, name, surname, username, null ,"", null, birthday, description);

			// creates a new object for accessing the database and updates the user
			new UpdateUserDatabase(getDataSource().getConnection(), u).updateUser();
		}
		catch(NumberFormatException e)
		{
			m = new Message("Birthday not correct","E300", e.getMessage());
		}
		catch(ParseException pe)
		{
			//Should not happen
			m = new Message("Birthday not correct","E300", pe.getMessage());
		}
		catch (SQLException ex)
		{
			m = new Message("Cannot update the user: unexpected error while accessing the database.", 
					"E200", ex.getMessage());
		}
		
		req.setAttribute("message", m);
		req.getRequestDispatcher("/?p=user&u=" + username).forward(req, res);
	}

}
