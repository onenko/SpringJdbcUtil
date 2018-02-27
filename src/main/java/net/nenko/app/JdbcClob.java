package net.nenko.app;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * JdbcClob - persist long strings into CLOB field
 *
 * NOTE: we have 3 ways to write into CLOB:
 *  - just pass String argument to jdbcTemplate.update()
 *  - write string into Clob created in PreparedStatementSetter
 *  - write string into stream (not implemented here)
 * @author conenko
 */
public class JdbcClob extends JdbcBase {

	private static final String INSERT_SQL = "insert into LONG_STRING_IN_CLOB (NKEY, SKEY, LONG_STR) values (?,?,?)" ;
	private static final String SELECT_SQL = "select NKEY, SKEY, LONG_STR from LONG_STRING_IN_CLOB" ;
	private static final String UPDATE_SQL = "update LONG_STRING_IN_CLOB set LONG_STR=? where SKEY=?";

	public JdbcClob(String url, String user, String pass, String drvr) {
		super(url, user, pass, drvr);
	}

	@Override
	public void insert(ObjectToStore obj) throws DataAccessException {
		jt.update(INSERT_SQL, obj.nKey, obj.sKey, obj.longTextData);
	}

//	public void insert_Clob_version(ObjectToStore obj) throws DataAccessException {
//		jt.update(INSERT_SQL, new PreparedStatementSetter() {
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setLong(1,  obj.nKey);
//				ps.setString(2,  obj.sKey);
//				Clob clob = ps.getConnection().createClob();
//				clob.setString(0, obj.longTextData);
//				ps.setClob(3, clob);
//			}
//		});
//	}

	@Override
	public List<ObjectToStore> select() {
		List<ObjectToStore> objs = jt.query(SELECT_SQL,
			new RowMapper<ObjectToStore>() {
				@Override
				public ObjectToStore mapRow(ResultSet rs, int arg1) throws SQLException {
					String str = rs.getString("CLOB");
					ObjectToStore obj = new ObjectToStore(rs.getLong("NKEY"), rs.getString("SKEY"), str);
					return obj;
				}
		});
		return objs;
	}

	@Override
	public void update(String sKey, String data) {
		jt.update(UPDATE_SQL, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				Clob clob = ps.getConnection().createClob();
				clob.setString(0, data);
				ps.setClob(1, clob);
				ps.setString(2, sKey);
			}
		});
	}

}

