package user.member.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;

/**
 * Servlet implementation class UserIdCheckServlet
 */
@WebServlet("/user/userIdCheck")
public class UserIdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserIdCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				String userId = request.getParameter("checkId");
				System.out.println(userId);
				
				int checkId = new UserService().userIdCheck(userId);
				
				System.out.println(checkId);
				String isUsable = checkId == 1 ? "true" : "false";
				System.out.println("isUsable@servlet = "+ isUsable);
				
//				String loc = request.getContextPath() + "/user/userIdCheck?userId="+userId;
				System.out.println("idCheck="+userId);
				System.out.println("idCheck="+checkId);
		
				
				//3. view단 처			
	
				request.setAttribute("checkId", checkId);
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
