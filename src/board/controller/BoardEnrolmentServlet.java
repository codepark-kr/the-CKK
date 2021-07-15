package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;

/**
 * Servlet implementation class BoardApplicationServlet
 */
@WebServlet("/board/enrolment")
public class BoardEnrolmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardEnrolmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int classNo = Integer.parseInt(request.getParameter("classNo"));
		String userId = request.getParameter("userId");
		
		int result = new BoardService().enrolClass(classNo, userId);
		
		String msg = "";
		
		if(result == -1) {
			msg = "수강인원 초과로 신청 실패하였습니다.";
		}
		else if(result > 0) {
			msg = "성공적으로 수강 신청 되셨습니다.";
		}
		else {
			msg = "수강 신청 실패하셨습니다.";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", request.getContextPath() + "/board/view?classNo=" + classNo);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
