package controller;

import java.util.Map;

import dao.BoardDao;
import dao.UserDao;
import service.BoardService;
import service.InsideInfor;
import service.Login;
import util.ScanUtil;
import util.View;

public class Main {

	public static void main(String[] args) {

		new Main().start();
	}

	private static Main instance;

	public static Main getInstance() {
		if (instance == null) {
			instance = new Main();
		}
		return instance;
	}

	public static Map<String, Object> LoginUser;

	private Login login = Login.getInstance();
	private BoardService boardService = BoardService.getInstance();
	private InsideInfor insid = InsideInfor.getInstance();
	private BoardDao BD = BoardDao.getInstance();
	private void start() {
		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				view = home();
				break;
			case View.USERLOGIN:
				view = login.login();
				break;
			case View.ADMINLOGIN:
				view = login.login2();
				break;
			case View.USERCH:
				view = boardService.UserM();
				break;
			case View.ADCH:
				view = boardService.ADM();
				break;
			case View.BOOKINFO:
				view = insid.BookRe();
			 
			}
		}
	}

	private int home() {
		System.out.println("-----------------------");
		System.out.println("1.회원로그인     2.관리자로그인");
		System.out.println("-----------------------");
		System.out.println("번호입력>");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.USERLOGIN;
		case 2:
			return View.ADMINLOGIN;
        default : System.out.println("올바르지 않은 입력입니다");
		}
		return View.HOME;
	}

}
