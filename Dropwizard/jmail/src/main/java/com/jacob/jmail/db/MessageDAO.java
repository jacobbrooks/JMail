package com.jacob.jmail.db;


import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import com.jacob.jmail.core.Message;

public interface MessageDAO {
	
	@SqlUpdate("insert into Messages (token, recipient, subject, message) values (:token, :recipient, :subject, :message)")
	void insert(@Bind("token") String token, @Bind("recipient") String recipient, @Bind("subject") String subject, @Bind("message") String message);
	
	@SqlQuery("select * from messages where recipient = :recipient")
	@RegisterFieldMapper(Message.class)
	List<Message> inbox(@Bind("recipient") String recipient);
	
}
