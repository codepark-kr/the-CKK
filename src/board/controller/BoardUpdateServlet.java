package board.controller;

import java.io.File;
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
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		Board b = new BoardService().selectOneBoard(classNo);
		System.out.println("boardUpdate@servlet = " + b);
		request.setAttribute("board", b);
		request.getRequestDispatcher("/WEB-INF/views/board/boardUpdate.jsp").forward(request, response);
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
		
		int classNo = Integer.parseInt(mpReq.getParameter("classNo"));
		Board b = new BoardService().selectOneBoard(classNo);
		System.out.println("update@servlet = " + b);
		
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
		if(mpReq.getOriginalFileName("pic1-input") != null) {
			//변경됐으면 원래 파일 삭제
			File oldFile = new File(saveDirectory, b.getClassPic1Ren());
			boolean result = oldFile.delete();
//			System.out.println(oldFile + (result ? " 삭제 성공!" : " 삭제 실패!"));
			b.setClassPic1Org(mpReq.getOriginalFileName("pic1-input"));
			b.setClassPic1Ren(mpReq.getFilesystemName("pic1-input"));
		}
		
		if(mpReq.getParameter("del-pic2") != null 
		|| (b.getClassPic2Org() != null && mpReq.getOriginalFileName("pic2-input") != null)) {
			//삭제 체크박스가 널이 아니거나 원래 값이 있었는데 새로 입력이 왔다면 기존 파일 삭제 
			File oldFile = new File(saveDirectory, b.getClassPic2Ren());
			boolean result = oldFile.delete();
			System.out.println(oldFile + (result ? " 삭제 성공!" : " 삭제 실패!"));
			b.setClassPic2Org(mpReq.getOriginalFileName("pic2-input"));
			b.setClassPic2Ren(mpReq.getFilesystemName("pic2-input"));
		}
		else if(b.getClassPic2Org() == null) {
			b.setClassPic2Org(mpReq.getOriginalFileName("pic2-input"));
			b.setClassPic2Ren(mpReq.getFilesystemName("pic2-input"));
		}
		
		if(mpReq.getParameter("del-pic3") != null 
				|| (b.getClassPic3Org() != null && mpReq.getOriginalFileName("pic3-input") != null)) {
			//삭제 체크박스가 널이 아니거나 원래 값이 있었는데 새로 입력이 왔다면 기존 파일 삭제 
			File oldFile = new File(saveDirectory, b.getClassPic3Ren());
			boolean result = oldFile.delete();
			System.out.println(oldFile + (result ? " 삭제 성공!" : " 삭제 실패!"));
			b.setClassPic3Org(mpReq.getOriginalFileName("pic3-input"));
			b.setClassPic3Ren(mpReq.getFilesystemName("pic3-input"));
		}
		else if(b.getClassPic3Org() == null) {
			b.setClassPic3Org(mpReq.getOriginalFileName("pic3-input"));
			b.setClassPic3Ren(mpReq.getFilesystemName("pic3-input"));
		}
		
		System.out.println(b);
		int result = new BoardService().updateBoard(b);
		
		String msg = "";
		String loc = "";
		String script = "";
		if(result > 0) {
			msg = "수업이 정상 수정되었습니다.";
			loc = request.getContextPath() + "/board/view?classNo=" + b.getClassNo();
		}
		else {
			msg = "수업 수정에 실패하였습니다.";
			script = "history.go(-1);";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("script", script);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	
	}

}
