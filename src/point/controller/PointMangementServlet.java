package point.controller;

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
import user.vo.User;

/**
 * Servlet implementation class PointMangementServlet
 */
@WebServlet("/point/pointManagement")
public class PointMangementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PointMangementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
		
		//사용자 수수료 % 받아와 세션 등록
		String userId = memberLoggedIn.getUserId();
		User commission = new PointService().selectCommGradeOne(userId);
		session.setAttribute("commission", commission);
		
		System.out.println("gardeee@servlet = "+ commission);
		
		
		List<Point> pointView = new PointService().viewPoint(userId);
		List<Point> pointAll = new PointService().pointAll();
//		System.out.println("===" + pointView);
//		System.out.println("===="+ pointView.size());
		
		String view = "/WEB-INF/views/mypage/point/pointManagement.jsp";
		request.setAttribute("pointView", pointView);
		request.setAttribute("pointAll", pointAll);
		request.getRequestDispatcher(view).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
