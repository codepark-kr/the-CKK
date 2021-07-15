package center.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import center.model.service.CenterService;
import center.model.vo.Center;
import center.model.vo.CenterComment;
import common.Utils;


/**
 * Servlet implementation class CenterViewServlet
 */
@WebServlet("/center/view")
public class CenterViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CenterViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getParameter("csNo"));
		int csNo = Integer.parseInt(request.getParameter("csNo"));
		

			
		//게시글 1개 가져오기
		CenterService centerService = new CenterService();
		Center center = centerService.selectOne(csNo);	
		List<CenterComment> commentList = centerService.selectCommentList(csNo);
	
		String view = "/WEB-INF/views/center/CenterView.jsp";
		if(center==null) {
			request.setAttribute("msg", "조회한 문의사항이 존재하지 않습니다");
			request.setAttribute("loc", "/center/list");
			view = "/WEB-INF/views/common/msg.jsp";
		}
		else {
			//???제목부분
			String csTitle = center.getCsTitle();
			csTitle = Utils.getSecureString(csTitle);
			center.setCsTitle(csTitle);
			
			//???내용부분
			String csContent = center.getCsContent();
			csContent = Utils.getSecureString(csContent);
			csContent = Utils.getStringWithBr(csContent);
			center.setCsContent(csContent);
		}
		request.setAttribute("center", center);
		request.setAttribute("commentList", commentList);
		RequestDispatcher reqDispatcher = request.getRequestDispatcher(view);
		reqDispatcher.forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
