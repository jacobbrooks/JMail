import java.sql.*;
import java.util.ArrayList;

public class Database {
	private Statement statement;

	public Database(){
		try{
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/jmail?useSSL=false", "jacobbrooks", "jaco4567");
			this.statement = c.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void createUser(String firstName, String lastName, String username, String password, String token){
		try{
			String userCountQuery = "select count(*) from users;";
			ResultSet results = statement.executeQuery(userCountQuery);
			int id = 0;
			while(results.next()){
				id = results.getInt("count(*)");
			}
			String createUserQuery = "insert into users values (" + id + ", '" + firstName + "', '" + lastName + "', '"
				+ username + "', '" + password + "', '" + token + "')";
			statement.executeUpdate(createUserQuery);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void createMessage(String token, String receiver, String subject, String message){
		try{
			String messageCountQuery = "select count(*) from messages;";
			ResultSet results = statement.executeQuery(messageCountQuery);
			
			int messageID = 0;
			while(results.next()){
				messageID = results.getInt("count(*)");
			}

			String senderIDQuery = "select id from users where token = '" + token + "';";
			results = statement.executeQuery(senderIDQuery);
			int senderID = 0;
			while(results.next()){
				senderID = results.getInt("id");
			}

			String receiverIDQuery = "select id from users where username = '" + receiver + "';";
			results = statement.executeQuery(receiverIDQuery);
			int receiverID = 0;
			while(results.next()){
				receiverID = results.getInt("id");
			}

			for(int i = 0; i < message.length(); i++){
				if(message.charAt(i) == '\''){
					message.replace(message.charAt(i), '\'');
				}
			}

			String createMessageQuery = "insert into messages values (" + messageID + ", " + senderID + ", " + receiverID + ", '" + subject + "', '" + message + "')"; 
			statement.executeUpdate(createMessageQuery);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public String[][] getInbox(String token, String from){
		ArrayList<String[]> inbox = new ArrayList<String[]>();
		try{
			String userIDQuery = "select id from users where token = '" + token + "';";
			ResultSet results = statement.executeQuery(userIDQuery);
			int id = 0;
			while(results.next()){
				id = results.getInt("id");
			}

			String inboxQuery = "select username, subject, message, messages.id from messages, users where messages.fromID = users.id and messages.toID = " + id + ";";

			if(!from.equals("*")){
				inboxQuery = "select username, subject, message, messages.id from messages, users where username = '" + from + "' and messages.fromID = users.id and messages.toID = " + id + ";";
			}
	
			results = statement.executeQuery(inboxQuery);
			while(results.next()){
				String fromUsername = results.getString("username");
				String subject = results.getString("subject");
				String message = results.getString("message");
				int mID = results.getInt("messages.id");
				String messageID = Integer.toString(mID);
				String[] messageInfo = {fromUsername, subject, message, messageID};
				inbox.add(messageInfo);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		String[][] returnInbox = new String[inbox.size()][4];
		for(int i = 0; i < returnInbox.length; i++){
			for(int j = 0; j < 4; j++){
				returnInbox[i][j] = inbox.get(i)[j];
			}
		}
		return returnInbox;
	}

	public void deleteMessage(String token, int id){
		try{
			String userIDQuery = "select id from users where token = '" + token + "';";
			ResultSet results = statement.executeQuery(userIDQuery);
			int userID = 0;
			while(results.next()){
				userID = results.getInt("id");
			}

			String deleteQuery = "delete from messages where id = " + id + " and toID = " + userID + ";";
			statement.executeUpdate(deleteQuery);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void deleteAccount(String token){
		try{
			String deleteQuery = "delete from users where token = '" + token + "';";
			statement.executeUpdate(deleteQuery);
		}catch(SQLException e){	
			e.printStackTrace();
		}
	}

	public String getTokenByUser(String username, String password){
		String token = "";
		try{
			String tokenQuery = "select token from users where username = '" + username + "' and password = '" + password  + "';";
			ResultSet results = statement.executeQuery(tokenQuery);
			while(results.next()){
				token = results.getString("token");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return token;
	}

}
