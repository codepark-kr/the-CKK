package user.schedule.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.schedule.model.service.ScheduleService;

/**
 * Servlet implementation class ScheduleServlet
 */
@WebServlet("/member/schedule")
public class UserScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		//값을 넘겨도 servlet자체에 연결이 안됨
		Map<String, Object> map = null;
	
		map = new ScheduleService().selectSchedule(userId);
//
//		System.out.println("userId@Schedule" + userId);
//		System.out.println("map" + map);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/WEB-INF/views/mypage/schedule/schedule.jsp")
		   .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
