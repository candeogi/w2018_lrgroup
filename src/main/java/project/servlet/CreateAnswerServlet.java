package project.servlet;

import project.resource.Message;
import project.resource.Answer;
import project.database.CreateAnswerDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;


/**
 * Creates a new user into the database. 
 * 
 * @author Alberto Pontini
 * @author Giovanni Candeo
 * @version 1.00
 * @since 1.00
 */
public final class CreateAnswerServlet extends SessionManagerServlet
{

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

			//parentID is the id of the answer this answer refers to
			//for now can only answer to questions
			//TODO answers to answers.
			//parentID = null;
			//parentID = Integer.parseInt(req.getParameter("parentID"));

			//retrieves the username answering through the session attribute
			IDUser = (String) req.getSession().getAttribute("loggedInUser");



			a = new Answer(IDUser,false, text, -1 ,new Timestamp((Long)System.currentTimeMillis()*1000));

			// creates a new object for accessing the database and stores the answer
			new CreateAnswerDatabase(getDataSource().getConnection(), a).createAnswer();

			m = new Message(String.format("Answer successfully created."));
		} catch (SQLException ex) {
			m = new Message("Cannot create the answer: unexpected error while accessing the database.",
					"E200", ex.getMessage());
		}
		
		// stores the user and the message as a request attribute
		req.setAttribute("answer", a);
		req.setAttribute("message", m);
		
		// forwards the control to the create-user-result JSP
		req.getRequestDispatcher("/jsp/create-answer-result.jsp").forward(req, res);
	}

}
