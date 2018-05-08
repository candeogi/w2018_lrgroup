package project.servlet;

import project.resource.Question;
import project.resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

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
public final class CreateQuestionServlet extends HttpServlet{ //extends AbstractDatabaseServlet {

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
		String title = null;
		String body = null;

		// model
		Question q  = null;
		Message m = null;

		//try{
			// retrieves the request parameters
			title = req.getParameter("title");
			body = req.getParameter("body");

			// creates a new user from the request parameters
			q = new Question(title, body, ((Long)System.currentTimeMillis()).toString());

			// creates a new object for accessing the database and stores the user
			//new CreateEmployeeDatabase(getDataSource().getConnection(), e).createEmployee();
			
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
		req.setAttribute("question", q);
		req.setAttribute("message", m);
		
		// forwards the control to the create-user-result JSP
		req.getRequestDispatcher("/jsp/create-question-result.jsp").forward(req, res);
	}

}
