package user.tutor.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.CKKFileRenamePolicy;
import user.member.model.service.UserService;
import user.tutor.model.service.TutorService;
import user.tutor.model.vo.TutorResume;
import user.vo.User;

/**
 * 튜터 이력서 제출용 서블릿
 */
@WebServlet("/tutor/submitResume")
public class TutorsubmitResumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TutorService tutorService = new TutorService();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("memberLoggedIn");
		
		
		String saveDirectory = getServletContext().getRealPath("/upload/tutor");
		int maxPostSize = 10 * 1024 * 1024;
		String encoding = "utf-8";
		FileRenamePolicy policy = new CKKFileRenamePolicy();
		//멀티파트리쿼스트 객체 생성
		MultipartRequest multipartRequest  = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		//사용자 입력값 처리 -> request => multipartrequest로 변환
		String tutorId = multipartRequest.getParameter("userId");
		String awardRecord = multipartRequest.getParameter("awardRecored");
		String career = multipartRequest.getParameter("career");
		String profileOrg = multipartRequest.getOriginalFileName("profile");
		String profileRen = multipartRequest.getFilesystemName("profile");
		String certN1 = multipartRequest.getParameter("certName");
		String certC1 = multipartRequest.getParameter("certCode");
		String certN2 = multipartRequest.getParameter("certName2");
		String certC2 = multipartRequest.getParameter("certCode2");
		String certN3 = multipartRequest.getParameter("certName3");
		String certC3 = multipartRequest.getParameter("certCode3");
		
		TutorResume tuResume = new TutorResume(tutorId, awardRecord, career, profileOrg, profileRen, certN1, certC1, certN2, certC2, certN3, certC3);
		//DB단 처리 -> 그 전에 테이블에 객체가 존재한다면 update메소드 실행 | 
		TutorResume checkInto = new TutorService().selectTutorResume(tutorId); 
		
		System.out.println("result@servlet = " +checkInto);
		
		int result = 0;
		// 존재하지 않는다면 insert메소드 실행
		if("N".equals(user.getResumeYNP()) && checkInto == null) {
			//결과값이 > 0 이상이라면, YNP컬럼을 P로 없데이트 해주는 메소드 실행 후 뷰 단처리 -> service에서 하나의 connection처리
			result = tutorService.writeResumeWithSubmit(tuResume);
			String msg = "";
			String loc = request.getContextPath();
			if(result > 0) {
				msg = "이력서 제출이 완료되었습니다.";
				
				//세션 정보 갱신
				String userId = user.getUserId();
				User userInfo = new UserService().selectOne(userId);
				session.setAttribute("memberLoggedIn", userInfo);
			}
			else {
				msg = "이력서 제출에 실패하였습니다. 확인 후 다시 제출하여주세요.";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			
		}else if (checkInto != null && "P".equals(user.getResumeYNP())) {
			String msg = "이미 제출하신 이력서가 있습니다. 확인 후 다시 이용해주세요.";
			String loc = request.getContextPath();	
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
		else if (checkInto != null &&  "Y".equals(user.getResumeYNP())) {
			result = tutorService.updateResumeWithSubmit(tuResume);
			String msg = "";
			String loc = request.getContextPath();	
			if(result > 0) {
				msg = "수정된 이력서가 제출되었습니다. 이력서 재심사에는 3~5일이 소요됩니다.";
			}
			else {
				msg = "이력서 제출에 실패하였습니다, 확인 후 다시 이용하여주세요.";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
		
		else {
			result = tutorService.updateResumeWithSubmit(tuResume);
			String msg ="";
			String loc = request.getContextPath();
			if(result > 0) {
				msg = "이력서 제출이 완료되었습니다.";
				String userId = user.getUserId();
				User userInfo = new UserService().selectOne(userId);
				session.setAttribute("memberLoggedIn", userInfo);
			}
			else {
				msg = "이력서 제출에 실패하였습니다. 확인 후 다시 제출하여 주세요.";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

