package project.servlet;

import project.resource.*;
import project.rest.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import java.io.*;
import java.util.List;

/**
 * Manages the REST API for the different REST resources.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
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

			if(req.getSession().getAttribute("loggedInUser")==null)
			{
				final Message m = new Message("User not logged in", "E4A6","LogIn required");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				m.toJSON(out);
			}
			
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

		switch(method)
		{
			case "GET":
				return true;
			case "POST":
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
		final String method = req.getMethod();
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
						switch(method)
						{
							case "GET":
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
								break;
							default:
								m = new Message("Unsupported operation for URI /answer/id/{questionID}.",
										"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("user"))
				{
					// /answer/user/{userid}
					path = path.substring(path.lastIndexOf("user") + 2);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /answer/user/{userID}: no {userID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "GET":
								new RestAnswer(req, res, getDataSource().getConnection()).searchAnswerByUserID();
								break;

							default:
								m = new Message("Unsupported operation for URI /answer/user/{userID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("upvote"))
				{
					// /answer/upvote/{answerID}
					path = path.substring(path.lastIndexOf("upvote") + 6);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /answer/upvote/{answerID}: no {answerID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "POST":
								//TODO funzione che aggiunge il voto
								break;

							default:
								m = new Message("Unsupported operation for URI /answer/upvote/{answerID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("downvote"))
				{
					// /answer/downvote/{answerID}
					path = path.substring(path.lastIndexOf("downvote") + 8);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /answer/downvote/{answerID}: no {answerID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "POST":
								//TODO funzione che aggiunge il voto negativo
								break;

							default:
								m = new Message("Unsupported operation for URI /answer/downvote/{answerID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("novote"))
				{
					// /answer/novote/{answerID}
					path = path.substring(path.lastIndexOf("novote") + 6);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /answer/novote/{answerID}: no {answerID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "POST":
								//TODO funzione che toglie il voto
								break;

							default:
								m = new Message("Unsupported operation for URI /answer/upvote/{answerID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
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
						RestQuestion rq = new RestQuestion(req, res, getDataSource().getConnection());
						rq.listQuestions();
						//TODO json parser in AJAX
						//List<Question> ql = rq.fromJSON(req.getInputStream());
						//req.setAttribute("questions",ql);
						//req.getRequestDispatcher("/jsp/show-questions-result.jsp").forward(req,res);
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
						switch(method)
						{
							case "GET":
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
								break;
							default:
								m = new Message("Unsupported operation for URI /question/id/{ID}.",
										"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
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
						switch(method)
						{
							case "GET":
								String user = path.substring(1);
								new RestQuestion(req, res, getDataSource().getConnection()).searchQuestionByUser();
								break;
							default:
								m = new Message("Unsupported operation for URI /question/user/{userID}.",
										"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("upvote"))
				{
					// /question/upvote/{questionID}
					path = path.substring(path.lastIndexOf("upvote") + 6);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /question/upvote/{questionID}: no {questionID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "POST":
								//TODO funzione che aggiunge il voto
								break;

							default:
								m = new Message("Unsupported operation for URI /question/upvote/{questionID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("downvote"))
				{
					// /question/downvote/{questionID}
					path = path.substring(path.lastIndexOf("downvote") + 8);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /question/downvote/{questionID}: no {questionID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "POST":
								//TODO funzione che aggiunge il voto negativo
								break;

							default:
								m = new Message("Unsupported operation for URI /question/downvote/{questionID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if(path.contains("novote"))
				{
					// /question/novote/{questionID}
					path = path.substring(path.lastIndexOf("novote") + 6);
					if (path.length() == 0 || path.equals("/"))
					{
						m = new Message("Wrong format for URI /question/novote/{questionID}: no {questionID} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
					else
					{
						switch(method)
						{
							case "POST":
								//TODO funzione che toglie il voto
								break;

							default:
								m = new Message("Unsupported operation for URI /question/upvote/{questionID}.",
								"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
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