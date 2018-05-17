package com.jacob.jmail.db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDAO {
	
	@SqlUpdate("insert into Users (firstname, lastname, username, password, token) values (:firstname, :lastname, :username, :password, :token)")
	void insert(@Bind("firstname") String firstname, @Bind("lastname") String lastname, @Bind("username") String username, @Bind("password") String password, @Bind("token") String token);
	
	@SqlQuery("select username from Users where token = ?")
	String getUsername(String token);
	
	@SqlQuery("select token from Users where username = ? and password = ?")
	String getToken(String username, String password);

}
