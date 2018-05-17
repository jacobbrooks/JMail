import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import spark.Request;
import spark.Response;
import java.io.IOException;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class LogIn {
	
	private Request req;
	private Response res;
	
	public LogIn(Request req, Response res) {
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
            LogInObject l = mapper.readValue(body, LogInObject.class);
            Database d = new Database();
            String token = d.getTokenByUser(l.username, l.password);
            String jsonRes = "{\"token\":\"" + token + "\"}";
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
