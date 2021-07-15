package user.admin.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.admin.model.service.AdminService;
import user.tutor.model.vo.TutorResume;
import user.vo.User;

/**
 * Servlet implementation class AdminCheckResumeServlet
 */
@WebServlet("/admin/check/resume")
public class AdminCheckResumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminCheckResumeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userId = request.getParameter("userId");
		
		Map<String, Object> map = new AdminService().getUserResume(userId);
		TutorResume tr = (TutorResume)map.get("tr");
		User user = (User)map.get("user");
		
		request.setAttribute("tutorResume", tr);
		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/views/member/tutor/tutorResumeView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
