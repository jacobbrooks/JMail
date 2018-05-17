package com.jacob.jmail.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.jacob.jmail.core.InboxMessage;
import com.jacob.jmail.core.Message;
import com.jacob.jmail.db.MessageDAO;
import com.jacob.jmail.db.UserDAO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageDAO messageTable;
	private UserDAO userTable;

	public MessageResource(MessageDAO messageTable, UserDAO userTable) {
		this.messageTable = messageTable;
		this.userTable = userTable;
	}

	@POST
	public Response sendMessage(Message m) {
		messageTable.insert(m.token, m.recipient, m.subject, m.message);
		return Response.status(201).type(MediaType.APPLICATION_JSON).entity(m).build();
	}
	
	@GET
	public Response inbox(@HeaderParam("token") String token, @QueryParam("from") String from) {
		String username = userTable.getUsername(token);
		
		List<Message> messages = messageTable.inbox(username);
		InboxMessage[] inbox = new InboxMessage[messages.size()];
		for(int i = 0; i < messages.size(); i++) {
			inbox[i] = new InboxMessage();
			inbox[i].id = messages.get(i).id;
			String sender = userTable.getUsername(messages.get(i).token);
			inbox[i].from = sender;
			inbox[i].subject = messages.get(i).subject;
			inbox[i].message = messages.get(i).message;
		}
		
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(inbox).build();
	}

}