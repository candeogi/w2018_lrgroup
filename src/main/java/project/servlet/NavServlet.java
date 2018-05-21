package project.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import project.database.SearchUserByUsernameDatabase;
import project.resource.*;

/**
 * Allows site navigation and login request on protected resources
 *
 * @author Alberto Pontini
 * @version 1.0.0
 *
 */

public class NavServlet extends AbstractDatabaseServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String get = req.getParameter("p");

		if(get==null) req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);


		switch(get)
		{
			case "create-question":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/jsp/create-question-form.jsp").forward(req, res);
				else
				{
					req.setAttribute("from", "create-question");
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				}

				break;

			case "create-answer":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/jsp/create-answer-form.jsp").forward(req, res);
				else
				{
					req.setAttribute("from", "create-answer");
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				}

				break;

			case "log-in":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/").forward(req, res);
				else
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				break;

			case "log-out":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/logout").forward(req, res);
				break;

			case "create-user":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/").forward(req, res);
				else
					req.getRequestDispatcher("/jsp/create-user-form.jsp").forward(req, res);
				break;

			case "user":
				if(req.getSession().getAttribute("loggedInUser") != null)
				{

					String user = req.getParameter("u");
					User u = null;
					if(user!=null && !user.equals(""))
					{
						try
						{
							List<User> ul = new SearchUserByUsernameDatabase(getDataSource().getConnection(),user).searchUserByUsername();
							if(ul!=null && ul.size() >= 1)
							{
								u = ul.get(0);
								req.setAttribute("user",u);
								req.getRequestDispatcher("/jsp/user-page.jsp").forward(req, res);
							}
						}
						catch(SQLException sqle)
						{
							req.setAttribute("message",new Message("Error while retrieving user from database", 
								"SomeCode", sqle.getMessage()));
							req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);
						}
					}
					if(u==null || user==null || user.equals(""))
					{
						req.setAttribute("message",new Message("Resource not found", "E404", "User " + user + " not found"));
						req.getRequestDispatcher("/jsp/404.jsp").forward(req, res);
					}
				}
				else
				{
					req.setAttribute("from", "user"); //bisogna passare anche u in qualche modo
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				}
				break;

			default:
				req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);
				break;
		}
	}
}