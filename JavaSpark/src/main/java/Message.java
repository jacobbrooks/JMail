import java.io.IOException;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Message {
	
	private Request req;
	private Response res;
	
	public Message(Request req, Response res) {
		this.req = req;
		this.res = res;
		processRequest();
	}
	
	public Response response() {
		return res;
	}
	
	private void processRequest() {
		String body = req.body();
		try{
            ObjectMapper mapper = new ObjectMapper();
            Database d = new Database();
            MessageObject m = mapper.readValue(body, MessageObject.class);
            d.createMessage(m.token, m.receiver, m.subject, m.message);
            String jsonRes = "{\"response\":\"message sent\"}";
            res.type("application/json");
            res.body(jsonRes);
         } catch(JsonParseException e){
            e.printStackTrace();
         } catch(JsonMappingException e){
            e.printStackTrace();
         } catch(IOException e){
            e.printStackTrace();
         }
	}

}
