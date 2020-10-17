package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.sql.DATE;
import controller.Main;
import service.BoardService;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;

public class BoardDao {

	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "hr";
	private String password = "java";

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private static BoardDao instance;

	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 도서연장
	public int ex() {

		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select historyno, title,expectdate "
					+ "from (select a.*, b.title "
					+ "from libhistory a , libbookinfo b "
					+ " where a.bookno = b.bookno)";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			// System.out.println("안녕");
			while (rs.next()) {
				System.out.println("내역번호 :" + rs.getInt("historyno")
						+ "\t도서명 :" + rs.getString("title") + "\t반납예정일 :"
						+ rs.getDate("expectdate"));
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
			System.out.println("내역번호 입력");
			int input = ScanUtil.nextInt();
			con = DriverManager.getConnection(url, user, password);
			String sql = "select historyno, title,expectdate "
					+ "from (select a.*, b.title "
					+ "from libhistory a , libbookinfo b "
					+ " where a.bookno = b.bookno)";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (input == rs.getInt("historyno")) {
					String sql1 = "update libhistory set expectdate = expectdate + 10"
							+ " where historyno = ?";
					ps = con.prepareStatement(sql1);
					ps.setInt(1, input);
					ps.executeUpdate();
					System.out.println("연장완료");

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

		return View.USERCH;

	}

	// 관리자 도서등록
	public void InputBook() {
		System.out.println("서적코드를 입력해주세요>");
		long Bookcd = ScanUtil.nextInt(); // 서적코드
		System.out.println("도서제목을 입력해주세요>");
		String Booknm = ScanUtil.nextLine(); // 도서제목
		System.out.println("저자를 입력해주세요>");
		String Bookau = ScanUtil.nextLine(); // 도서저자
		System.out.println("출판사를 입력해주세요>");
		String Bookpu = ScanUtil.nextLine(); // 도서출판사
		// System.out.println("출간일를 입력해주세요>");
		// int Bookdate = ScanUtil.nextInt(); // 도서출간일

		try {
			con = DriverManager.getConnection(url, user, password);
			Map<String, Object> logx = Main.LoginUser;
			String sql = "INSERT INTO LIBBOOKINFO VALUES(?,?,?,?,TO_DATE('20201111','YYYYMMDD'),'KYUNGCHAN',0)";

			ps = con.prepareStatement(sql);
			ps.setLong(1, Bookcd);
			ps.setObject(2, Booknm);
			ps.setObject(3, Bookau);
			ps.setObject(4, Bookpu);
			// ps.setInt(5, Bookdate);
			// ps.setObject(5, logx.get("ADMINID"));

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("도서등록이 완료되었습니다.");
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

	// 책 정보 수정
	public void UpDateBook() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBBOOKINFO";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("서적코드 :" + rs.getString("BOOKNO")
						+ "\t도서제목 :" + rs.getString("title") + "\t저자 :"
						+ rs.getString("AUTHOR") + "\t출판사 :"
						+ rs.getString("PUBLISHER") + "\t출고일 :"
						+ rs.getDate("PDATE") + "\t대여여부 :"
						+ rs.getInt("RENTYESNO"));
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
			System.out.println("변경하실 책 코드를 입력해주세요>");
			String input = ScanUtil.nextLine();

			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBBOOKINFO";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("변경된 서적코드를 입력해주세요>");
			long Bookcd = ScanUtil.nextInt(); // 서적코드
			System.out.println("변경된 도서제목을 입력해주세요>");
			String Booknm = ScanUtil.nextLine(); // 도서제목
			System.out.println("변경된 저자를 입력해주세요>");
			String Bookau = ScanUtil.nextLine(); // 도서저자
			System.out.println("변경된 출판사를 입력해주세요>");
			String Bookpu = ScanUtil.nextLine(); // 도서출판사
			// System.out.println("변경된 출간일를 입력해주세요>");
			// int Bookdate = ScanUtil.nextInt(); // 도서출간일
			while (rs.next()) {
				if (input.equals(rs.getString("BOOKNO"))) {
					String sql1 = "update LIBBOOKINFO set BOOKNO = ? , TITLE = ? , AUTHOR = ? , PUBLISHER = ? "
							+ " where BOOKNO = '" + input + "' ";

					ps = con.prepareStatement(sql1);
					ps.setLong(1, Bookcd);
					ps.setString(2, Booknm);
					ps.setString(3, Bookau);
					ps.setString(4, Bookpu);

					ps.executeUpdate();
					System.out.println("도서정보 수정완료");

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

	// 도서삭제
	public void DeleteBook() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBBOOKINFO";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("서적코드 :" + rs.getString("BOOKNO")
						+ "\t도서제목 :" + rs.getString("title") + "\t저자 :"
						+ rs.getString("AUTHOR") + "\t출판사 :"
						+ rs.getString("PUBLISHER") + "\t출고일 :"
						+ rs.getDate("PDATE") + "\t대여여부 :"
						+ rs.getInt("RENTYESNO"));
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

			System.out.println("삭제하실 서적코드를 입력해주세요>");
			String input = ScanUtil.nextLine();
			String sql = "select * from LIBBOOKINFO";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (input.equals(rs.getString("BOOKNO"))) {
					String sql1 = "delete LIBBOOKINFO " + "where BOOKNO =  "
							+ input;

					ps = con.prepareStatement(sql1);

					ps.executeUpdate();
					System.out.println("도서삭제완료");

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

	// 도서 대여 여부 확인
	public void BookRent() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "  SELECT BOOKNO, TITLE, "
					+ "DECODE(RENTYESNO, 0, '보유중입니다', 1, '대출중입니다') RENTYESNO"
					+ " FROM LIBBOOKINFO";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("서적코드 :" + rs.getString("BOOKNO")
						+ "   도서제목 :" + rs.getString("title") + "\t대여여부 :"
						+ rs.getString("RENTYESNO"));

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

	// 가지고있는 도서조회
	public void BookJo() {

		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "SELECT * FROM LIBBOOKINFO ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("도서코드 : " + rs.getString("BOOKNO")
						+ " / 도서명 : " + rs.getString("TITLE"));

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

	// 공지 등록
	public void ListIn() {
		try {

			System.out.println("공지제목을 입력해주세요.>");
			String Bookcd = ScanUtil.nextLine(); // 공지제목
			System.out.println("공지내용을 입력해주세요>");
			String Booknm = ScanUtil.nextLine(); // 공지내용
			con = DriverManager.getConnection(url, user, password);
			String sql = "INSERT INTO LIBBOARD VALUES((select nvl(max(BOARDNO), 0) + 1 from LIBBOARD),?,?,'KYUNGCHAN',SYSDATE,'KYUNGCHAN')";

			ps = con.prepareStatement(sql);
			ps.setString(1, Bookcd);
			ps.setString(2, Booknm);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("공지등록이 완료되었습니다.");
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

	// 공지수정
	public void ListUp() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBBOARD";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("게시판번호 :" + rs.getString("boardno")
						+ "\t제목 :" + rs.getString("btitle") + "\t 작성자 :"
						+ rs.getString("BOARDWIRTER"));
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
			System.out.println("수정하실 게시글 번호를 입력해주세요>");
			String input = ScanUtil.nextLine();

			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBBOARD";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("변경될 게시판제목을 입력해주세요>");
			String Boardnm = ScanUtil.nextLine(); // 게시글제목
			System.out.println("변경될 내용을 입력해주세요>");
			String Boardcon = ScanUtil.nextLine(); // 게시글내용

			while (rs.next()) {
				if (input.equals(rs.getString("BOARDNO"))) {
					String sql1 = "update LIBBOARD set BTITLE = ? , BCONTENT = ?  "
							+ " where BOARDNO = '" + input + "' ";

					ps = con.prepareStatement(sql1);
					ps.setString(1, Boardnm);
					ps.setString(2, Boardcon);

					ps.executeUpdate();
					System.out.println("게시글정보 수정완료");

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

	// 공지삭제
	public void DeleteList() {
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from LIBBOARD";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("공지번호 :" + rs.getString("BOARDNO")
						+ "\t공지제목 :" + rs.getString("BTITLE"));
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

			System.out.println("삭제하실 공지번호를 입력해주세요>");
			String input = ScanUtil.nextLine();
			String sql = "select * from LIBBOARD";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (input.equals(rs.getString("BOARDNO"))) {
					String sql1 = "delete LIBBOARD " + "where BOARDNO =  "
							+ input;

					ps = con.prepareStatement(sql1);

					ps.executeUpdate();
					System.out.println("공지삭제완료");

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

}
