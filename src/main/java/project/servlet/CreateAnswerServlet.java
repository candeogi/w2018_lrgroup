package project.servlet;

import project.resource.User;
import project.resource.Message;
import project.resource.Answer;

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
public final class CreateAnswerServlet extends HttpServlet{ //extends AbstractDatabaseServlet {

	/**
	 * Creates a new answer into the database. 
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
		String text = null;
		int parentID;
		String IDUser;

		// model
		Answer a  = null;
		Message m = null;

		try
		{
			// retrieves the request parameters
			text = req.getParameter("text");
			parentID = Integer.parseInt(req.getParameter("parentID"));
			IDUser = req.getParameter("IDUser");


			m = new Message(String.format("Answer successfully created."));
			String now = new Date(((long)System.currentTimeMillis()*1000)).toString();
			a = new Answer(IDUser,false, text, parentID,now);
			// creates a new object for accessing the database and stores the user     <---------AGGIUNGERE!
			//new CreateEmployeeDatabase(getDataSource().getConnection(), e).createEmployee();
		}
		catch(NumberFormatException n)
		{
			m = new Message("Error, parentID must be an integer", "Qualche codice errore", n.getMessage());
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
		req.setAttribute("answer", a);
		req.setAttribute("message", m);
		
		// forwards the control to the create-user-result JSP
		req.getRequestDispatcher("/jsp/create-answer-result.jsp").forward(req, res);
	}

}
