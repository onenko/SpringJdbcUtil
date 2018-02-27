package net.nenko.app;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public abstract class JdbcBase {
	protected final JdbcTemplate jt;

	public JdbcBase(String url, String user, String pass, String drvr) {
    	SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    	dataSource.setDriverClassName(drvr);
    	dataSource.setUrl(url);
    	dataSource.setUsername(user);
    	dataSource.setPassword(pass);

    	jt = new JdbcTemplate(); 
    	jt.setDataSource(dataSource); 
	}

	abstract public void insert(ObjectToStore obj);
	abstract public List<ObjectToStore> select();
	abstract public void update(String sKey, String data);
}
