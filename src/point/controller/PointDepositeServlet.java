package point.controller;

import java.io.IOException;
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
 * Servlet implementation class PointDepositeServlet
 */
@WebServlet("/point/pointDeposit")
public class PointDepositeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/mypage/point/pointDeposit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
		
		//사용자 입력값 처리 -> 1.포인트 환전량 | 2.사용자 Id
		int pointAmount = Integer.parseInt(request.getParameter("pointAmount"));
		String userId = request.getParameter("userId");
		
		
		//일반회원 | 튜터 수수료 설정
		User temp = (User)session.getAttribute("commission");
		double commissionCS = 0.0;
		
		if("US".equals(memberLoggedIn.getCommGrade())) {
			commissionCS = temp.getCommission();
		}
		else {
			commissionCS = 0.03;
		}
		
		//업무로직 -> 포인트 로그 테이블에 기록
		Point point = new Point(0, userId, "I", null, pointAmount, null);
		int result = new PointService().updatePoint(point);
		
		System.out.println("result@servlet =" +result);
		
		//뷰단 처리
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = "";
		
		if(result > 0) {
			msg = "포인트 충전이 완료되었습니다.";
			loc = request.getContextPath() + "/point/pointManagement?userId=" + userId;
			
			//세션 로그인 정보 갱신
			User updateUser = new UserService().selectOne(userId);
			memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
			
			if(updateUser.getUserId().equals(memberLoggedIn.getUserId())) {
				session.setAttribute("memberLoggedIn", updateUser);
			}
			
		}
		else {
			msg = "포인트 충전에 실패하였습니다.";
			loc = request.getContextPath() + "/point/pointManagement?userId=" + userId;
		}

		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
	}

}
