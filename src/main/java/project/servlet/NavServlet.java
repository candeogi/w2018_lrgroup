package project.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Allows site navigation and login request on protected resources
 *
 * @author Alberto Pontini
 * @version 1.0.0
 *
 */

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

			case "create-user":
				if(req.getSession().getAttribute("loggedInUser") != null)
					req.getRequestDispatcher("/").forward(req, res);
				else
					req.getRequestDispatcher("/jsp/create-user-form.jsp").forward(req, res);
				break;

			case "user":
				if(req.getSession().getAttribute("loggedInUser") != null)
				{
					/*Per ora è banale, bisogna aggiungere funzione che cerca l'utente nel database e che visualizza
					la pagina, anche se non si è loggati, in base all'URL, esempio:
					.../?p=user;u=pincopallino controlla i dati di pincopallino nel DB e li visualizza nella pagina*/
					req.getRequestDispatcher("/jsp/user-page.jsp").forward(req, res); //restituisce errore per ora
				}
				else
				{
					req.setAttribute("from", "create-answer");
					req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
				}
				break;

			default:
				req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);
				break;
		}
	}
}