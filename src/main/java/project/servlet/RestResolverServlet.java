package project.servlet;

import project.resource.*;
import project.rest.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

/**
 * Manages the REST API for the different REST resources.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 * @author Alberto Forti (alberto.forti@studenti.unipd.it)
 */
public class RestResolverServlet extends AbstractDatabaseServlet {

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

    /**
     * Overrides service method called by the servlet container.
     * It provides the resource requested if this is available, otherwise it throws an error message.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @throws ServletException if any error occurs while executing the servlet.
     * @throws IOException      if any error occurs in the client/server communication.
     */
    @Override
    protected final void service(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(JSON_UTF_8_MEDIA_TYPE);
        final OutputStream out = res.getOutputStream();

        try {
            if (!checkMethodMediaType(req, res)) return;

            /*if (req.getSession().getAttribute("loggedInUser") == null) {
                final Message m = new Message("User not logged in", "E4A6", "LogIn required");
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                m.toJSON(out);
                //AGGIUNGERE UN RETURN!
            }*/

            String[] split = req.getRequestURI().split("/");
            getServletContext().log("primo split " + split[0]);
            getServletContext().log("secondo split " + split[1]);
            getServletContext().log("terzo split " + split[2]);
            getServletContext().log("quarto split " + split[3]);
            switch (split[3]) // split[3] -> a 0 è vuoto, poi web-app-unipd, poi rest, poi question/answer
            {
                case "question":
                    if (processQuestion(req, res)) {
                        return;
                    }
                    break;
                case "answer":
                    getServletContext().log("sono entrato in processAnswer");
                    if (processAnswer(req, res)) {
                        getServletContext().log("entrato nel if di processAnswer");
                        return;
                    }
                    break;
                case "user":
                    if (processUser(req, res)) {
                        return;
                    }
                    break;
                case "website":
                    getServletContext().log("sono entrato in processWebSite");
                    if (processWebsite(req, res)) {
                        return;
                    }
                    break;
                case "category":
                    if (processCategory(req, res)) {
                        return;
                    }
                    break;
                case "certificate":
                    getServletContext().log("sono entrato in processCertificate");
                    if (processCertificate(req, res)) {
                        return;
                    }
                    break;
                default:
                    getServletContext().log("non ho trovato nulla da fare");
                    break;
            }
            // if none of the above process methods succeeds, it means an unknow resource has been requested
            final Message m = new Message("Unknown resource requested.", "E4A6",
                    String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            m.toJSON(out);
        } finally {
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
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res) throws IOException {

        final String method = req.getMethod();
        final String contentType = req.getHeader("Content-Type");
        final String accept = req.getHeader("Accept");
        final OutputStream out = res.getOutputStream();

        Message m;

        if (accept == null) {
            m = new Message("Output media type not specified.", "E4A1", "Accept request header missing.");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(out);
            return false;
        }

        if (!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            m = new Message("Unsupported output media type. Resources are represented only in application/json.",
                    "E4A2", String.format("Requested representation is %s.", accept));
            res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            m.toJSON(out);
            return false;
        }

        switch (method) {
            case "GET":
                return true;
            case "POST":
                return true;
            case "DELETE":
                return true;
            case "PUT":
                return true;
            default:
                m = new Message("Unsupported operation.",
                        "E4A5", String.format("Requested operation %s.", method));
                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                m.toJSON(out);
                return false;
        }

    }

    /**
     * Process the answer-related resources
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return TRUE if a resource is found, otherwise FALSE
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processAnswer(HttpServletRequest req, HttpServletResponse res) throws IOException {

        OutputStream out = res.getOutputStream();
        String path = req.getRequestURI();
        final String method = req.getMethod();
        getServletContext().log("method " + method);
        Message m = null;

        try {

            path = path.substring(path.lastIndexOf("answer") + 6);

            if (path.contains("question")) {
                // /answer/question/{questionID}
                path = path.substring(path.lastIndexOf("question") + 8);
                if (path.length() == 0 || path.equals("/")) {

                    m = new Message("Wrong format for URI /answer/question/{questionID}: no {questionID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());

                } else {
                    switch (method) {
                        case "GET":
                            try {
                                Integer.parseInt(path.substring(1));
                                new RestAnswer(req, res, getDataSource().getConnection()).searchAnswerByQuestionID();
                            } catch (NumberFormatException e) {
                                m = new Message(
                                        "Wrong format for URI /answer/question/{questionID}: {questionID} is not an integer.",
                                        "E4A7", e.getMessage());
                                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                m.toJSON(res.getOutputStream());
                            }
                            break;
                        default:
                            m = new Message("Unsupported operation for URI /answer/question/{questionID}.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }

            } else if (path.contains("parentAns")) {
                // /answer/parentAns/{answerID}
                path = path.substring(path.lastIndexOf("parentAns") + 9);
                if (path.length() == 0 || path.equals("/")) {
                    m = new Message("Wrong format for URI /answer/parentAns/{answerID}: no {answerID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());

                } else {
                    switch (method) {
                        case "GET":
                            new RestAnswer(req, res, getDataSource().getConnection()).searchAnswerByAnswerID();
                            break;

                        default:
                            m = new Message("Unsupported operation for URI /answer/parentAns/{answerID}.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }

            } else if (path.contains("user")) {
                // /answer/user/{userid}
                path = path.substring(path.lastIndexOf("user") + 4);
                if (path.length() == 0 || path.equals("/")) {
                    m = new Message("Wrong format for URI /answer/user/{userID}: no {userID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());

                } else {
                    switch (method) {
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

            } else if (path.contains("upvote")) {
                // /answer/upvote/{answerID}
                path = path.substring(path.lastIndexOf("upvote") + 6);
                if (path.length() == 0 || path.equals("/")) {
                    m = new Message("Wrong format for URI /answer/upvote/{answerID}: no {answerID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());
                } else {
                    switch (method) {
                        case "POST":
                            new RestAnswer(req, res, getDataSource().getConnection()).upvoteAnswer();
                            break;

                        default:
                            m = new Message("Unsupported operation for URI /answer/upvote/{answerID}.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }
            } else if (path.contains("downvote")) {
                // /answer/downvote/{answerID}
                path = path.substring(path.lastIndexOf("downvote") + 8);
                if (path.length() == 0 || path.equals("/")) {
                    m = new Message("Wrong format for URI /answer/downvote/{answerID}: no {answerID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());

                } else {
                    switch (method) {
                        case "POST":
                            new RestAnswer(req, res, getDataSource().getConnection()).downvoteAnswer();
                            break;

                        default:
                            m = new Message("Unsupported operation for URI /answer/downvote/{answerID}.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }

            } else if (path.contains("novote")) {
                // /answer/novote/{answerID}
                path = path.substring(path.lastIndexOf("novote") + 6);
                if (path.length() == 0 || path.equals("/")) {
                    m = new Message("Wrong format for URI /answer/novote/{answerID}: no {answerID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());
                } else {
                    switch (method) {
                        case "POST":
                            new RestAnswer(req, res, getDataSource().getConnection()).deleteAnswerVote();
                            break;

                        default:
                            m = new Message("Unsupported operation for URI /answer/upvote/{answerID}.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }

            } else if (path.contains("votes")) {
                // /answer/votes/{answerID}
                path = path.substring(path.lastIndexOf("votes") + 5);
                if (path.length() == 0 || path.equals("/")) {
                    m = new Message("Wrong format for URI /answer/votes/{answerID}: no {answerID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());

                } else {
                    switch (method) {
                        case "GET":
                            new RestAnswer(req, res, getDataSource().getConnection()).countAnswerVotes();
                            break;

                        default:
                            m = new Message("Unsupported operation for URI /answer/upvote/{answerID}.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }

            } else if (!(path.length() == 0) || path.equals("/")) {
                switch (method) {
                    case "POST":
                        new RestAnswer(req, res, getDataSource().getConnection()).createAnswer();
                        break;
                    case "PUT":
                        getServletContext().log("sono entrato in PUT");
                        new RestAnswer(req, res, getDataSource().getConnection()).updateAnswer();
                        break;
                    case "DELETE":
                        getServletContext().log("sono entrato in case DELETE");
                        new RestAnswer(req, res, getDataSource().getConnection()).deleteAnswer();
                        break;
                    default:
                        m = new Message("Unsupported operation for URI /answer.",
                                "E4A5", String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                        break;
                }

            } else {
                m = new Message("Unknown operations");
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }

        } catch (Throwable t) {
            m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
        return true;
    }

    /**
     * Process the question-related resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return TRUE if a resource is found, otherwise FALSE
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processQuestion(HttpServletRequest req, HttpServletResponse res) throws IOException {

        final String method = req.getMethod();
        String path = req.getRequestURI();
        Message m;

        try {
            // strip everything until after the /question
            path = path.substring(path.lastIndexOf("question") + 8);

            if (path.length() == 0 || path.equals("/")) {
                switch (method) {
                    case "GET":
                        RestQuestion rq = new RestQuestion(req, res, getDataSource().getConnection());
                        rq.listQuestions();
                        break;
                    default:
                        m = new Message("Unsupported operation for URI /question.",
                                "E4A5", String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                        break;
                }

            } else {

                if (path.contains("id")) {
                    //question/id/{ID}
                    path = path.substring(path.lastIndexOf("id") + 2);

                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/id/{ID}: no {ID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());

                    } else {
                        switch (method) {
                            case "GET":
                                try {
                                    Integer.parseInt(path.substring(1));
                                    new RestQuestion(req, res, getDataSource().getConnection()).searchQuestionByID();
                                } catch (NumberFormatException e) {
                                    m = new Message(
                                            "Wrong format for URI /question/id/{ID}: {ID} is not an integer.",
                                            "E4A7", e.getMessage());
                                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                    m.toJSON(res.getOutputStream());
                                }
                                break;
                            case "PUT":
                                new RestQuestion(req, res, getDataSource().getConnection()).updateQuestion();
                                break;
                            default:
                                m = new Message("Unsupported operation for URI /question/id/{ID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }

                } else if (path.contains("user")) {
                    //question/user/{userID}
                    path = path.substring(path.lastIndexOf("user") + 4);

                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/user/{userID}: no {userID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());
                    } else {
                        switch (method) {
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

                } else if (path.contains("upvote")) {
                    // /question/upvote/{questionID}
                    path = path.substring(path.lastIndexOf("upvote") + 6);

                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/upvote/{questionID}: no {questionID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());
                    } else {
                        switch (method) {
                            case "POST":
                                new RestQuestion(req, res, getDataSource().getConnection()).upvoteQuestion();
                                break;

                            default:
                                m = new Message("Unsupported operation for URI /question/upvote/{questionID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }

                } else if (path.contains("category")) {
                    // /question/category/{category}
                    path = path.substring(path.lastIndexOf("category") + 8);

                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/category/{categoryID}: no {categoryID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());
                    } else {
                        switch (method) {
                            case "GET":
                                new RestQuestion(req, res, getDataSource().getConnection()).searchQuestionByCategory();
                                break;
                            default:
                                m = new Message("Unsupported operation for URI /question/category/{categoryID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }

                } else if (path.contains("downvote")) {
                    // /question/downvote/{questionID}
                    path = path.substring(path.lastIndexOf("downvote") + 8);

                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/downvote/{questionID}: no {questionID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());

                    } else {
                        switch (method) {
                            case "POST":
                                new RestQuestion(req, res, getDataSource().getConnection()).downvoteQuestion();
                                break;

                            default:
                                m = new Message("Unsupported operation for URI /question/downvote/{questionID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }
                } else if (path.contains("novote")) {
                    // /question/novote/{questionID}

                    path = path.substring(path.lastIndexOf("novote") + 6);
                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/novote/{questionID}: no {questionID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());

                    } else {
                        switch (method) {
                            case "POST":
                                new RestQuestion(req, res, getDataSource().getConnection()).deleteQuestionVote();
                                break;

                            default:
                                m = new Message("Unsupported operation for URI /question/upvote/{questionID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }
                } else if (path.contains("votes")) {
                    // /question/novote/{questionID}
                    path = path.substring(path.lastIndexOf("votes") + 5);

                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /question/votes/{questionID}: no {questionID} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());

                    } else {
                        switch (method) {
                            case "GET":
                                new RestQuestion(req, res, getDataSource().getConnection()).countQuestionVotes();
                                break;

                            default:
                                m = new Message("Unsupported operation for URI /question/votes/{questionID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }

                } else if (path.contains("latestQuestion")) {
                    path = path.substring(path.lastIndexOf("latestQuestion") + 14);

                    if (path.length() == 0 || path.equals("/")) {
                        switch (method) {
                            case "GET":
                                new RestQuestion(req, res, getDataSource().getConnection()).searchQuestionByTimestamp();
                                break;
                            default:
                                m = new Message("Unsupported operation for URI /question/category/{categoryID}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }

                    } else {
                        m = new Message("Unsupported operation for URI /question/latestQuestion.",
                                "E4A5", String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                    }
                }
            }
        } catch (Throwable t) {
            m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
        return true;
    }

    /**
     * Process the category-related resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return TRUE if a resource is found, otherwise FALSE
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processCategory(HttpServletRequest req, HttpServletResponse res) throws IOException {
        OutputStream out = res.getOutputStream();
        final String method = req.getMethod();
        String path = req.getRequestURI();
        Message m = null;
        try {
            // strip everything until after the /category
            path = path.substring(path.lastIndexOf("category") + 8);

            if (path.length() == 0 || path.equals("/")) {
                switch (method) {
                    case "GET":
                        new RestCategory(req, res, getDataSource().getConnection()).searchCategory();
                        break;
                    case "POST":
                        new RestCategory(req, res, getDataSource().getConnection()).addCategory();
                        break;
                    default:
                        m = new Message("Unsupported operation for URI /category.",
                                "E4A5", String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                        break;
                }

            }
        } catch (Throwable t) {
            m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
        return true;
    }


    private boolean processWebsite(HttpServletRequest req, HttpServletResponse res) throws IOException {

        OutputStream out = res.getOutputStream();
        final String method = req.getMethod();
        String path = req.getRequestURI();
        Message message = null;

        try {

            path = path.substring(path.indexOf("website") + 7);

            if (path.contains("user")) {

                path = path.substring(path.lastIndexOf("user") + 5);

                if (path.length() == 0 || path.equals("/")) {
                    message = new Message("Wrong format for URI /website/user/{ID}: no {ID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    message.toJSON(res.getOutputStream());
                } else {
                    switch (method) {
                        case "GET":
                            RestWebsite restWebsite = new RestWebsite(req, res, getDataSource().getConnection());
                            restWebsite.searchWebsiteByUser();
                            break;
                        case "DELETE":
                            RestWebsite restWebsiteDelete = new RestWebsite(req, res, getDataSource().getConnection());
                            restWebsiteDelete.removeWebsite();
                            break;
                        default:
                            message = new Message("Unsupported operation for URI /question.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            message.toJSON(res.getOutputStream());
                            break;

                    }
                }
            }

        } catch (SQLException e) {
            message = new Message("Unexpected error.", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(res.getOutputStream());
        }
        return true;
    }

    private boolean processCertificate(HttpServletRequest req, HttpServletResponse res) throws IOException {

        OutputStream out = res.getOutputStream();
        final String method = req.getMethod();
        String path = req.getRequestURI();
        Message message = null;

        try {

            path = path.substring(path.lastIndexOf("certificate") + 11);

            if (path.contains("user")) {
                path = path.substring(path.lastIndexOf("user") + 4);

                if (path.length() == 0 || path.equals("/")) {

                    message = new Message("Wrong format for URI /certificate/user/{ID}: no {ID} specified.",
                            "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    message.toJSON(res.getOutputStream());

                } else {
                    switch (method) {
                        case "GET":
                            RestCertificate restCertificate = new RestCertificate(req, res, getDataSource().getConnection());
                            restCertificate.searchCertificateByUserName();
                            break;
                        case "DELETE":
                            RestCertificate restCertificateDelete = new RestCertificate(req, res, getDataSource().getConnection());
                            restCertificateDelete.deleteHaveCertificateByUserName();
                            break;
                        default:
                            message = new Message("Unsupported operation for URI /certificate.",
                                    "E4A5", String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            message.toJSON(res.getOutputStream());
                            break;
                    }
                }
            }

        } catch (SQLException e) {
            message = new Message("Unexpected error.", "E5A1", e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message.toJSON(res.getOutputStream());
        }
        return true;
    }

    /**
     * Process the user-related resources
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return TRUE if a resource is found, otherwise FALSE
     * @throws IOException if any error occurs in the client/server communication.
     */
    private boolean processUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
        OutputStream out = res.getOutputStream();
        String path = req.getRequestURI();
        final String method = req.getMethod();
        Message m = null;
        try {
            // strip everything until after the /user
            path = path.substring(path.lastIndexOf("user") + 4);

            if (path.length() == 0 || path.equals("/")) {
                switch (method) {
                    case "GET":
                        new RestUser(req, res, getDataSource().getConnection()).searchUser();
                        break;
                    default:
                        m = new Message("Unsupported operation for URI /user.",
                                "E4A5", String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                        break;
                }
            } else {
                if (path.contains("id")) {
                    // /user/id/{username}
                    path = path.substring(path.lastIndexOf("id") + 2);
                    if (path.length() == 0 || path.equals("/")) {
                        m = new Message("Wrong format for URI /user/id/{username}: no {username} specified.",
                                "E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        m.toJSON(res.getOutputStream());
                    } else {
                        switch (method) {
                            case "GET":
                                new RestUser(req, res, getDataSource().getConnection()).searchUserByUsername();
                                break;
                            case "POST":
                                new RestUser(req, res, getDataSource().getConnection()).createUser();
                                break;
                            case "PUT":
                                new RestUser(req, res, getDataSource().getConnection()).updateUser();
                                break;
                            case "DELETE":
                                new RestUser(req, res, getDataSource().getConnection()).deleteUser();
                                break;
                            default:
                                m = new Message("Unsupported operation for URI /user/id/{username}.",
                                        "E4A5", String.format("Requested operation %s.", method));
                                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                                m.toJSON(res.getOutputStream());
                                break;
                        }
                    }
                }

            }
        } catch (Throwable t) {
            m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
        return true;
    }


}