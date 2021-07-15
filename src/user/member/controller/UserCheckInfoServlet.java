package user.member.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;
import user.vo.User;

/**
 * 비밀번호 확인 서블릿
 * 
 * 0814 기본 서블릿 완성 
 *  - 비밀번호 암호화 필요
 */
@WebServlet("/user/checkInfo")
public class UserCheckInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/user/findPwd.jsp").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("id-chk");
		String userName = request.getParameter("name-chk");
		String bDay = request.getParameter("birth-chk");
		String phone = request.getParameter("phone-chk");
		String gender = request.getParameter("regi-gender");
		
		//bDay parsing
		Date birthday = null;
		if(bDay != null) {
			birthday = Date.valueOf(bDay);
		}
		
//		System.out.println(userId + userName + bDay + phone + gender);
		
		User user = new UserService().selectOne(userId);
		
		//사용자가 입력한 id값이 존재 하는지 검사
		if(user != null) {
			//id값이 존재한다면, 나머지 입력값과 데이터베이스 저장값 비교
			int result = 0;
			if(userId.equals(user.getUserId()) && 
								 userName.equals(user.getUserName()) && 
								 birthday.equals(user.getBirthday()) &&
								 phone.equals(user.getPhone()) && 
								 gender.equals(user.getGender())) {
				//정상처리 -> 랜덤 비밀번호로 비밀번호 update
				Random rnd = new Random();
				String rndNum = String.valueOf(rnd.nextInt(100000)+1);
				//자동으로 설정될 비밀번호
				String rndPwd = String.valueOf((char)(((int)rnd.nextInt(26) + 97))) + rndNum 
							  + String.valueOf((char)(((int)rnd.nextInt(26) + 97)));
//				System.out.println(rndPwd);
				
				//발급된 비밀번호로 업데이트
				result = new UserService().updatePassword(userId, rndPwd);
				
				//view단 처리
				String msg = "";
				String loc = "";
//				System.out.println("result@servlet = " +result);
					if(result > 0) {
						msg = "임시 비밀번호가 발급되었습니다. \\n임시 비밀번호 : " + rndPwd + "\\n 비밀번호를 수정 후 이용해주시기 바랍니다.";
						loc = request.getContextPath();
					}else {
						msg = "정보 조회에 심패하였습니다. 다시 시도하여주세요.";
						loc = request.getContextPath();
					}
				
				request.setAttribute("msg", msg);
				request.setAttribute("loc", loc);
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			}
			//입력값이 Id정보와 일치하지 않을 시 안내 페이지 ( 비밀번호 변경 안됨 )
			else {
				 String msg = "입력하신 값이 일치하지 않습니다. 다시 한번 확인해주세요.";
				 String loc = request.getContextPath() + "/user/checkInfo";
				 request.setAttribute("msg", msg);
				 request.setAttribute("loc", loc);
				 request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			}
		}
		//Id가 존재하지 않을 시 안내 페이지
		else {
			String msg = "아이디가 존재하지 않습니다. 확인 후 이용해 주세요.";
			String loc = request.getContextPath() + "/user/checkInfo";
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
	}
}
