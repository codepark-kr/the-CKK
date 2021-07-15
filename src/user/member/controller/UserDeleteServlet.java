package user.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;

/**
 * Servlet implementation class UserDeleteServlet
 */
@WebServlet("/user/delete")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");	
		
		int result = new UserService().deleteUser(userId);
		System.out.println("servlet@result=" + result);
		
		String msg = "";
		String loc = "";
		if(result > 0) {
			msg = "회원 탈퇴가 성공적을 완료되었습니다.";
			loc = request.getContextPath() + "/user/logout";
			
			//로그인 되어있는 아이디가 'admin'일 경우 -> 페이지 우회주소 변경 필요 (if문 작성)
			
		}
		else {
			msg = "회원 탈퇴에 실패하였습니다. 다시 시도해주세요.";
			loc = request.getContextPath() + "/user/update?userId=" + userId;
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
