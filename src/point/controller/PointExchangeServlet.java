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
 * 튜터 포인트 환전 서블릿
 */
@WebServlet("/point/pointExchange")
public class PointExchangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PointService pointService = new PointService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/mypage/point/pointExchange.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//사용자 입력값 처리 -> 1.포인트 환전량 | 2.사용자 Id
		HttpSession session = request.getSession(true);
		User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
		String userId = memberLoggedIn.getUserId();
		double pointAmount = Integer.parseInt(request.getParameter("pointAmount"));
		
		
		//선생 포인트 환전 이벤트일때, 수수료 등급 가져오기
		User commission = (User)session.getAttribute("commission");
		
		//업무로직 -> 포인트 로그 테이블에 기록 (포인트 입력값 수수료 계산 후 입력)
		//포인트 로그 테이블에도 수수료 적용된 가격이 기록됨
		int var = (int)(pointAmount - (pointAmount*commission.getCommission()));
		int intoAdmin = (int)(pointAmount *commission.getCommission());
		
		//입력값이 현재 보유수량보다 적을 경우 -> 환전 불가처리
		if(memberLoggedIn.getPointSum() < pointAmount) {
			String msg = "포인트가 부족합니다.";
			String loc = request.getContextPath() + "/point/pointManagement?userId=" +userId;
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
		
		Point tutorPoint = new Point(0, userId, "O", null, (int)pointAmount, null);
		Point adminPoint = new Point(0, "admin", "I", "수수료 입금", intoAdmin, null);

		//환전 요청시 -> admin에게 수수료 추가까지 한 connection에서 진행
		int result = pointService.updatePointWithAdComm(tutorPoint, adminPoint);
//		int result = pointService.updatePoint(tutorPoint);
//		System.out.println("grade@servlet= " + grade);
//		System.out.println("result@servlet =" +result);
		
		//뷰 처리
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = "";
		
		if(result > 0) {
			msg = "포인트 환전이 완료되었습니다.";
			loc = request.getContextPath() + "/point/pointManagement?userId=" + userId;
			
			//세션 로그인 정보 갱신
			User updateUser = new UserService().selectOne(userId);
			memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
			
			if(updateUser.getUserId().equals(memberLoggedIn.getUserId())) {
				session.setAttribute("memberLoggedIn", updateUser);
			}
		}
		else {
			msg = "포인트 환전에 실패하였습니다.";
			loc = request.getContextPath() + "/point/pointManagement?userId=" + userId;
		}

		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

}
