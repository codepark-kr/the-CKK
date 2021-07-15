package user.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;
import user.vo.User;

/**
 * Servlet implementation class UserEnrollServlet
 */
@WebServlet("/user/enroll")
public class UserEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEnrollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/member/user/userEnroll.jsp")
		   .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		String birthday_ = request.getParameter("birthday");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		
		Date birthday = null;
		if(birthday_ != null && !"".equals(birthday_))
		birthday = Date.valueOf(birthday_);
		
		User users = new User(userId, password, 
							  UserService.USER_GRADE, userName, 
							  gender, birthday, 
							  email, address, 
							  phone, null, 
							  0,null,null);
		
		int result = new UserService().userJoin(users);
		
		//뷰단 처리
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = request.getContextPath();
		if(result > 0) {
			
			msg = "가입을 축하드립니다";
			loc = request.getContextPath();
			System.out.println("enroll users=" + users);
		}else {
			msg = "가입에 실패 하셨습니다.";
			loc = request.getContextPath() + "/user/enroll"; 
			System.out.println("enroll users=" + users);
		}
		System.out.println("enroll result=" + result);
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

}
