package user.member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import board.model.vo.Board;
import user.tutor.model.vo.Review;
import user.vo.Message;
import user.vo.User;

public class UserDAO {
	
	Properties prop = new Properties();
	
	public UserDAO() {
		//java.util.Properties
		String fileName = "/sql/member/user/user-query.properties";
		String memberSQL = UserDAO.class.getResource(fileName).getPath();
		
		try {
			prop.load(new FileReader(memberSQL));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public User selectOne(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectOne");
		ResultSet rset = null;
		User user = null;
		try {
			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			
			
			if(rset.next()) {
				
				user = new User();
				user.setUserId(rset.getString("user_id"));
				user.setCommGrade(rset.getString("comm_grade"));
				user.setPassword(rset.getString("password"));
				user.setUserName(rset.getString("user_name"));
				user.setGender(rset.getString("gender"));
				user.setBirthday(rset.getDate("birthday"));
				user.setEmail(rset.getString("email"));
				user.setAddress(rset.getString("address"));
				user.setPhone(rset.getString("phone"));
				user.setEnrollDate(rset.getDate("enroll_date"));
				user.setPointSum(rset.getInt("point_sum"));
				user.setResumeYNP(rset.getString("resume_ynp"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
//		System.out.println(member);
		return user;
	}

	public int updateUser(Connection conn, User u) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUserName());
			pstmt.setString(2, u.getGender());
			pstmt.setString(3, u.getPhone());
			pstmt.setDate(4, u.getBirthday());
			pstmt.setString(5, u.getEmail());
			pstmt.setString(6, u.getAddress());
			pstmt.setString(7, u.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteUser(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updatePassword(Connection conn, String userId, String rndPwd) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updatePassword");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rndPwd);
			pstmt.setString(2, userId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}
	
	public int userJoin(Connection conn, User users) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("userJoin"); 
		
		try {
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//쿼리문미완성
			pstmt.setString(1, users.getUserId() );
			pstmt.setString(2, users.getPassword());
			pstmt.setString(3, users.getUserName());
			pstmt.setString(4, users.getGender());
			pstmt.setDate(5, users.getBirthday());
			pstmt.setString(6, users.getEmail());
			pstmt.setString(7, users.getAddress());
			pstmt.setString(8, users.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		System.out.println("users@dao="+users);
		return result;
		
	}

	public int userIdCheck(Connection conn, String userId) {
		System.out.println(userId);
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkId");
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
		
			if(rset.next()) {
				return 1;
		
			}
			else {
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
//		System.out.println("member@dao = " + member);
		
		return -1;
	}

	public int userEmailCheck(Connection conn, String userEmail) {
		System.out.println(userEmail);
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkEmail");
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userEmail);
			
			rset = pstmt.executeQuery();
		
			if(rset.next()) {
				return 1;
		
			}
			else {
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
//		System.out.println("member@dao = " + member);
		
		return -1;
	}

	public int userPhoneCheck(Connection conn, String userPhone) {
		System.out.println(userPhone);
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkPhone");
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPhone);
			
			rset = pstmt.executeQuery();
		
			if(rset.next()) {
				return 1;
		
			}
			else {
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
//		System.out.println("member@dao = " + member);
		
		return -1;
	}

	public Map<String, Object> getMessage(Connection conn, int numPerPage, int cPage, String userId) {
		Map<String, Object> map = new HashMap<>();
		List<Message> mlist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getMessage");
		Message m = null;
		
		System.out.println("userId@dao = " + userId);
		
		int totalContents = 0;
		
		int startRnum = (cPage-1)*numPerPage+1;
		int endRnum = cPage*numPerPage;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRnum);
			pstmt.setInt(3, endRnum);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				m = new Message();
				m.setMsgContent(rset.getString("msg_content"));
				m.setMsgDate(rset.getDate("msg_date"));
				m.setMsgNo(rset.getInt("msg_no"));
				m.setMsgReadYn(rset.getString("msg_read_yn"));
				m.setMsgTitle(rset.getString("msg_title"));
				m.setUserId(rset.getString("user_id"));
				totalContents = rset.getInt("cnt");
				mlist.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		map.put("mlist", mlist);
		map.put("totalContents", totalContents);
		
		System.out.println("map@dao = " + map);
		
		return map;
	}

	public int readMessage(Connection conn, int msgNo) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("readMessage");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, msgNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public int writeReview(Connection conn, Review review) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("writeReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, review.getUserId());
			pstmt.setString(2, review.getTutorId());
			pstmt.setInt(3, review.getClassNo());
			pstmt.setString(4, review.getReviewContent());
			pstmt.setInt(5, review.getReviewScore());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public Map<String, Object> selectMyClassReturnMap(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMyClass");
		Map<String, Object> map = null;
		List<Board> list = null;
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			list = new ArrayList<>();
			map = new HashMap<>();
			while(rset.next()) {
				Board userBoard = new Board();
				userBoard.setClassNo(rset.getInt("class_no"));
				userBoard.setTutorId(rset.getString("tutor_id"));
				userBoard.setPrice(rset.getInt("price"));
				userBoard.setTitle(rset.getString("title"));
				userBoard.setClassContent(rset.getString("class_content"));
				userBoard.setLastApplyDate(rset.getDate("last_apply_date"));
				userBoard.setCapacity(rset.getInt("capacity"));
				userBoard.setClassAddress(rset.getString("class_address"));
				userBoard.setApplyExpireYn(rset.getString("apply_expire_yn"));
				userBoard.setClassLocation(rset.getString("class_location"));
				userBoard.setStartTime(rset.getInt("start_time"));
				userBoard.setEndTime(rset.getInt("end_time"));
				userBoard.setClassDate(rset.getDate("class_date"));
				userBoard.setClassEndYn(rset.getString("class_end_yn"));
				userBoard.setCategory(rset.getString("category"));
				userBoard.setTutorName(rset.getString("user_name"));
				
				list.add(userBoard);
			}
			map.put("list", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return map;
	}

	public int countReview(Connection conn, String userId, String tutorId, int classNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countReview");
		int cnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, tutorId);
			pstmt.setInt(3, classNo);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				cnt = rset.getInt("cnt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("cnt@DAO = " + cnt);
		return cnt;
	}

}
