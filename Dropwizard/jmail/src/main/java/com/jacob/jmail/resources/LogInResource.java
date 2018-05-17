package com.jacob.jmail.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.jacob.jmail.core.LogIn;
import com.jacob.jmail.db.UserDAO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogInResource {

	private UserDAO userTable;

	public LogInResource(UserDAO userTable) {
		this.userTable = userTable;
	}

	@POST
	public Response login(LogIn l) {
		String token = userTable.getToken(l.username, l.password);
		String res =  "{\"token\":\"" + token + "\"}";
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(res).build();
	}

}
