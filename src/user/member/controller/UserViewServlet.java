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
 * 유저 단순 조회 서블릿
 */
@WebServlet("/user/view")
public class UserViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//아이디 가져오기
		String userId = request.getParameter("userId");
//		System.out.println("userId = " + userId);
		
		User u = new UserService().selectOne(userId);
//		System.out.println("user = " + u);
		
		
		String view = "";
		String msg = "";
		String loc = "";
		if(u != null) {
			view = "/WEB-INF/views/member/user/userView.jsp";
			request.setAttribute("user", u);
		}
		else {
			msg = "회원정보 불러오기에 실패하였습니다.";
			loc = request.getContextPath();
			view = "/WEB-INF/views/common/msg.jsp";
		}
		
		request.setAttribute("selectOne", u);
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
