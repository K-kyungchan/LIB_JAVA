package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDao {

	private UserDao() {
	}

	private static UserDao instance;

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	public int insertUser(Map<String, Object> p) {
		String sql = "INSERT INTO TB_JDBC_USER VALUES (?, ?, ?)";

		List<Object> param = new ArrayList<>();
		param.add(p.get("USER_ID"));
		param.add(p.get("PASSWORD"));
		param.add(p.get("USER_NAME"));

		return jdbc.update(sql, param);
	}

	public Map<String, Object> UserNum(String userno, String password) {
		String sql = "SELECT * " + " FROM LIBUSER" + " WHERE userno = ?";
        
		List<Object> param = new ArrayList<>();
		param.add(userno);

		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> ADNUM(String adminid, String adminpw) {
		String sql = "SELECT adminid, adminpw" + " FROM LIBADMIN"
				+ " WHERE adminid = ?" + "AND adminpw = ?";

		List<Object> param = new ArrayList<>();
		param.add(adminid);
		param.add(adminpw);

		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> Booknm(String title, String x) {
		String sql = "SELECT title" + " FROM LIBBOOKINFO" + " WHERE title = ?";

		List<Object> param = new ArrayList<>();
		param.add(title);

		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> Author(String author, String x) {
		String sql = "SELECT Author" + " FROM LIBBOOKINFO" + " WHERE Author = ?";

		List<Object> param = new ArrayList<>();
		param.add(author);

		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> BookCo(String Bookno, String x) {
		String sql = "SELECT bookno" + " FROM LIBBOOKINFO" + " WHERE bookno = ?";

		List<Object> param = new ArrayList<>();
		param.add(Bookno);

		return jdbc.selectOne(sql, param);
	}
    
}