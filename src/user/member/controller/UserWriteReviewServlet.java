package user.member.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.member.model.service.UserService;
import user.tutor.model.vo.Review;
import user.vo.User;

/**
 * 사용자 리뷰 작성 서블릿
 */
@WebServlet("/user/writeReview")
public class UserWriteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그인되어있는 계정 가져오기
		HttpSession session = request.getSession();
		User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
		String userId = memberLoggedIn.getUserId();
		
		
//		List<Board> list = new UserService().selectMyClass(userId);
		
		//서버단 사용자 수강한 클래스 정보 / Review 작성 여부 map으로 가져오기
			// 수강 클래스 정보 ( "list" : List<Board> ) 로 대입
			// review 작성여부는 (class_no : return int) 로 대입
		Map<String, Object> map = new UserService().selectMyClassReturnMap(userId);
		
//		System.out.println("list@servlet = " + list);
		
		request.setAttribute("userId", userId);
		request.setAttribute("map", map);
//		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/mypage/review/writeReview.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String tutorId = request.getParameter("tutorId");
		int classNo = Integer.parseInt(request.getParameter("classNo")); 
		String reviewContent = request.getParameter("reviewContent");
		int reviewScore = Integer.parseInt(request.getParameter("reviewScore"));
		
		Review review = new Review(userId, tutorId, classNo, reviewContent, reviewScore, null);
		
		int result = new UserService().writeReview(review);
		
		String msg ="";
		String loc = request.getContextPath() + "/user/writeReview?userId=" +userId;
		if(result > 0) {
			msg = "리뷰를 작성해주셔서 감사합니다!";
		}
		else {
			msg = "리뷰 작성에 실패하였습니다. 다시 시도해 주세요.";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	}

}
