package user.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;

/**
 * Servlet implementation class UserPhoneCheckServlet
 */
@WebServlet("/user/userPhoneCheck")
public class UserPhoneCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPhoneCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userPhone = request.getParameter("checkPhone");
		System.out.println(userPhone);
		
		int checkPhone = new UserService().userPhoneCheck(userPhone);
		
		System.out.println(checkPhone);
		String isUsable = checkPhone == 1 ? "true" : "false";
		System.out.println("isUsable@servlet = "+ isUsable);
		
//		String loc = request.getContextPath() + "/user/userIdCheck?userId="+userId;
		System.out.println("userPhone="+userPhone);
		System.out.println("checkPhone="+checkPhone);

		
		//3. view단 처			

		request.setAttribute("checkPhone", checkPhone);
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().append(isUsable);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
