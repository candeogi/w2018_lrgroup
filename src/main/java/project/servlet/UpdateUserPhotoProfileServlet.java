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
import javax.servlet.annotation.MultipartConfig;


/**
 * Updates a user's PhotoProfile
 * 
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */

@MultipartConfig(fileSizeThreshold=1024*1024, 	// 1 MB 
                 maxFileSize=1024*1024,      	// 1 MB
                 maxRequestSize=1024*1024*10)   // 10 MB
public final class UpdateUserPhotoProfileServlet extends AbstractDatabaseServlet
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
		Part photoProfile = null;
		String username = null;

		// model
		User u  = null;
		Message m = null;

		try
		{
			// retrieves the request parameter
			username = req.getParameter("username");
			photoProfile = req.getPart("photoProfile");
			byte[] picToSet = null;
			if(photoProfile == null)
			{
					//photoProfile = req.getParameter("currentPhotoProfile");
					//byte[] picToSet = photoProfile.getBytes(); //Not too sure about this
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

				picToSet = buffer.toByteArray();
			}
			
			u = new User(null, null, null, username, picToSet ,"", null, null, null);

			// creates a new object for accessing the database and updates the user
			new UpdateUserPhotoProfileDatabase(getDataSource().getConnection(), u).updateUserPhotoProfile();
		}
		catch (SQLException ex)
		{
			m = new Message("Cannot upload image file: unexpected error while accessing the database.", 
					"E200", ex.getMessage());
		}
		
		
		if(m!=null && m.isError())
		{
			req.setAttribute("message", m);
			req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
		}
		else
		{
			res.sendRedirect(req.getContextPath() + "/?p=user&u=" + username);
		}
	}

}
