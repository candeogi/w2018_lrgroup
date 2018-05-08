package project.servlet;

import project.resource.User;
import project.resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;


/**
 * Creates a new user into the database. 
 * 
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */
public final class CreateUserServlet extends HttpServlet{ //extends AbstractDatabaseServlet {

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

		int bday;
		int bmonth;
		int byear;

		// model
		User u  = null;
		Message m = null;

		try
		{
			// retrieves the request parameters
			username = req.getParameter("username");
			password = req.getParameter("password");
			password2 = req.getParameter("password2");
			name = req.getParameter("name");
			surname = req.getParameter("surname");
			email = req.getParameter("email");
			bdate = req.getParameter("bdate");
			String[] split = bdate.split("-");
			bday = Integer.parseInt(split[2]);
			bmonth = Integer.parseInt(split[1]);
			byear = Integer.parseInt(split[0]);

			// creates a new user from the request parameters
			if(!password2.equals(password))
			{
				m = new Message("Passwords do not match","E300", "--");
			}
			else
			{
				m = new Message(String.format("User %s successfully created.", username));
			}
			Date regDate = new Date(((long)System.currentTimeMillis()*1000));
			u = new User(username, "", name, surname, email, regDate, null, new Date(byear,bmonth,bday));
			// creates a new object for accessing the database and stores the user     <---------AGGIUNGERE!
			//new CreateEmployeeDatabase(getDataSource().getConnection(), e).createEmployee();
		}
		catch(NumberFormatException e)
		{
			m = new Message("Birthday not correct","E300", e.getMessage());
		}
		/*} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message(String.format("Cannot create the user: user %s already exists.", username),
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the user: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}*/
		
		// stores the user and the message as a request attribute
		req.setAttribute("user", u);
		req.setAttribute("message", m);
		
		// forwards the control to the create-user-result JSP
		req.getRequestDispatcher("/jsp/create-user-result.jsp").forward(req, res);
	}

}
