package user.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.admin.model.service.AdminService;

/**
 * 관리자 이력서 승인 서블릿
 */
@WebServlet("/admin/filterResume")
public class AdminResumeFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String resumeYNP = request.getParameter("resumeYNP");
		String tutorId = request.getParameter("tutorId");
		System.out.println(tutorId);
		System.out.println(resumeYNP);
		
		int result = new AdminService().updateResume(tutorId, resumeYNP);
		
		String msg = "";
		String loc = "";
		if(result > 0) {
			msg = "이력서 등급 처리에 성공하였습니다.";
			loc = request.getContextPath();  //이 부분에 회원 목록으로 돌아가게 하면 될듯
		}
		else {
			msg = "이력서 등급 처리에 실패하였습니다.";
			loc = request.getContextPath() + "/tutor/tutorResumeView?userId=" +tutorId;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
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
