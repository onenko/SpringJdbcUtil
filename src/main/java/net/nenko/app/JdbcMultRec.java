package net.nenko.app;

import java.util.List;

public class JdbcMultRec extends JdbcBase {

	public JdbcMultRec(String url, String user, String pass, String drvr) {
		super(url, user, pass, drvr);
	}

	@Override
	public void insert(ObjectToStore obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ObjectToStore> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String sKey, String data) {
		// TODO Auto-generated method stub

	}

}
