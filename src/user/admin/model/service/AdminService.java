
package user.admin.model.service;
import static common.JDBCTemplate.*;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.admin.model.dao.AdminDAO;
import user.tutor.model.vo.TutorResume;
import user.vo.User;

public class AdminService {

	public static final String ADMIN_COMM_GRADE = "AD";
	
	private AdminDAO adminDAO = new AdminDAO();
	
//	(예빈)
	public User selectOne(String userId) {
		Connection conn = getConnection();
		User user = adminDAO.selectOne(conn, userId);
		close(conn);
		return user; 
	}

	public int userCount(String tbl, String col, String target) {
		Connection conn = getConnection();
		int result = adminDAO.userCount(conn, tbl, col, target);
//		System.out.println("countUser-result@service = "+result);
		close(conn);
		return result;
	}

	public int getRevenue(String io) {
		Connection conn = getConnection();
		int result = adminDAO.getRevenue(conn, io);
//		System.out.println("getRevenue-result@service = "+result);
		close(conn);
		return result;
	}

	public int getPCnt(String io) {
		Connection conn = getConnection();
		int result = adminDAO.getPCnt(conn, io);
//		System.out.println("pCnt@service = "+result);
		close(conn);
		return result;
	}


	public List<Integer> getRW() {
		Connection conn = getConnection();
		List<Integer> rw = adminDAO.getRW(conn);
		close(conn);
		return rw;
	}
	
	public List<Integer> getMW(String grade) {
		Connection conn = getConnection();
		List<Integer> mw = adminDAO.getMW(conn, grade);
		close(conn);
		return mw;
	}

	
//	(준혁)
	public List<User> selectAllUsers() {

		Connection conn = getConnection();
		
		List<User> uList = adminDAO.selectAllUsers(conn);
		
		close(conn);
		
		return uList;
	}

	public List<User> selectProcessUsers() {

		Connection conn = getConnection();
		
		List<User> tList = adminDAO.selectProcessUsers(conn);
		
		close(conn);
		
		return tList;
	}
	
	public int updateResume(String tutorId, String resumeYNP) {
		Connection conn = getConnection();
		int result = new AdminDAO().updateResume(conn, tutorId, resumeYNP);
		if(result > 0) {
			commit(conn);
			
		}else {
			rollback(conn);
		}
		return result;
	}

	public Map<String, Object> getUserResume(String userId) {
		Connection conn = getConnection();		
		Map<String, Object> map = new HashMap<>();		
		User u = adminDAO.selectOneUser(conn, userId);		
		TutorResume tr = adminDAO.selectTutorResume(conn, userId);		
		close(conn);		
		map.put("user", u);
		map.put("tr", tr);
		
		return map;
	}
	


}
