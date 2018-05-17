import java.io.IOException;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class SignUp {

	private Request req;
	private Response res;

	public SignUp(Request req, Response res) {
		this.req = req;
		this.res = res;
		processRequest();
	}

	public Response response() {
		return res;
	}

	private void processRequest() {
		String body = req.body();
		try {
			ObjectMapper mapper = new ObjectMapper();
			User u = mapper.readValue(body, User.class);
			String token = generateToken(u);
			Database d = new Database();
			d.createUser(u.firstname, u.lastname, u.username, u.password, token);
			String response = "{\"token\":\"" + token + "\"}";
			res.type("application/json");
			res.body(response); 
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String generateToken(User user) {
		String fullName = user.firstname + user.lastname;
		int nameSum = asciiSum(fullName);
		int passwordSum = asciiSum(user.password) / user.password.length();
		String token = user.username + nameSum + passwordSum;
		return token;
	}

	private int asciiSum(String s){
	      int asciiSum = 0;
	      for(int i = 0; i < s.length(); i++){
	         char c = s.charAt(i);
	         int ascii = c - '0';
	         asciiSum += ascii;
	      }
	      return asciiSum;
	}
	      
}
