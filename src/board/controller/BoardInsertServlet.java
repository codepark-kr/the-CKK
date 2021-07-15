package board.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.CKKFileRenamePolicy;

/**
 * Servlet implementation class BoardInsertServlet
 */
@WebServlet("/board/insert")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/board/boardInsert.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String saveDirectory = getServletContext().getRealPath("/upload/food");
		int maxPostSize = 10 * 1024 * 1024;
		String encoding = "utf-8";
		
		FileRenamePolicy policy = new CKKFileRenamePolicy();
		
		MultipartRequest mpReq = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		Board b = new Board();
		b.setTitle(mpReq.getParameter("title"));
		b.setCategory(mpReq.getParameter("category"));
		b.setClassLocation(mpReq.getParameter("classLocation"));
		b.setClassAddress(mpReq.getParameter("classAddress"));
		b.setLastApplyDate(Date.valueOf(mpReq.getParameter("lastApplyDate")));
		b.setCapacity(Integer.parseInt(mpReq.getParameter("capacity")));
		b.setPrice(Integer.parseInt(mpReq.getParameter("price")));
		b.setClassDate(Date.valueOf(mpReq.getParameter("classDate")));
		b.setStartTime(Integer.parseInt(mpReq.getParameter("startTime")));
		b.setEndTime(Integer.parseInt(mpReq.getParameter("endTime")));
		b.setClassContent(mpReq.getParameter("classContent"));
		b.setTutorId(mpReq.getParameter("tutorId"));
		b.setClassPic1Org(mpReq.getOriginalFileName("classPic1Org"));
		b.setClassPic1Ren(mpReq.getFilesystemName("classPic1Org"));
		b.setClassPic2Org(mpReq.getOriginalFileName("classPic2Org"));
		b.setClassPic2Ren(mpReq.getFilesystemName("classPic2Org"));
		b.setClassPic3Org(mpReq.getOriginalFileName("classPic3Org"));
		b.setClassPic3Ren(mpReq.getFilesystemName("classPic3Org"));
		
		System.out.println(b);
		int result = new BoardService().insertBoard(b);
		
		String msg = "";
		String loc = "";
		String script = "";
		if(result > 0) {
			msg = "수업이 정상 등록되었습니다.";
			loc = request.getContextPath() + "/board/view?classNo=" + b.getClassNo();
		}
		else {
			msg = "수업 등록에 실패하였습니다.";
			script = "history.go(-1);";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("script", script);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		
	}

}
