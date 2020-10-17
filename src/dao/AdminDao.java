package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import controller.Main;
import util.JDBCUtil;
import util.ScanUtil;

public class AdminDao {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "hr";
	private String password = "java";

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private static AdminDao instance;

	public static AdminDao getInstance() {
		if (instance == null) {
			instance = new AdminDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 유저 등록
	public void InputUser() {
		System.out.println("새로운 회원번호를 입력해주세요>");
		int UserCo = ScanUtil.nextInt();

		System.out.println("회원이름>");
		String UserNm = ScanUtil.nextLine();

		System.out.println("생년월일>");
		String UserDay = ScanUtil.nextLine();

		System.out.println("주소>");
		String UserAddress = ScanUtil.nextLine();

		System.out.println("전화번호>");
		String UserPhone = ScanUtil.nextLine();

		try {
			con = DriverManager.getConnection(url, user, password);
			Map<String, Object> logx = Main.LoginUser;
			String sql = "INSERT INTO LIBUSER VALUES(?,?,?,?,?,sysdate,'KYUNGCHAN')";

			ps = con.prepareStatement(sql);
			ps.setInt(1, UserCo);
			ps.setString(2, UserNm);
			ps.setString(3, UserDay);
			ps.setString(4, UserAddress);
			ps.setString(5, UserPhone);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("회원등록이 완료되었습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 회원정보 수정
	public void UpdateUser() {

		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBUSER";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("회원번호 :" + rs.getString("USERNO")
						+ "\t회원이름 :" + rs.getString("UNAME") + "\t전화번호 :"
						+ rs.getString("UPHONE")

				);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}

		}

		try {
			System.out.println("변경하실 회원정보를 입력해주세요>");
			String input = ScanUtil.nextLine();

			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBUSER";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("변경된 회원코드를 입력해주세요>");
			String Userno = ScanUtil.nextLine();
			System.out.println("변경된 회원이름을 입력해주세요>");
			String Usernm = ScanUtil.nextLine();
			System.out.println("변경된 생일를 입력해주세요>");
			String Userday = ScanUtil.nextLine();
			System.out.println("변경된 주소를 입력해주세요>");
			String Userad = ScanUtil.nextLine();
			System.out.println("변경된 전화번호를 입력해주세요>");
			String UserPh = ScanUtil.nextLine();
			while (rs.next()) {
				if (input.equals(rs.getString("USERNO"))) {
					String sql1 = "update LIBUSER set USERNO = ? , UNAME = ? , UBIRTH = ? , UADDRESS = ? , UPHONE = ?"
							+ " where USERNO = '" + input + "' ";

					ps = con.prepareStatement(sql1);
					ps.setString(1, Userno);
					ps.setString(2, Usernm);
					ps.setString(3, Userday);
					ps.setString(4, Userad);
					ps.setString(5, UserPh);

					ps.executeUpdate();
					System.out.println("회원정보 수정완료");
				} else {
					System.out.println("정보를 리터럴 형식에 맞게 넣으셈");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}

		}

	}

	// 회원 제거
	public void DelUser() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBUSER";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("회원번호 :" + rs.getString("userno")
						+ "\t회원이름 :" + rs.getString("uname") + "\t전화번호 : "
						+ rs.getString("uphone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}

		}

		try {
			con = DriverManager.getConnection(url, user, password);

			System.out.println("삭제하실 회원번호를 입력해주세요>");
			String input = ScanUtil.nextLine();
			String sql = "select * from LIBUSER";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (input.equals(rs.getString("userno"))) {
					String sql1 = "delete LIBUSER " + "where userno =  "
							+ input;

					ps = con.prepareStatement(sql1);

					ps.executeUpdate();
					System.out.println("회원삭제완료");

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 모든 회원정보 조회
	public void AllUser() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "SELECT * FROM LIBUSER ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("회원번호 : " + rs.getString("USERNO")
						+ " / 회원이름 : " + rs.getString("UNAME") + "/  회원생일 : "
						+ rs.getString("ubirth") + "/  회원주소 : "
						+ rs.getString("uaddress") + "/  전화번호 : "
						+ rs.getString("uphone") + "/  회원등록일 : "
						+ rs.getString("uadddate") + "/  관리자 : "
						+ rs.getString("adminid")

				);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

}
