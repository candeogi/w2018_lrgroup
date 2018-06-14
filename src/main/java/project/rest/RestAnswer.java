package project.rest;

import project.database.*;
import project.resource.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.*;
import java.util.List;



/**
 * Manages the REST API for the {@link Answer} resource.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class RestAnswer extends RestResource
{

	/**
	 * Creates a new REST resource for managing {@code Answer} resources.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public RestAnswer(final HttpServletRequest req, final HttpServletResponse res, Connection con)
	{
		super(req, res, con);
	}

	/**
	 * Creates a new answer into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void createAnswer() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			final Answer answer = Answer.fromJSON(req.getInputStream());

			// creates a new object for accessing the database and stores the answer
			a = new CreateAnswerDatabase(con, answer).createAnswer();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_CREATED);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot create the answer: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505"))
			{
				m = new Message("Cannot create the answer: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			}
			else
			{
				m = new Message("Cannot create the answer: unexpected error.", "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}

	/**
	 * Updates an answer in the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void updateAnswer() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			final Answer answer = Answer.fromJSON(req.getInputStream());

			a = new UpdateAnswerDatabase(con, answer).updateAnswer();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot delete the answer: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot delete the answer: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Deletes an answer in the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void deleteAnswer() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			final Answer answer = Answer.fromJSON(req.getInputStream());

			a = new DeleteAnswerByIDDatabase(con, answer.getID()).deleteAnswerByID();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot delete the answer: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot delete the answer: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	public void deleteAnswerVote() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("novote") + 6);

			final int answerID = Integer.parseInt(path.substring(1));

			a = new DeleteAnswerVoteDatabase(con,req.getSession().getAttribute("loggedInUser").toString() , answerID).deleteAnswerVote();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot delete the answer: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot delete the answer: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	public void countAnswerVotes() throws IOException
	{
		int a;
		Message m = null;
		Votes v;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("votes") + 5);

			final int answerID = Integer.parseInt(path.substring(1));
			
			a = new CountAnswerVotesDatabase(con, answerID).countAnswerVotes();

			if(a!=-1)
			{
				v = new Votes("answer",a,answerID);
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
	 * Creates an upvote for an answer in the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void upvoteAnswer() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("upvote") + 6);

			final int answerID = Integer.parseInt(path.substring(1));

			a = new CreateAnswerUpvoteDatabase(con, req.getSession().getAttribute("loggedInUser").toString(),answerID).createAnswerUpvote();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot upvote the answer: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot upvote the answer: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Creates a downvote for an answer in the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */

	public void downvoteAnswer() throws IOException
	{
		boolean a;
		Message m = null;

		try{

			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("downvote") + 8);

			final int answerID = Integer.parseInt(path.substring(1));

			a = new CreateAnswerDownvoteDatabase(con, req.getSession().getAttribute("loggedInUser").toString(),answerID).createAnswerDownvote();

			if(a)
			{
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				// it should not happen
				m = new Message("Cannot downvote the answer: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t) 
		{
			m = new Message("Cannot downvote the answer: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches answers by a question ID.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchAnswerByQuestionID()  throws IOException
	{

		List<Answer> al  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the ID
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("question") +8);

			final int questionID = Integer.parseInt(path.substring(1));


			// creates a new object for accessing the database and search the answers
			al = new SearchAnswerByQuestionIDDatabase(con, questionID).searchAnswerByQuestionID();

			if(al != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(al).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search answers: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search answers: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches answers by an answer ID.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchAnswerByAnswerID()  throws IOException
	{

		List<Answer> al  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the ID
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("parentAns") +9);

			final int questionID = Integer.parseInt(path.substring(1));

			// creates a new object for accessing the database and search the answers
			al = new SearchAnswerByAnswerIDDatabase(con, questionID).searchAnswerByAnswerID();

			if(al != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(al).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search answers: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search answers: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	/**
	 * Searches answers by a user ID.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchAnswerByUserID()  throws IOException
	{

		List<Answer> al  = null;
		Message m = null;

		try
		{
			// parse the URI path to extract the ID
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("user") +4);

			final String userID = path.substring(1);


			// creates a new object for accessing the database and search the answers
			al = new SearchAnswerByUserIDDatabase(con,userID).searchAnswerByUserID();

			if(al != null)
			{
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(al).toJSON(res.getOutputStream());
			}
			else
			{
				// it should not happen
				m = new Message("Cannot search answers: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
		catch (Throwable t)
		{
			m = new Message("Cannot search answers: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}
}
