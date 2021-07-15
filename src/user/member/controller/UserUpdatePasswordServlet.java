package user.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;
import user.vo.User;

/**
 * 사용자 비밀번호 변경 서블릿
 */
@WebServlet("/user/updatePassword")
public class UserUpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/user/updatePwd.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("new_password");
		
		//비밀번호 일치 여부 검사
		User user = new UserService().selectOne(userId);
			
		String msg = "";
		String loc = "";
		String view = "/WEB-INF/views/common/msg.jsp";
		//비밀번호 일치 시 새 비밀번호 입력값으로 변경 진행
		if(password.equals(user.getPassword())) {
			
			int result = new UserService().updatePassword(userId, newPassword);
			
			//비밀번호 변경 성공시
			if(result > 0) {
				msg = "비밀번호가 변경되었습니다. \\n다시 로그인 후 이용해주십시오.";
				loc = request.getContextPath() + "/user/logout";
				
			}
			//비밀번호 변경 실패시 (오류발생시)
			else {
				msg = "비밀번호 변경에 실패하였습니다. 다시 시도해주십시오.";
				loc = request.getContextPath() + "/user/updatePassword?userId=" + userId;
				
			}

		}
		//비밀번호 불 일치시 안내 메세지 
		else {
			msg = "입력하신 현재 비밀번호가 일치하지 않습니다. 확인 후 다시 이용해주십시오.";
			loc = request.getContextPath() + "/user/updatePassword?userId=" + userId;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
	
	}
}
