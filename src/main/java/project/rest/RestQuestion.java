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
	 * Creates an upvote for a question in the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void upvoteQuestion() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.indexOf("upvote") + 6);

			final int questionID = Integer.parseInt(path.substring(1));

			a = new CreateQuestionUpvoteDatabase(con, req.getSession().getAttribute("loggedInUser").toString(),questionID).createQuestionUpvote();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot upvote the question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot upvote the question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Creates a downvote for a question in the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void downvoteQuestion() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.indexOf("downvote") + 8);

			final int questionID = Integer.parseInt(path.substring(1));

			a = new CreateQuestionDownvoteDatabase(con, req.getSession().getAttribute("loggedInUser").toString(),questionID).createQuestionDownvote();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot downvote the question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot downvote the question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Counts votes of a question into the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void countQuestionVotes() throws IOException
	{
		int a;
		Message m = null;
		Votes v;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.indexOf("votes") + 5);

			final int questionID = Integer.parseInt(path.substring(1));
			
			a = new CountAnswerVotesDatabase(con, questionID).countAnswerVotes();

			if(a!=-1)
			{
				v = new Votes("question",a,questionID);
				res.setStatus(HttpServletResponse.SC_OK);
				v.toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot count votes: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot count votes: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Deletes votes of a question into the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void deleteQuestionVote() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.indexOf("novote") + 6);

			final int questionID = Integer.parseInt(path.substring(1));
			
			a = new DeleteQuestionVoteDatabase(con,req.getSession().getAttribute("loggedInUser").toString() ,questionID).deleteQuestionVote();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot delete the question vote: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot delete the question vote: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
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
			path = path.substring(path.indexOf("id") + 2);

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
			path = path.substring(path.indexOf("user") + 4);

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
			path = path.substring(path.indexOf("timestamp") + 9);

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
	 * Searches question ordered by votes.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchQuestionOrderedByVotes()  throws IOException
	{

		List<Question> ql  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the timestamp
			String path = req.getRequestURI();
			path = path.substring(path.indexOf("byvote") + 6);

			//final long timestamp = Long.parseLong(path.substring(1));


			// creates a new object for accessing the database and search the question
			ql = new SearchQuestionOrderByVotesDatabase(con).SearchQuestionOrderedByVotes();

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

		List<Question> q;
		Message m;

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

		List<Question> q;
		Message m;

		try
		{
			// parse the URI path to extract the category
			String path = req.getRequestURI();
			path = path.substring(path.indexOf("category") + 8);

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

	/**
	 * Updates a question in the database.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void updateQuestion() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			final Question question = Question.fromJSON(req.getInputStream());

			new UpdateQuestionDatabase(con, question).updateQuestion();

			/*if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot update the question: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}*/
		}
		catch (Throwable t)
		{
			m = new Message("Cannot update the question: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches question by keyword.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchByKeyword()  throws IOException
	{

		List<Question> q;
		Message m;

		try
		{
			// parse the URI path to extract the category
			String path = req.getRequestURI();
			path = path.substring(path.indexOf("searchby") + 8);

			// creates a new object for accessing the database and search the question
			q = new SearchQuestionByKeywordDatabase(con, "%"+path.substring(1)+"%").searchQuestionByKeyword();

			if(q != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(q).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search question by keyword: unexpected error.", "E5A1", null);
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
