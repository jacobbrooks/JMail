package com.jacob.jmail.resources;

import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.jacob.jmail.core.SignUp;
import com.jacob.jmail.db.UserDAO;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserDAO userTable;

	public UserResource(UserDAO userTable) {
		this.userTable = userTable;
	}

	@POST
	public Response signUp(SignUp s) {
		String token = generateToken(s.username);
		userTable.insert(s.firstname, s.lastname, s.username, s.password, token);
		return Response.status(201).entity(s).type(MediaType.APPLICATION_JSON).build();
	}
	
	private String generateToken(String username) {
		String token = "";
		for (int i = 0; i < username.length(); i++) {
			token += username.charAt(i);
			token += username.charAt(Math.abs(new Random().nextInt() % username.length()));
			token += Math.abs(new Random().nextInt() % username.length());
		}
		return token;
	}

}
