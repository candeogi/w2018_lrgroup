package project.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import project.database.*;
import project.resource.*;

/**
 * Show questions ordered by timestamp
 *
 * @author Alberto Forti
 * @version 1.0.0
 *
 */

public class ShowQuestionsServlet extends AbstractDatabaseServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<Question> q = null;
        Message m = null;
        try {

            // creates a new object for accessing the database and searching the questions
            q = new SearchQuestionByTimestampDatabase(getDataSource().getConnection())
                    .SearchQuestionByTimestamp();
            m = new Message("Questions successfully searched.");

        } catch (SQLException ex) {
            m = new Message("Cannot search for questions: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }

        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();
        out.printf("<!DOCTYPE html>%n");

        out.printf("<html lang=\"en\">%n");
        out.printf("<head>%n");
        out.printf("<meta charset=\"utf-8\">%n");
        out.printf("<title>Show questions</title>%n");
        out.printf("</head>%n");
        out.printf("<body>%n");
        out.printf("<h1>Show questions</h1>%n");
        out.printf("<hr/>%n");
        if(m.isError()) {
            out.printf("<ul>%n");
            out.printf("<li>error code: %s</li>%n", m.getErrorCode());
            out.printf("<li>message: %s</li>%n", m.getMessage());
            out.printf("<li>details: %s</li>%n", m.getErrorDetails());
            out.printf("</ul>%n");
        } else {
            out.printf("<p>%s</p>%n", m.getMessage());

            out.printf("<table>%n");
            out.printf("<tr>%n");
            out.printf("<td>User</td><td>Title</td><td>Body</td><td>Published on</td><td>Last modified</td>%n");
            out.printf("</tr>%n");

            for(Question e: q) {
                out.printf("<tr>%n");
                out.printf("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>%n",
                        e.getIDUser(), e.getTitle(), e.getBody(), e.getTimestamp(),e.getLastModified());
                out.printf("</tr>%n");
            }
            out.printf("</table>%n");
        }
        out.printf("</body>%n");
        out.printf("</html>%n");
        out.flush();
        out.close();
    }
}