package service;

import java.util.HashMap;
import java.util.Map;

 
import controller.Main;
import dao.UserDao;
import util.ScanUtil;
import util.View;

public class Login {

	private Login() {
	}

	private static Login instance;

	public static Login getInstance() {
		if (instance == null) {
			instance = new Login();
		}
		return instance;
	}
    
	private UserDao userDao = UserDao.getInstance();

	 
        //회원 로그인 
	public int login() {
		System.out.println("===============로그인===============");
		System.out.println("회원번호>");
		String userno = ScanUtil.nextLine();
		String password = null;
		
		Map<String, Object> user = userDao.UserNum(userno, password);
		
		if(user == null){
			System.out.println("존재하지 않는 회원번호 입니다.");
		}else{
			System.out.println("로그인 성공🙉");
		
			Main.LoginUser = user;
			
			return View.USERCH;
		}
		return View.USERLOGIN;
		
	}
	   // 관리자 로그인
	public int login2() {
		System.out.println("====로그인====");
		System.out.println("관리자ID>");
		String ADID = ScanUtil.nextLine();
		System.out.println("패스워드>");
		String ADPASS = ScanUtil.nextLine();
		
		Map<String, Object> user = userDao.ADNUM(ADID, ADPASS);
		
		if(user == null){
			System.out.println("존재하지않는 ID 혹은 패스워드입니다.");
		}else{
			System.out.println("로그인 성공🙉");
		
			Main.LoginUser = user;
			
			return View.ADCH;
		}
		return View.ADMINLOGIN;
		
	}
}
