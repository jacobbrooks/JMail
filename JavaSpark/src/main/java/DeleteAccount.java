import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import spark.Request;
import spark.Response;

public class DeleteAccount {
	private Request req;
	private Response res;
	
	public DeleteAccount(Request req, Response res) {
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
            DeleteAccountObject delete = mapper.readValue(body, DeleteAccountObject.class);
            d.deleteAccount(delete.token);
            String jsonRes = "{\"response\":\"account deleted\"}";
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
