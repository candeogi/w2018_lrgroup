package project.rest;

import project.database.*;
import project.resource.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.io.*;


public class RestCertificate extends RestResource {

    public RestCertificate(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super(req, res, con);
    }


}
