package center.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import center.model.service.CenterService;
import center.model.vo.CenterComment;

/**
 * Servlet implementation class CenterCommentInsertServlet
 */
@WebServlet("/center/comment/insert")
public class CenterCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CenterCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		int csNo = Integer.parseInt(request.getParameter("csNo"));
		String adminId = request.getParameter("adminId");
		String csaContent = request.getParameter("csaContent");
		
		CenterComment centerComment = new CenterComment(csNo, adminId, csaContent, null);    
	
		int result = new CenterService().insertCenterComment(centerComment);
		
		String view = "/WEB-INF/views/common/msg.jsp";
		String msg = "";
		String loc = request.getContextPath() + "/center/view?csNo="+ csNo;
		
		if(result > 0) {
			msg ="댓글이 등록되셨습니다";
		}
		else {
			msg ="댓글 등록에 실패했습니다";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		RequestDispatcher reqDispatcher = request.getRequestDispatcher(view);
		reqDispatcher.forward(request, response);
		
		}catch(Exception e) {
			
			e.printStackTrace();
			throw e;
		}
	}	
	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
