package user.schedule.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import board.model.vo.BoardWithSch;
import user.schedule.model.service.ScheduleService;

/**
 * Servlet implementation class CoworkScheduleServlet
 */
@WebServlet("/member/coworkschedule")
public class CoworkScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoworkScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		System.out.println("classNo =" + classNo);
		
		List<BoardWithSch> list = new ScheduleService().selectCowork(classNo);


		
		
		System.out.println(list);
//		System.out.println("checkDate =" + checkDate);
//
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		System.out.println(jsonStr);
		
		//응답에 쓰기
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().append(jsonStr);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
