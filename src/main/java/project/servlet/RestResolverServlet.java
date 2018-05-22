package project.servlet;

import project.resource.*;
import project.rest.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import java.io.*;

public class RestResolverServlet extends AbstractDatabaseServlet
{

	/**
	 * The JSON MIME media type
	 */
	private static final String JSON_MEDIA_TYPE = "application/json";

	/**
	 * The JSON UTF-8 MIME media type
	 */
	private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

	/**
	 * The any MIME media type
	 */
	private static final String ALL_MEDIA_TYPE = "*/*";

	@Override
	protected final void service(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException
	{
		res.setContentType(JSON_UTF_8_MEDIA_TYPE);
		final OutputStream out = res.getOutputStream();

		try
		{
			if(!checkMethodMediaType(req,res)) return;

			//Controllare l'autenticazione
			
			String[] split = req.getRequestURI().split("/");
			switch(split[3]) // split[3] -> a 0 è vuoto, poi web-app-unipd, poi rest, poi question/answer
			{
				case "question":
					if (processQuestion(req, res))
					{
						return;
					}
					break;
				case "answer":
					if(processAnswer(req,res))
					{
						return;
					}
					break;
				//Aggiungere le risorse possibili
			}
			// if none of the above process methods succeeds, it means an unknow resource has been requested
			final Message m = new Message("Unknown resource requested.", "E4A6",
										  String.format("Requested resource is %s.", req.getRequestURI()));
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			m.toJSON(out);
		}
		finally
		{
			out.flush();
			out.close();
		}
	}

	/**
	 * Checks that the request method and MIME media type are allowed.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request method and the MIME type are allowed; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res) throws IOException
	{

		final String method = req.getMethod();
		final String contentType = req.getHeader("Content-Type");
		final String accept = req.getHeader("Accept");
		final OutputStream out = res.getOutputStream();

		Message m = null;

		if(accept == null) {
			m = new Message("Output media type not specified.", "E4A1", "Accept request header missing.");
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			m.toJSON(out);
			return false;
		}

		if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
			m = new Message("Unsupported output media type. Resources are represented only in application/json.",
							"E4A2", String.format("Requested representation is %s.", accept));
			res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			m.toJSON(out);
			return false;
		}

		//i'm allowing only GET requests
		switch(method)
		{
			case "GET":
				// nothing to do
				return true;
			default:
				m = new Message("Unsupported operation.",
								"E4A5", String.format("Requested operation %s.", method));
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				m.toJSON(out);
				return false;
		}

	}

	private boolean processAnswer(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		OutputStream out = res.getOutputStream();
		String path = req.getRequestURI();
		Message m = null;
		try {
			// strip everything until after the /answer
			path = path.substring(path.lastIndexOf("answer") + 6);

			if (path.length() == 0 || path.equals("/"))
			{
				m = new Message("Wrong format for URI /answer/id/{questionID} or /answer/user/{userid}",
								"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				m.toJSON(res.getOutputStream());			}
			else
			{
				if(path.contains("id"))
				{
					// /answer/id/{questionID}
					path = path.substring(path.lastIndexOf("id") + 2);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /answer/id/{questionID}: no {questionID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						try
						{
							Integer.parseInt(path.substring(1));
							new RestAnswer(req, res, getDataSource().getConnection()).searchAnswerByQuestionID();
						}
						catch (NumberFormatException e)
						{
							m = new Message(
									"Wrong format for URI /answer/id/{questionID}: {questionID} is not an integer.",
									"E4A7", e.getMessage());
							res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							m.toJSON(res.getOutputStream());
						}
					}
				}
				else if(path.contains("user"))
				{
					// /answer/user/{userid}
					path = path.substring(path.lastIndexOf("user") + 2);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /answer/user/{userid}: no {userid} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						new RestAnswer(req, res, getDataSource().getConnection()).searchAnswerByUserID();
					}
				}
			}
		}
		catch(Throwable t)
		{
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}

	private boolean processQuestion(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		OutputStream out = res.getOutputStream();
		final String method = req.getMethod();

		String path = req.getRequestURI();
		Message m = null;
		try {
			// strip everything until after the /question
			path = path.substring(path.lastIndexOf("question") + 8);

			if (path.length() == 0 || path.equals("/"))
			{
				switch (method) {
					case "GET":
						new RestQuestion(req, res, getDataSource().getConnection()).listQuestions();
						/*List<Question> list
						req.getRequestDispatcher("/jsp/show-questions-result.jsp").forward(req,res);*/
						//TODO json parser
						break;
					default:
						m = new Message("Unsupported operation for URI /question.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}

			}
			else
			{
				if(path.contains("id"))
				{
					//question/id/{ID}
					path = path.substring(path.lastIndexOf("id")+2);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /question/id/{ID}: no {ID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						try
						{
							Integer.parseInt(path.substring(1));
							new RestQuestion(req, res, getDataSource().getConnection()).searchQuestionByID();
						}
						catch (NumberFormatException e)
						{
							m = new Message(
									"Wrong format for URI /question/id/{ID}: {ID} is not an integer.",
									"E4A7", e.getMessage());
							res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							m.toJSON(res.getOutputStream());
						}
					}
				}
				else if(path.contains("user"))
				{
					//question/user/{userID}
					path = path.substring(path.lastIndexOf("user")+4);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /question/user/{userID}: no {userID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						String user = path.substring(1);
						new RestQuestion(req, res, getDataSource().getConnection()).searchQuestionByUser();
					}
				}
			}
		}
		catch(Throwable t)
		{
			m = new Message("Unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}
}