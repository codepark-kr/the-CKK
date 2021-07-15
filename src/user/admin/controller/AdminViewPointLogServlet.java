package user.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import point.model.service.PointService;
import point.model.vo.Point;
import user.member.model.service.UserService;
import user.vo.User;

/**
 * Servlet implementation class AdminViewPointLogServlet
 */
@WebServlet("/admin/viewPointLog")
public class AdminViewPointLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String userId = request.getParameter("userId");
	
		List<Point> pointView = new PointService().viewPoint(userId);
		User commission = new PointService().selectCommGradeOne(userId);
		User selectedUser = new UserService().selectOne(userId);
		
		
		

		request.setAttribute("selectedUser", selectedUser);
		request.setAttribute("pointView", pointView);
		request.setAttribute("commission", commission);
		request.getRequestDispatcher("/WEB-INF/views/member/admin/pointView.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
		
		String admin = memberLoggedIn.getUserId();
		String userId = request.getParameter("userId");
		
		int pointAmount = Integer.parseInt(request.getParameter("pointAmount"));
		String pointContent = request.getParameter("pointContent");

		Point point = new Point(0, userId, "O",pointContent ,pointAmount,null);
		int result = new PointService().minusPoint(point);
		
		String msg = "";
		String loc = "";
		String view = "/WEB-INF/views/common/msg.jsp";	
		
		if(result > 0) {
			msg = "포인트 환불 처리 되었습니다.";
			loc = request.getContextPath() + "/admin/viewPointLog?userId=" + userId;
			
			User updateUser = new UserService().selectOne(userId);
			memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
			
			if(updateUser.getUserId().equals(memberLoggedIn.getUserId())) {
				session.setAttribute("memberLoggedIn", updateUser);
			}
			
		}
		
		else {
			msg = "포인트 환불 처리에 실패하였습니다. ";
			loc = request.getContextPath() + "/admin/viewPointLog?userId=" + userId;
			
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

}
