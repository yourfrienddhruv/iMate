package com.dstar.imate.web.ws.groupon;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/Test")
@RequestScoped
public class WSRSTest{

	@GET  //Cross Domain Supported
	@Path("/{operation}")
	@Produces("application/json") 
	public String GETDispatcher_(@PathParam("operation") String type,String messageData) {
		return type;
	}
}
