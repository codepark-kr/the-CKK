package board.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int classNo = Integer.parseInt(request.getParameter("classNo"));

		Map<String, Object> map = new BoardService().deleteBoard(classNo);
		
		int result = (Integer)map.get("result");
		// 이미지 삭제
		Board b = (Board)map.get("board");
		
		String saveDirectory = getServletContext().getRealPath("/upload/food");

		File oldFile1 = new File(saveDirectory, b.getClassPic1Ren());
		boolean del1 = oldFile1.delete();
		System.out.println(oldFile1 + (del1 ? " 삭제 성공!" : " 삭제 실패!"));
		
		if(b.getClassPic2Ren() != null && !"".equals(b.getClassPic2Ren())) {
			File oldFile2 = new File(saveDirectory, b.getClassPic2Ren());
			boolean del2 = oldFile2.delete();
			System.out.println(oldFile2 + (del2 ? " 삭제 성공!" : " 삭제 실패!"));
		}

		if(b.getClassPic3Ren() != null && !"".equals(b.getClassPic3Ren())) {
			File oldFile3 = new File(saveDirectory, b.getClassPic3Ren());
			boolean del3 = oldFile3.delete();
			System.out.println(oldFile3 + (del3 ? " 삭제 성공!" : " 삭제 실패!"));
		}
		
		String msg = "";
		String loc = "";
		String script = "";
		if(result > 0) {
			msg = "클래스가 정상적으로 삭제 되었습니다.";
			loc = request.getContextPath() + "/board/list";
		}
		else {
			msg = "클래스 삭제가 실패하였습니다.";
			script = "history.go(-1);";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("script", script);
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
