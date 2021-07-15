package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;

/**
 * Servlet implementation class BoardCancelServlet
 */
@WebServlet("/board/cancel")
public class BoardCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCancelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int classNo = Integer.parseInt(request.getParameter("classNo"));
		String userId = request.getParameter("userId");
		
		int result = new BoardService().cancelClass(classNo, userId);
		
		String msg = "";
		
		if(result > 0) msg = "정상적으로 수강 취소 되었습니다.";
		else msg = "수강 신청 취소 실패하였습니다.";
		
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
