package center.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import center.model.service.CenterService;
import center.model.vo.Center;
import common.CKKFileRenamePolicy;

/**
 * Servlet implementation class CenterInsertServlet
 */
@WebServlet("/center/insert")
public class CenterInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CenterInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/center/CenterInsert.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String saveDirectory = getServletContext().getRealPath("/upload/cscenter");//??경로
		System.out.println(saveDirectory);
		int maxPostSize = 10*1024*1024;
		String encoding =  "utf-8";
		
		FileRenamePolicy policy = new CKKFileRenamePolicy();
		
		MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		String csTitle = multipartRequest.getParameter("csTitle");
		String userId = multipartRequest.getParameter("csWriter");
		String csContent = multipartRequest.getParameter("csContent");
		
		String csFileOrg = multipartRequest.getOriginalFileName("upFile");
		String csFileRen = multipartRequest.getFilesystemName("upFile");
		String secretYn = multipartRequest.getParameter("secretYn");
		if(secretYn != null) secretYn = "Y";
		else secretYn = "N";
		
		Center center = new Center(0, userId, csTitle, csContent, csFileOrg, csFileRen, null, null, secretYn);
		System.out.println("============" + center);
		
		int result = new CenterService().insertCenter(center);
//		System.out.println("lastCsNo = " + center.getCsNo());
		int csNo = center.getCsNo();
		
		String msg="";
		String loc="";
		
		if(result>0) {
			msg="게시글이 등록되었습니다";
			loc=request.getContextPath() + "/center/view?csNo=" + csNo;
			//  http://localhost:9090/CKK   /center/view?csNo=     1
		}
		else {
			msg="게시글 등록실패됐습니다";
			loc=request.getContextPath() +"/center/list";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		
	}

}
