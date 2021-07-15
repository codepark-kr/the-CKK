package user.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.member.model.service.UserService;
import user.vo.User;

/**
 * 회원정보 수정 서블릿
 */
@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			request.setCharacterEncoding("utf-8");
		//사용자 입력값 가져오기
			String userId = request.getParameter("userId");
			String userName = request.getParameter("userName");
			String gender = request.getParameter("gender");
			String phone = request.getParameter("phone");
			String bDay = request.getParameter("birthday");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			
			//Date type parsing
			Date birthday = null;
			if(bDay != null && !"".equals(bDay)) {
				birthday = Date.valueOf(bDay);
			}
			
			//업무로직
			User u = new User(userId, null, null, 
								userName, gender, birthday, 
								email, address, phone, 
								null, 0, null, null);
			int result = new UserService().updateUser(u);
			//뷰단 처리
			String view = "/WEB-INF/views/common/msg.jsp";
			String msg = "";
			String loc = request.getContextPath() + "/user/view?userId=" +userId;
			//수정 성공
			if(result > 0) {
				//정보 수정 완료 알람
				msg = "회원 정보 수정에 성공하였습니다!";
				
				HttpSession session = request.getSession();
				User updateUser = new UserService().selectOne(userId);
				User userLoggedIn = (User)session.getAttribute("memberLoggedIn");
				
				if(userId.equals(userLoggedIn.getUserId())) {
					session.setAttribute("memberLoggedIn", updateUser);
				}
				//정보 수정 페이지로 보내줌
				
				
			}
			//수정 실패
			else {
				//정보 수정 실패 알람
				msg = "회원 정보 수정에 실패하였습니다. 다시 한번 시도하여주세요.";
				//정보 수정 페이지로
				
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher(view).forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
