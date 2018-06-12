package project.rest;

import project.resource.Message;
import project.resource.WebSite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class RestWebsite extends  RestResource{
    public RestWebsite(final HttpServletRequest req, final HttpServletResponse res,Connection con) {
        super(req, res, con);

    }

    /*public searchWebsiteByID() throws IOException{
        List<WebSite> websites=null;
        Message message=null;
        try{
            String path=req.getRequestURI();
            path=path.substring(path.lastIndexOf("id")+2);
            fina
        }

    }*/
}
