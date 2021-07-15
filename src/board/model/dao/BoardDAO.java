package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import board.model.vo.Board;
import board.model.vo.Schedule;
import user.tutor.model.vo.Review;
import user.tutor.model.vo.TutorResume;
import user.vo.User;

public class BoardDAO {

	private Properties prop = new Properties();
	
	public BoardDAO() {
		String fileName = "/sql/board/board-query.properties";
		fileName = BoardDAO.class.getResource(fileName).getPath();
//		System.out.println("fileName@dao = " + fileName);
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> getBoardList(Connection conn, int numPerPage, int cPage, Map<String, Object> param) {

		Map<String, Object> map = new HashMap<>();
		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getBoardList1");
		String[] categoryArr = (String[])param.get("categoryArr");
		String[] locationArr = (String[])param.get("locationArr");
		String keywordType = (String)param.get("keywordType");
		String keyword = (String)param.get("keyword");	
		int lowPrice = (Integer)param.get("lowPrice");
		int highPrice = (Integer)param.get("highPrice");
		Date classDate = (Date)param.get("classDate");
		
		if(categoryArr != null && categoryArr.length != 0) {
			sql += " and category in(";
			for(int i = 0; i < categoryArr.length; i++) {
				if(i != 0) sql += ",";
				sql += "'" + categoryArr[i] + "'";
			}
			sql += ")";
		}
		
		if(locationArr != null && locationArr.length != 0) {
			sql += " and class_location in(";
			for(int i = 0; i < locationArr.length; i++) {
				if(i != 0) sql += ",";
				sql += "'" + locationArr[i] + "'";
			}
			sql += ")";
		}
		
		if(keyword != null && keyword != "") {
			sql += " and " + keywordType + " like '%" + keyword + "%'";
		}
		
		if(lowPrice != 0 && highPrice != 0) {
			sql += " and price between " + lowPrice + " and " + highPrice;
		}
		else if(lowPrice != 0 && highPrice == 0) {
			sql += " and price > " + lowPrice;
		}
		else if(lowPrice == 0 && highPrice != 0) {
			sql += " and price < " + highPrice;
		}
		
		if(classDate != null) {
			sql += " and class_date = to_date('"+ classDate +"','yyyy/MM/dd')";
		}
		
		sql += prop.getProperty("getBoardList2");
		
		System.out.println(sql);
		
		Board b = null;
		int totalContents = 0;
		
		int startRnum = (cPage-1)*numPerPage+1;
		int endRnum = cPage*numPerPage;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRnum);
			pstmt.setInt(2, endRnum);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				b = new Board();
				b.setApplyExpireYn(rset.getString("apply_expire_yn"));
				b.setCapacity(rset.getInt("capacity"));
				b.setClassAddress(rset.getString("class_address"));
				b.setClassContent(rset.getString("class_content"));
				b.setClassDate(rset.getDate("class_date"));
				b.setClassEndYn(rset.getString("class_end_yn"));
				b.setClassLocation(rset.getString("class_location"));
				b.setClassNo(rset.getInt("class_no"));
				b.setEndTime(rset.getInt("end_time"));
				b.setLastApplyDate(rset.getDate("last_apply_date"));
				b.setPrice(rset.getInt("price"));
				b.setStartTime(rset.getInt("start_time"));
				b.setTitle(rset.getString("title"));
				b.setTutorId(rset.getString("tutor_id"));
				b.setClassPic1Org(rset.getString("class_pic1_org"));
				b.setClassPic2Org(rset.getString("class_pic2_org"));
				b.setClassPic3Org(rset.getString("class_pic3_org"));
				b.setClassPic1Ren(rset.getString("class_pic1_ren"));
				b.setClassPic2Ren(rset.getString("class_pic2_ren"));
				b.setClassPic3Ren(rset.getString("class_pic3_ren"));
				b.setTutorName(rset.getString("tutor_name"));
				b.setCategory(rset.getString("category"));
				totalContents = rset.getInt("cnt");
				
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
//		System.out.println("list@dao = " + list);
//		System.out.println("totalContents = " + totalContents);
		
		map.put("list", list);
		map.put("totalContents", totalContents);
		
		return map;
		
	}

	public Board selectOneBoard(Connection conn, int classNo) {

		Board board = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOneBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				board = new Board();
				board.setApplyExpireYn(rset.getString("apply_expire_yn"));
				board.setCapacity(rset.getInt("capacity"));
				board.setCategory(rset.getString("category"));
				board.setClassAddress(rset.getString("class_address"));
				board.setClassContent(rset.getString("class_content"));
				board.setClassDate(rset.getDate("class_date"));
				board.setClassEndYn(rset.getString("class_end_yn"));
				board.setClassLocation(rset.getString("class_location"));
				board.setClassNo(rset.getInt("class_no"));
				board.setEndTime(rset.getInt("end_time"));
				board.setLastApplyDate(rset.getDate("last_apply_date"));
				board.setPrice(rset.getInt("price"));
				board.setStartTime(rset.getInt("start_time"));
				board.setTitle(rset.getString("title"));
				board.setTutorId(rset.getString("tutor_id"));
				board.setClassPic1Org(rset.getString("class_pic1_org"));
				board.setClassPic2Org(rset.getString("class_pic2_org"));
				board.setClassPic3Org(rset.getString("class_pic3_org"));
				board.setClassPic1Ren(rset.getString("class_pic1_ren"));
				board.setClassPic2Ren(rset.getString("class_pic2_ren"));
				board.setClassPic3Ren(rset.getString("class_pic3_ren"));
				board.setTutorName(rset.getString("tutor_name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return board;
	}

	public User selectOneUser(Connection conn, String tutorId) {

		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOneUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tutorId);
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
		
		return user;
	}

	public TutorResume selectOneTutorResume(Connection conn, String tutorId) {
		
		TutorResume tr = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOneTutorResume");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tutorId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				tr = new TutorResume();
				tr.setAwardRecord(rset.getString("award_record"));
				tr.setCareer(rset.getString("career"));
				tr.setCertC1(rset.getString("cert_c_1"));
				tr.setCertC2(rset.getString("cert_c_2"));
				tr.setCertC3(rset.getString("cert_c_3"));
				tr.setCertN1(rset.getString("cert_n_1"));
				tr.setCertN2(rset.getString("cert_n_2"));
				tr.setCertN3(rset.getString("cert_n_3"));
				tr.setProfileOrg(rset.getString("profile_org"));
				tr.setProfileRen(rset.getString("profile_ren"));
				tr.setTutorId(rset.getString("tutor_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tr;
	}

	public List<Review> getReview(Connection conn, String tutorId) {
	
		List<Review> reviewList = new ArrayList<>();
		Review r = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tutorId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				r = new Review();
				r.setClassNo(rset.getInt("class_no"));
				r.setReviewContent(rset.getString("review_content"));
				r.setReviewDate(rset.getDate("review_date"));
				r.setReviewScore(rset.getInt("review_score"));
				r.setTutorId(rset.getString("tutor_id"));
				r.setUserId(rset.getString("user_id"));
				reviewList.add(r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return reviewList;
	}

	public int insertBoard(Connection conn, Board b) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getTutorId());
			pstmt.setInt(2, b.getPrice());
			pstmt.setString(3, b.getTitle());
			pstmt.setString(4, b.getClassContent());
			pstmt.setDate(5, b.getLastApplyDate());
			pstmt.setInt(6, b.getCapacity());
			pstmt.setString(7, b.getClassAddress());
			pstmt.setString(8, b.getClassLocation());
			pstmt.setInt(9, b.getStartTime());
			pstmt.setInt(10, b.getEndTime());
			pstmt.setDate(11, b.getClassDate());
			pstmt.setString(12, b.getCategory());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int selectBoardLastSeq(Connection conn) {

		int lastSeq = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBoardLastSeq");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				lastSeq = rset.getInt("class_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return lastSeq;
	}

	public int insertImages(Connection conn, Board b) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertImages");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b.getClassNo());
			pstmt.setString(2, b.getClassPic1Org());
			pstmt.setString(3, b.getClassPic1Ren());
			pstmt.setString(4, b.getClassPic2Org());
			pstmt.setString(5, b.getClassPic2Ren());
			pstmt.setString(6, b.getClassPic3Org());
			pstmt.setString(7, b.getClassPic3Ren());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

//	public int getCntApplied(Connection conn, int classNo) {
//
//		int cntApplied = -1;
//		PreparedStatement pstmt = null;
//		ResultSet rset = null;
//		String sql = prop.getProperty("getCntApplied");
//		
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, classNo);
//			rset = pstmt.executeQuery();
//			if(rset.next()) {
//				cntApplied = rset.getInt("cnt");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return cntApplied;
//	}

	public int updateBoard(Connection conn, Board b) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getCategory());
			pstmt.setString(3, b.getClassLocation());
			pstmt.setString(4, b.getClassAddress());
			pstmt.setDate(5, b.getLastApplyDate());
			pstmt.setInt(6, b.getCapacity());
			pstmt.setInt(7, b.getPrice());
			pstmt.setDate(8, b.getClassDate());
			pstmt.setInt(9, b.getStartTime());
			pstmt.setInt(10, b.getEndTime());
			pstmt.setString(11, b.getClassContent());
			pstmt.setInt(12, b.getClassNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateImages(Connection conn, Board b) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateImages");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getClassPic1Org());
			pstmt.setString(2, b.getClassPic1Ren());
			pstmt.setString(3, b.getClassPic2Org());
			pstmt.setString(4, b.getClassPic2Ren());
			pstmt.setString(5, b.getClassPic3Org());
			pstmt.setString(6, b.getClassPic3Ren());
			pstmt.setInt(7, b.getClassNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int deleteBoard(Connection conn, int classNo) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int deleteImages(Connection conn, int classNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteImages");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public List<Schedule> selectSchdules(Connection conn, int classNo) {

		List<Schedule> slist = new ArrayList<>();
		Schedule sch = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectSchedules");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				sch = new Schedule();
				sch.setClassNo(rset.getInt("class_no"));
				sch.setScheduleNo(rset.getInt("schedule_no"));
				sch.setUserId(rset.getString("user_id"));
				slist.add(sch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return slist;
	}

	public int refundPoint(Connection conn, List<Schedule> slist, Board b) {

		int refund = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("PointIO");
		
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i = 0; i < slist.size(); i++) {
				pstmt.setString(1, "admin");
				pstmt.setString(2, "O");
				pstmt.setString(3, "수업 삭제 환불");
				pstmt.setInt(4, b.getPrice());
				refund = pstmt.executeUpdate();
			}
			
			if(refund <= 0) return refund;
			
			for(Schedule s : slist) {
				pstmt.setString(1, s.getUserId());
				pstmt.setString(2, "I");
				pstmt.setString(3, "수업 삭제 환불");
				pstmt.setInt(4, b.getPrice());
				refund = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return refund;
	}

	public int deleteSchedules(Connection conn, int classNo) {

		int deleteSch = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteSchedules");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			deleteSch = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return deleteSch;
	}

	public int insertSchedule(Connection conn, int classNo, String userId) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertSchedule");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int enrolPoint(Connection conn, String userId, int price) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("PointIO");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, "O");
			pstmt.setString(3, "수강 신청");
			pstmt.setInt(4, price);
			result = pstmt.executeUpdate();
			
			if(result <= 0) return result;
		
			pstmt.setString(1, "admin");
			pstmt.setString(2, "I");
			pstmt.setString(3, "수강 신청");
			pstmt.setInt(4, price);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteSchedule(Connection conn, int classNo, String userId) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteSchedule");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int cancelPoint(Connection conn, String userId, int price) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("PointIO");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "admin");
			pstmt.setString(2, "O");
			pstmt.setString(3, "수강 취소 환불");
			pstmt.setInt(4, price);
			result = pstmt.executeUpdate();
			
			if(result <= 0) return result;
		
			pstmt.setString(1, userId);
			pstmt.setString(2, "I");
			pstmt.setString(3, "수강 취소 환불");
			pstmt.setInt(4, price);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int noticeToUser(Connection conn, List<Schedule> slist, Board b) {

		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("noticeToUser");
		String title = "수업 취소 관련 메시지 입니다.";
		String content = b.getClassDate() + " 진행 예정이던 \n"
					   + "<" + b.getTitle() + ">\n"
					   + "수업이 튜터의 개인 사정으로 인해 취소되어\n"
					   + "수강 신청하신 유저분들에게 알려드립니다.";
			try {
				pstmt = conn.prepareStatement(sql);
				for(Schedule s : slist) {
					pstmt.setString(1, s.getUserId());
					pstmt.setString(2, title);
					pstmt.setString(3, content);
					result = pstmt.executeUpdate();
					if(result <= 0) return result;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return result;
	}
	
	
}
