package project.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import project.database.SearchQuestionByUserDatabase;
import project.database.SearchUserByUsernameDatabase;
import project.resource.*;

/**
 * Allows site navigation and login request on protected resources
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 */

public class NavServlet extends AbstractDatabaseServlet {
    /**
     * Allows site navigation and login request on protected resources
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String get = req.getParameter("p");

        if (get == null) req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);

        else {
            switch (get) {
                case "create-question":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/jsp/create-question-form.jsp").forward(req, res);
                    else {
                        req.setAttribute("from", "create-question");
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    }

                    break;

                case "show-user-questions":
                    if (req.getSession().getAttribute("loggedInUser") != null) {
                        List<Question> q = null;
                        Message m = null;
                        try {
                            q = new SearchQuestionByUserDatabase(getDataSource().getConnection(),
                                    (String) req.getSession().getAttribute("loggedInUser")).SearchQuestionByUser();
                            m = new Message("Question searched");
                        } catch (SQLException ex) {
                            m = new Message("Cannot search for questions: unexpected error while accessing the database.",
                                    "E200", ex.getMessage());
                        }

                        req.setAttribute("m", m);
                        req.setAttribute("questions", q);
                        req.getRequestDispatcher("/jsp/show-questions-result.jsp").forward(req, res);
                    } else {
                        req.setAttribute("from", "show-user-questions");
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    }

                    break;

                case "create-answer":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/jsp/create-answer-form.jsp").forward(req, res);
                    else {
                        req.setAttribute("from", "create-answer");
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    }

                    break;

                case "update-question":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/jsp/update-question-form.jsp").forward(req, res);
                    else
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    break;

                case "log-in":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/").forward(req, res);
                    else
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    break;

                case "log-out":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/logout").forward(req, res);
                    break;

                case "answer":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/jsp/create-answer-form.jsp").forward(req, res);
                    break;

                case "create-user":
                    if (req.getSession().getAttribute("loggedInUser") != null)
                        req.getRequestDispatcher("/").forward(req, res);
                    else
                        req.getRequestDispatcher("/jsp/create-user-form.jsp").forward(req, res);
                    break;

                case "admin-panel":
                    if (req.getSession().getAttribute("loggedInUser") != null) {
                        req.setAttribute("from", "admin-panel");
                        req.getRequestDispatcher("/admin-panel").forward(req, res);
                        //TODO Check if user is an admin
                    } else {
                        req.setAttribute("from", "admin-panel");
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    }

                    break;

                case "user":
                    if (req.getSession().getAttribute("loggedInUser") != null) {

                        String user = req.getParameter("u");
                        User u = null;
                        if (user != null && !user.equals("")) {
                            try {
                                List<User> ul = new SearchUserByUsernameDatabase(getDataSource().getConnection(), user).searchUserByUsername();
                                if (ul != null && ul.size() >= 1) {
                                    u = ul.get(0);
                                    req.setAttribute("user", u);
                                    req.getRequestDispatcher("/jsp/user-information.jsp").forward(req, res);
                                }
                            } catch (SQLException sqle) {
                                req.setAttribute("message", new Message("Error while retrieving user from database",
                                        "SomeCode", sqle.getMessage()));
                                req.getRequestDispatcher("/jsp/index.jsp").forward(req, res);
                            }
                        }
                        if (u == null || user == null || user.equals("")) {
                            req.setAttribute("message", new Message("Resource not found", "E404", "User " + user + " not found"));
                            req.getRequestDispatcher("/jsp/404.jsp").forward(req, res);
                        }
                    } else {
                        req.setAttribute("from", "user"); //bisogna passare anche u in qualche modo
                        req.getRequestDispatcher("/jsp/login-form.jsp").forward(req, res);
                    }
                    break;

                default:
                    req.setAttribute("message", new Message("Resource not Found", "E404", "Page '" + get + "' not found"));
                    req.getRequestDispatcher("/jsp/404.jsp").forward(req, res);
                    break;
            }
        }
    }
    }