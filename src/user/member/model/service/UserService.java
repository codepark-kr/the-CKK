package user.member.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import board.model.vo.Board;
import user.member.model.dao.UserDAO;
import user.tutor.model.vo.Review;
import user.vo.User;

public class UserService {
	
	UserDAO usersDAO = new UserDAO();
	public static final String RESUME_Y = "Y";
	public static final String RESUME_N = "N";
	public static final String RESUME_P = "P";
	public static final String USER_GRADE = "US";
	public static final String USER_TUTOR = "T4";
	public static final String USER_ADMIN = "AD";

	public User selectOne(String userId) {
		Connection conn = getConnection(); 
		User user = usersDAO.selectOne(conn, userId);
		close(conn);
//		System.out.println("member@service = " +member);
		return user;
	}

	public int updateUser(User u) {
		Connection conn = getConnection();
		int result = usersDAO.updateUser(conn, u);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int deleteUser(String userId) {
		Connection conn = getConnection();
		int result = usersDAO.deleteUser(conn, userId);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int updatePassword(String userId, String rndPwd) {
		Connection conn = getConnection();
		int result = usersDAO.updatePassword(conn, userId, rndPwd);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int userJoin(User users) {
		Connection conn = getConnection();
		int result = usersDAO.userJoin(conn, users);
		if(result>0)
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		
		System.out.println("user@service="+users);
		return result;
	}


	public int userIdCheck(String userId) {
		
		Connection conn = getConnection();
	
		System.out.println(userId);
		int checkId = usersDAO.userIdCheck(conn, userId);
		System.out.println(checkId);
		close(conn);
	
		
		
		return checkId; 
	}


	public int userEmailCheck(String userEmail) {
		Connection conn = getConnection();
		
		System.out.println(userEmail);
		int checkEmail = usersDAO.userEmailCheck(conn, userEmail);
		System.out.println(checkEmail);
		close(conn);
	
		return checkEmail; 
	}


	public int userPhoneCheck(String userPhone) {
		Connection conn = getConnection();
		System.out.println(userPhone);
		int checkPhone = usersDAO.userPhoneCheck(conn, userPhone);
		System.out.println(checkPhone);
		close(conn);
	
		return checkPhone; 
	}

	public Map<String, Object> getMessage(int numPerPage, int cPage, String userId) {

		Connection conn = getConnection();
		
		Map<String, Object> map = usersDAO.getMessage(conn, numPerPage, cPage, userId);
		
		close(conn);
		
		return map;
	}

	public int readMessage(int msgNo) {

		Connection conn = getConnection();
		
		int result = usersDAO.readMessage(conn, msgNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	public int writeReview(Review review) {
		Connection conn = getConnection();
		int result = usersDAO.writeReview(conn, review);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}
	
	public Map<String, Object> selectMyClassReturnMap(String userId) {
		Connection conn = getConnection();
		Map<String, Object> map = usersDAO.selectMyClassReturnMap(conn, userId);
		
		List<Board> list = (List<Board>)map.get("list");
		
		for(Board b : list) {
			int cnt = usersDAO.countReview(conn, userId, b.getTutorId(), b.getClassNo());
			if(cnt > 0) {
				map.put(String.valueOf(b.getClassNo()), true);
			}else {
				map.put(String.valueOf(b.getClassNo()), false);
			}
		}
		System.out.println("map@service = " + map);
		close(conn);
		return map;
	}
}
