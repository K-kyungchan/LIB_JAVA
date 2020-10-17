package service;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;

import controller.Main;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;
import dao.BoardDao;

public class BoardService {

	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String password = "java";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private static BoardService instance;

	public static BoardService getInstance() {
		if (instance == null) {
			instance = new BoardService();
		}
		return instance;
	}

	private BoardDao boardDao = BoardDao.getInstance();
	private InsideInfor inside = InsideInfor.getInstance();

	// 회원 로그인 성공시
	public int UserM() {

		System.out.println("============================================");
		System.out.println("1.도서검색  2.회원정보수정/연장  3.공지글 조회  4.로그아웃");
		System.out.println("============================================");
		System.out.println("입력>");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			inside.BookRe();

		case 2:
			inside.update(password, null);
		case 3:
            inside.list();
		case 4:
//			System.out.println("로그아웃 성공");
//			return View.HOME;
		default:
//			System.out.println("잘못된 입력입니다");
			return View.USERCH;
		}
	}

	// 관리자 로그인 성공시
	public int ADM() {

		System.out.println("==============================================");
		System.out.println("1.도서관리  2.공지글관리  3.회원관리  4.도서대여  5.로그아웃");
		System.out.println("==============================================");
		System.out.println("입력>");

		int input = ScanUtil.nextInt();

		 switch (input) {
		 case 1:
		      inside.BookMana();
		 case 2:
		      inside.ListMana();
		 case 3:
		      inside.UserMana();
		 case 4: 
			 inside.BookRent();
		 }
		return View.ADCH;
	}

}
