package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import controller.Main;
import dao.AdminDao;
import dao.BoardDao;
import dao.UserDao;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;

public class InsideInfor {

	private static final String InputBook = null;
	private static InsideInfor instance;
	private UserDao Book = UserDao.getInstance();
	private BoardDao BoDa = BoardDao.getInstance();
	private AdminDao AdDa = AdminDao.getInstance();
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String password = "java";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public static InsideInfor getInstance() {
		if (instance == null) {
			instance = new InsideInfor();
		}
		return instance;
	}

	// 도서검색 선택시
	public int BookRe() {

		System.out.println("=============================");
		System.out.println("1.도서조회     2.도서신청   3.뒤로가기");
		System.out.println("=============================");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();

		switch (input) {
		// 도서조회
		case 1:
			System.out.println("===========================================");
			System.out.println("1.도서제목 조회   2.저자조회   3.서적코드 조회 4.뒤로가기");
			System.out.println("===========================================");
			int input2 = ScanUtil.nextInt();
			switch (input2) {
			case 1:
				System.out.println("도서제목>");
				String booknm = ScanUtil.nextLine();
				String x = null;
				Map<String, Object> Booknm = Book.Booknm(booknm, x);
				if (Booknm == null) {
					System.out.println("===================");
					System.out.println("존재하지 않는 도서 입니다.");
					System.out.println("===================");
					return BookRe();
				} else {
					System.out.println("==================================");
					System.out.println("도서정보 : " + booknm + "는 보유중입니다. ");
					System.out.println("==================================");
					return BookRe();

				}
			case 2:
				System.out.println("저자이름>");
				String AuNm = ScanUtil.nextLine();
				String xs = null;
				Map<String, Object> Aunm = Book.Booknm(AuNm, xs);
				if (AuNm == null) {
					System.out.println("===================");
					System.out.println("존재하지 않는 저자 입니다.");
					System.out.println("===================");
					return BookRe();
				} else {
					System.out.println("==================================");
					System.out.println("저자정보 : " + AuNm + "는 보유중입니다. ");
					System.out.println("==================================");
					return BookRe();

				}
			case 3:
				System.out.println("서적코드>");
				String Bkco = ScanUtil.nextLine();
				String s = null;
				Map<String, Object> BookCo = Book.BookCo(Bkco, s);
				if (BookCo == null) {
					System.out.println("===================");
					System.out.println("존재하지 않는 코드 입니다.");
					System.out.println("===================");
					return BookRe();
				} else {
					System.out.println("==================================");
					System.out.println("코드정보 : " + Bkco + "는 보유중입니다. ");
					System.out.println("==================================");
					return BookRe();
				}
			case 4:
				return BookRe();
			}
		case 2:
			System.out.print("제목>");
			String title = ScanUtil.nextLine();
			// 31548456121
			try {
				con = DriverManager.getConnection(url, user, password);
				Map<String, Object> logx = Main.LoginUser;

				String sql = "INSERT INTO LIBAPPLYBOARD VALUES((select nvl(max(FINDNO), 0) + 1 from LIBAPPLYBOARD),?,?,sysdate,1,1)";

				ps = con.prepareStatement(sql);
				ps.setString(1, title);
				ps.setObject(2, logx.get("USERNO"));

				int result = ps.executeUpdate();
				if (0 < result) {
					System.out.println("등록이 완료되었습니다.");
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
		case 3:
			return View.USERCH;

		}

		return View.USERCH;

	}

	// 회원 개인정보수정
	public int update(String sql, List<Object> param) {
		System.out.println("====================");
		System.out.println("1.내정보 수정  2.도서연장");
		System.out.println("====================");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:

			System.out.println("전화번호 수정>");
			String PhonNum = ScanUtil.nextLine();
			System.out.println("주소 수정>");
			String Address = ScanUtil.nextLine();

			try {
				con = DriverManager.getConnection(url, user, password);

				sql = "update LIBUSER " + "set UPHONE = ? , UADDRESS = ? ";
				ps = con.prepareStatement(sql);
				ps.setString(1, PhonNum);
				ps.setString(2, Address);

				int result = ps.executeUpdate();
				if (0 < result) {
					System.out.println("수정이 완료되었습니다.");
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
		case 2:
			// 도서연장
			BoDa.ex();

		}

		return View.USERCH;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 게시판보는거 일단 대충
	public void list() {

		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select BOARDNO, BTITLE, BOARDWIRTER, BOARDDATE "
					+ "from LIBBOARD";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println("공지번호 : " + rs.getInt("BOARDNO"));
				System.out.println("공지제목 : " + rs.getString("BTITLE"));
				System.out.println("작성자 : " + rs.getString("BOARDWIRTER"));
				System.out.println("작성일 : " + rs.getDate("BOARDDATE"));

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

	public void BookMana() {
		System.out.println("==============================================");
		System.out.println("1.도서등록   2.도서수정  3.도서삭제  4.대출확인  5.도서조회");
		System.out.println("==============================================");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			BoDa.InputBook();
			break;
		case 2:
			BoDa.UpDateBook();
			break;
		case 3:
			BoDa.DeleteBook();
			break;
		case 4:
			BoDa.BookRent();
			break;
		case 5:
			BoDa.BookJo();
			break;
		}

	}

	public void ListMana() {
		System.out.println("============================");
		System.out.println("1.공지등록   2.공지수정  3.공지삭제");
		System.out.println("============================");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			BoDa.ListIn();
			break;
		case 2:
			BoDa.ListUp();
			break;
		case 3:
			BoDa.DeleteList();
		}
	}

	public void UserMana() {
		System.out.println("======================================= ");
		System.out.println("1.회원등록   2.회원수정  3.회원삭제  4.회원정보조회   ");
		System.out.println("======================================= ");
		System.out.println("입력>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			AdDa.InputUser();
			break;
		case 2:
			AdDa.UpdateUser();
			break;
		case 3:
			AdDa.DelUser();
		case 4:
			AdDa.AllUser();

		}
	}

	// 도서대여시키는거
	public void BookRent() {
		System.out.println("회원번호를 입력하세요");
		System.out.println("입력>");
		String UserNO = ScanUtil.nextLine();
		System.out.println("도서코드를 입력하세요");
		System.out.println("입력>");
		String Bookco = ScanUtil.nextLine();
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from libuser";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (UserNO.equals(rs.getString("USERNO"))) {
					String sql1 = "INSERT INTO libhistory VALUES((select nvl(max(historyno), 1) + 1 from libhistory),?,?,sysdate,null,sysdate +10, 0)";

					ps = con.prepareStatement(sql1);
					ps.setString(1, UserNO);
					ps.setString(2, Bookco);

					ps.executeUpdate();
					System.out.println("도서를 대여했습니다");
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