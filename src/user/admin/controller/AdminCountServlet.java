package user.admin.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.admin.model.service.AdminService;


@WebServlet("/admin/analysis")
public class AdminCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminCountServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		countAll = select count(*) from ? where ? like ?

		List<Integer> list = new ArrayList<>();
		
//		무의미한 조건문을 포함시켜 ?의 갯수를 맞춤으로 하나의 쿼리문/메소드로 해결한다.
//		... resume_ynp like ('%%'); 와 같이 선언하게 되면 resume_ynp의 값이 무엇이던 간에 모든 수를 세므로.
		
		int allUsers = new AdminService().userCount("all_user", "user_id", "");
		int quitUsers = new AdminService().userCount("quit_user", "user_id", "");
		int tutors = new AdminService().userCount("all_user", "comm_grade", "T");
		int users = allUsers - tutors - 1;
		
//		System.out.println("testcode@servlet = \n"+allUsers+"\n"+quitUsers+"\n"+tutors+"\n"+users);
		
		list.add(allUsers);
		list.add(quitUsers);
		list.add(tutors);
		list.add(users);
		
//		System.out.println("list@servlet = "+list);
		
		List<String> pAmount = new ArrayList<>();
		
		
		int revenueI = new AdminService().getRevenue("I");
		int revenueO = new AdminService().getRevenue("O");
		int revenueAll = revenueI + revenueO;
		
		DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###원");
		String revA = formatter.format(revenueAll);		
		String revI = formatter.format(revenueI);		
		String revO = formatter.format(revenueO);		

		pAmount.add(revA);
		pAmount.add(revI);
		pAmount.add(revO);
		
//		System.out.println(pAmount.toString());
						
		List<Integer> pCnt = new ArrayList<>();
		
		int pCntI = new AdminService().getPCnt("I");
		int pCntO = new AdminService().getPCnt("O");
		int pCntAll = pCntI + pCntO;
		
		pCnt.add(pCntAll);
		pCnt.add(pCntI);
		pCnt.add(pCntO);
		
//		System.out.println("pCnt@servlet = "+pCnt.toString());	
		
/*
 * (예빈)여기서부터 차트에 대입될 7일간 값 구하기
 * 기존에 존재하는 쿼리에 조건절만 대입해 주면 되고, ?는 2개를 추가할 것. (col, int)
 * int의 값은 반복문으로 처리할 것.
 * 
 * 1. 매출의 7일 간 통계 sum
 * (PL.point_amount) ... where point_io_date > (SYSDATE - 8 ~ - 1) 
 * 단, 등급별 연산 switch문 대입
 * 
 * 2. 회원 가입 수의 7일 간 통계 
 * where enroll_date > (SYSDATE - 8 ~ - 1)
 * 
 */
		
	List<Integer> rWeekly = new ArrayList<>();
	List<Integer> mWeeklyT = new ArrayList<>();
	List<Integer> mWeeklyU = new ArrayList<>();

// rWeekly = [0]6일전 ~ 오늘[6]치 일일 매출 데이터를 리스트로 반환.
// mWeekly = [0]6일전 ~ 오늘[6]치 튜터의 회원 가입 데이터를 리스트로 반환.
	
	rWeekly = new AdminService().getRW();
	System.out.println("rWeekly@servlet = "+rWeekly.toString());
	mWeeklyT = new AdminService().getMW("T");
	mWeeklyU = new AdminService().getMW("US");
	System.out.println("mWeeklyU@servlet = "+mWeeklyU.toString());
	System.out.println("mWeeklyT@servlet = "+mWeeklyT.toString());

				
		
		request.setAttribute("list", list); // 가입 유저/탈퇴 유저/튜터 유저/일반 유저의 총합 데이터
		request.setAttribute("pAmount", pAmount); // 포인트로 인한 수익 총합 데이터
		request.setAttribute("pCnt", pCnt); // 포인트 입출력 횟수의 총합 데이터
		request.setAttribute("rWeekly", rWeekly); //주간 수익 데이터 (6일 전 ~ 당일)
		request.setAttribute("mWeeklyT", mWeeklyT); // 주간 신규 승급 튜터 유저 (6일 전 ~ 당일)
		request.setAttribute("mWeeklyU", mWeeklyU); // 주간 신규 가입 일반 유저 (6일 전 ~ 당일)
		
		request.getRequestDispatcher("/WEB-INF/views/member/admin/Analysis.jsp")
		   .forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
