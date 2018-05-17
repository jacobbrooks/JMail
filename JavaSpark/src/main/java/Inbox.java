import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import spark.Request;
import spark.Response;

public class Inbox {
	private Request req;
	private Response res;
	
	public Inbox(Request req, Response res) {
		this.req = req;
		this.res = res;
		processRequest();
	}
	
	public Response response() {
		return res;
	}
	
	private void processRequest() {
		String token = req.headers("token");
        String from = req.queryParams("from");

        Database d = new Database();
        String[][] inbox = d.getInbox(token, from);
        InboxMessage[] messages = new InboxMessage[inbox.length];
        for(int i = 0; i < inbox.length; i++){
           messages[i] = new InboxMessage();
           messages[i].from = inbox[i][0];
           messages[i].subject = inbox[i][1];
           messages[i].message = inbox[i][2];
           messages[i].messageID = inbox[i][3];
        }

        try{
           ObjectMapper mapper = new ObjectMapper();
           String inboxJSON = mapper.writeValueAsString(messages);
           res.type("application/json");
           res.body(inboxJSON);
        } catch(JsonParseException e){
           e.printStackTrace();
        } catch(JsonMappingException e){
           e.printStackTrace();
        } catch(IOException e){
           e.printStackTrace();
        }
	}
}
