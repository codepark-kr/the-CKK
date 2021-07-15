package user.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.member.model.service.UserService;

/**
 * Servlet implementation class UserEmailServlet
 */
@WebServlet("/user/userEmailCheck")
public class UserEmailCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEmailCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String userEmail = request.getParameter("checkEmail");
			System.out.println(userEmail);
			
			int checkEmail = new UserService().userEmailCheck(userEmail);
			
			System.out.println(checkEmail);
			String isUsable = checkEmail == 1 ? "true" : "false";
			System.out.println("isUsable@servlet = "+ isUsable);
			

			System.out.println("UserEmail="+userEmail);
			System.out.println("EmailCheck="+checkEmail);
	
			
			//3. view단 처			
	
			request.setAttribute("checkEmail", checkEmail);
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
