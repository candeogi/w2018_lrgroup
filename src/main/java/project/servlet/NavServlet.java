package project.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NavServlet extends HttpServlet
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
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);

				break;

			case "create-answer":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/jsp/create-question-form.jsp").forward(req, res);
				else
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);

				break;

			case "log-in":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/").forward(req, res);
				else
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				break;

			case "create-user":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/").forward(req, res);
				else
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				break;

			default:
				req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);
				break;
		}
	}
}