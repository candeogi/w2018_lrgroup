package project.servlet;

import project.resource.Question;
import project.resource.Message;
import project.database.CreateQuestionDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Creates a new question into the database. <p>
 * The title and body of the question is provided by the request through the form.
 * The user writing the question is obtained through the active session.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
 */
public final class CreateQuestionServlet extends SessionManagerServlet
{

	/**
	 * Creates a new question into the database.
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
		String IDUser;

		// model
		Question q  = null;
		Message m = null;

		try{
			// retrieves the request parameters
			title = req.getParameter("title");
			body = req.getParameter("body");

			// retrieves the user id (username) through the session parameter
			IDUser = (String) req.getSession().getAttribute("loggedInUser");

			// creates a new question from the request parameters
			q = new Question(IDUser, title, body, new Timestamp((Long)System.currentTimeMillis()));

			// creates a new object for accessing the database and stores the question
			new CreateQuestionDatabase(getDataSource().getConnection(), q).createQuestion();
		}catch (SQLException ex) {
			m = new Message("Cannot create the user: unexpected error while accessing the database.",
					"E200", ex.getMessage());

		}
		
		// stores the user and the message as a request attribute
		req.setAttribute("question", q);
		req.setAttribute("message", m);
		
		// forwards the control to the create-user-result JSP
		req.getRequestDispatcher("/jsp/create-question-result.jsp").forward(req, res);
	}

}
