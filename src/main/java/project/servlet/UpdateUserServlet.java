package project.servlet;


import project.resource.User;
import project.resource.Message;
import project.database.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


/**
 * Updates a user's informations (except password and user's photoProfile)
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public final class UpdateUserServlet extends SessionManagerServlet {

    /**
     * Updates a user's informations (except password)
     *
     * @param req the HTTP request from the client.
     * @param res the HTTP response from the server.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String name = null;
        String surname = null;
        String username = null;
        String email = null;
        String bdate = null;
        String description = null;

        // model
        User u = null;
        Message m = null;

        try {
            // retrieves the request parameters
            if (req.getParameter("username") != null)
                username = req.getParameter("username");
            else
                username = (String) req.getSession().getAttribute("loggedInUser");

            getServletContext().log("loggedUser: " + username);

            name = req.getParameter("name");
            getServletContext().log("name : " + name);
            if (name.equals(""))
                name = req.getParameter("currentName");


            surname = req.getParameter("surname");
            getServletContext().log("surname : " + surname);
            if (surname.equals(""))
                surname = req.getParameter("currentSurname");

            email = req.getParameter("email");
            getServletContext().log("email : " + email);
            if (email.equals(""))
                email = req.getParameter("currentEmail");

            bdate = req.getParameter("bdate");
            getServletContext().log("bdate : " + bdate);
            if (bdate.equals("") || bdate.equals("mm/dd/yyy")) {
                getServletContext().log("tengo quella di prima");
                bdate = req.getParameter("currentBdate");
            }


            description = req.getParameter("description");
            getServletContext().log("description : " + description);
            if (description.equals(""))
                description = req.getParameter("currentDescription");

            bdate = bdate.replace("/", "-");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = new Date(dateFormat.parse(bdate).getTime());

            if (req.getParameter("admin-modifying") != null && req.getParameter("isAdmin") != null)
                u = new User(email, name, surname, username, null, "", true, null, birthday, description);
            else
                u = new User(email, name, surname, username, null, "", false, null, birthday, description);

            // creates a new object for accessing the database and updates the user
            new UpdateUserDatabase(getDataSource().getConnection(), u).updateUser();
        } catch (NumberFormatException e) {
            m = new Message("Birthday not correct", "E300", e.getMessage());
        } catch (ParseException pe) {
            //Should not happen
            m = new Message("Birthday not correct", "E300", pe.getMessage());
        } catch (SQLException ex) {
            m = new Message("Cannot update the user: unexpected error while accessing the database.",
                    "E200", ex.getMessage());
        }


        if (m != null && m.isError()) {
            req.setAttribute("message", m);
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, res);
        } else {

            res.setStatus(HttpServletResponse.SC_OK);
           /* String url = req.getParameter("view");
            getServletContext().log("view : " + url);
            if (url != null)

                res.sendRedirect(req.getContextPath() + "/?p=" + url);
            else
                res.sendRedirect(req.getContextPath() + "/?p=user&u=" + username);*/
        }
    }


}
