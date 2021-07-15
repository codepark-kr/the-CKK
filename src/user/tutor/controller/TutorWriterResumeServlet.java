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
import user.tutor.model.service.TutorService;
import user.tutor.model.vo.TutorResume;
import user.vo.User;

/**
 *  튜터 이력서 작성 서블ㄹ
 */
@WebServlet("/tutor/writerResume")
public class TutorWriterResumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
		HttpSession session = request.getSession();
		User memberLoggedIn = (User)session.getAttribute("memberLoggedIn");
		
		String tutorId = memberLoggedIn.getUserId();
		TutorResume tr = new TutorService().selectTutorResume(tutorId);
	
		
		String view = "/WEB-INF/views/member/tutor/tutorResume.jsp";
		request.setAttribute("tutorResume", tr);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String saveDirectory = getServletContext().getRealPath("/upload/tutor"); 
		//10MB로 업로드할 파일용량 제한:10* 1kb*1kb 
		int maxPostSize = 10 * 1024 * 1024;
		// 업로드는 서버에 파일명은 DB에 저장
		//encoding
		String encoding = "utf-8";

//		FileRenamePolicy policy = new MvcFileRenamePolicy();
		FileRenamePolicy policy = new CKKFileRenamePolicy();

		MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);

		
		String tutorId = multipartRequest.getParameter("userId");
		String awardRecord = multipartRequest.getParameter("awardRecord");
		String career = multipartRequest.getParameter("career");
		String profileOrg = multipartRequest.getOriginalFileName("profile");
		String profileRen = multipartRequest.getFilesystemName("profile");
		String certName = multipartRequest.getParameter("certName");
		String certCode = multipartRequest.getParameter("certCode");
		String certName2 = multipartRequest.getParameter("certName2");
		String certCode2 = multipartRequest.getParameter("certCode2");
		String certName3 = multipartRequest.getParameter("certName3");
		String certCode3 = multipartRequest.getParameter("certCode3");
	
		//사용자가 입력한 이력서 객체 생성
		TutorResume tutorResume = new TutorResume(tutorId, awardRecord, 
												  career, profileOrg, profileRen, 
												  certName, certCode, 
												  certName2, certCode2, 
												  certName3, certCode3);
		
		//기존에 저장된 이력서 여부 검사할 객체 생성
		TutorResume tr = new TutorService().selectTutorResume(tutorId);
		int result =  0;
		String msg ="";
		String loc = "";
		if(tr !=  null ) {
			result = new TutorService().updateResume(tutorResume, tutorId);
			if(result > 0) {
				msg = "이력서 수정에 성공하였습니다.";
			}else {
				msg = "이력서 수정에 실패하였습니다. 다시 시도하여주세요.";			
			}
			
		}else {
			result = new TutorService().writeResume(tutorResume, profileOrg);
			if(result > 0) {
				msg = "이력서를 저장하였습니다.";
			}else {
				msg = "이력서 저장에 실패하였습니다. 다시 시도하여주세요.";			
			}
		}
		System.out.println("?????"+result);
		//3. view단 처리
		String view = "/WEB-INF/views/common/msg.jsp";
		System.out.println("=="+ tutorResume);
		loc = request.getContextPath() + "/tutor/writerResume?tutorId="+tutorId;
		System.out.println(msg);
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("tutorResume", tr);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

}
