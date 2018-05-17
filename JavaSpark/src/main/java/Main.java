import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
    		post("/signup", (req, res) -> new SignUp(req, res).response().body());
    		post("/message", (req, res) -> new Message(req, res).response().body());
    		post("/login", (req, res) -> new LogIn(req, res).response().body());
    		
    		get("/inbox", (req, res) -> new Inbox(req, res).response().body());
    		
    		delete("/delete/account", (req, res) -> new DeleteAccount(req, res).response().body());
    		delete("/delete/message", (req, res) -> new DeleteMessage(req, res).response().body());
    }
}