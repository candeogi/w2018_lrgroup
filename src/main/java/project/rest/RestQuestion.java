package project.rest;

import project.database.*;
import project.resource.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.io.*;


/**
 * Manages the REST API for the {@link Question} resource.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class RestQuestion extends RestResource
{

	/**
	 * Creates a new REST resource for managing {@code Question} resources.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public RestQuestion(final HttpServletRequest req, final HttpServletResponse res, Connection con)
	{
		super(req, res, con);
	}

	/**
	 * Creates a new question into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void createQuestion() throws IOException
	{
		//NOT NEEDED FOR NOW, just in case for future implementations
		Question q  = null;
		Message m = null;

		try{

			final Question question =  Question.fromJSON(req.getInputStream());

			// creates a new object for accessing the database and stores the question
			q = null; //new CreateQuestionDatabase(con, question).createQuestion(); COMMENTATA PER I TEST

			if(q != null)
			{
				res.setStatus(HttpServletResponse.SC_CREATED);
				q.toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot create the question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505"))
			{
				m = new Message("Cannot create the question: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			}
			else
			{
				m = new Message("Cannot create the question: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	/**
	 * Searches question by its ID.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchQuestionByID()  throws IOException
	{

		List<Question> q  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the ID
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("id") + 2);

			final int questionID = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the question
			q = new SearchQuestionByIDDatabase(con, questionID).SearchQuestionByID();

			if(q != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(q).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches question by user.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchQuestionByUser()  throws IOException
	{

		List<Question> q  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the ID
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("user") + 4);

			final String idUser = path.substring(1);


			// creates a new object for accessing the database and search the question
			q = new SearchQuestionByUserDatabase(con, idUser).SearchQuestionByUser();

			if(q != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(q).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches question by its timestamp.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchQuestionByTimestamp()  throws IOException
	{

		List<Question> ql  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the timestamp
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("timestamp") + 9);

			//final long timestamp = Long.parseLong(path.substring(1));


			// creates a new object for accessing the database and search the question
			ql = new SearchQuestionByTimestampDatabase(con).SearchQuestionByTimestamp();

			if(ql != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(ql).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Lists all questions from the database
	 *
	 * @throws IOException
	 * 				if any error occurs in the client/server communication.
	 *
	 */
	public void listQuestions() throws IOException {

		List<Question> q = null;
		Message m = null;

		try{
			// creates a new object for accessing the database and lists all the questions
			q = new ListQuestionsDatabase(con).listQuestions();

			if(q != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(q).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot list question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches question by category.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchQuestionByCategory()  throws IOException
	{

		List<Question> q  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the category
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("category") + 8);

			final int categoryID = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the question
			q = new SearchQuestionByCategoryDatabase(con, categoryID).searchQuestionByCategory();

			if(q != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(q).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

}
