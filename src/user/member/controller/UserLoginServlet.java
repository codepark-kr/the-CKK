package user.member.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import point.model.service.PointService;
import user.member.model.service.UserService;
import user.vo.User;


@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
//		password = Utils.getEncryptedPassword(password);
		
		
		String saveId = request.getParameter("saveId");
		
		//2. 업무로직
		User user = new UserService().selectOne(userId);
//		System.out.println(user.toString());
	
		System.out.println("id, password@servlet = "+userId+" "+password);
		String view = "";
		//로그인 성공
		if(user != null && password.equals(user.getPassword())) {
			//로그인한 사용자정보를 session객체에 속성으로 저장
			//세션객체가 존재하면, 해당객체 반환.
			//세션객체가 존재하지 않으면, 생성해서 반환
			HttpSession session = request.getSession(true);
			session.setAttribute("memberLoggedIn", user);

			
			//마지막요청으로 부터 유효시간 설정(초)
//			session.setMaxInactiveInterval(10*60);
			
			//saveId관련 쿠키처리
			//아이디 저장을 체크했다면, saveId=honggd 쿠키생성
			//쿠키는 별도의 삭제메소드가 없어서 유효기간으로 삭제한다.
			Cookie cookie = new Cookie("saveId", userId);
			cookie.setPath(request.getContextPath());
			
			if(saveId != null)
				cookie.setMaxAge(7*24*60*60);
			else
				cookie.setMaxAge(0);
			response.addCookie(cookie);
			
			//로그인 성공시, 포워딩이 아닌 redirect처리함.
			//client에게 다시 요청할 주소를 응답으로 전달
			String referer = request.getHeader("referer");
			response.sendRedirect(referer);
		}
		
		//로그인 실패시, 경고창 띄움
		else {
			view = "/WEB-INF/views/common/msg.jsp";
			request.setAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
			request.setAttribute("loc", request.getContextPath());
			request.getRequestDispatcher(view)
				   .forward(request, response);
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
