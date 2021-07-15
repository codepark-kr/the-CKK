package user.schedule.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.model.vo.Board;
import board.model.vo.BoardWithSch;
import board.model.vo.Schedule;

public class ScheduleDAO {
	Properties prop = new Properties();
	
	public ScheduleDAO() {
		String fileName = "/sql/schedule/schedule-query.properties";
		fileName = ScheduleDAO.class.getResource(fileName).getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Schedule> selectSchedule(Connection conn, String userId) {

		List<Schedule> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectSchedule");
		Schedule s = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				s = new Schedule();
				s.setClassNo(rset.getInt("class_no"));
				s.setScheduleNo(rset.getInt("schedule_no"));
				s.setUserId(rset.getString("user_id"));
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public List<Board> selectBoard(Connection conn, List<Schedule> slist) {

		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBoard");
		Board b = null;
		
		try {
			for(Schedule s : slist) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, s.getClassNo());
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
					b.setTutorName(rset.getString("tutor_Name"));
					b.setCategory(rset.getString("category"));
					
					list.add(b);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}

	public List<Board> selectTutorSchedule(Connection conn, String userId) {
		// selectTutorSchedule
		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectTutorSchedule");
		Board b = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
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
				b.setCategory(rset.getString("category"));;
			
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}



	public List<BoardWithSch> selectRecord(Connection conn, int classNo, int year, int month) {
		List<BoardWithSch> list = new ArrayList<BoardWithSch>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectRecord");
		BoardWithSch b = null;
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, classNo);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);
			/*
			 * pstmt.setInt(1, year); pstmt.setInt(2, month);
			 */
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				b = new BoardWithSch();
				b.setTitle(rset.getString("title"));
				b.setClassNo(rset.getInt("class_no"));
				b.setYear(rset.getInt("year"));
				b.setMonth(rset.getInt("month"));
				list.add(b);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public List<BoardWithSch> selectCowork(Connection conn, int classNo) {

		List<BoardWithSch> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFriend");
		BoardWithSch b = null;
		
		try {
	
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, classNo);
				rset = pstmt.executeQuery();
			
				while(rset.next()) {
					b = new BoardWithSch();
					b.setUserId(rset.getString("user_id"));
					b.setUserName(rset.getString("user_name"));
					b.setPhone(rset.getString("phone"));
					b.setClassNo(rset.getInt("classno"));
					b.setClassContent(rset.getString("content"));
					b.setTitle(rset.getString("title"));
					b.setTutorName(rset.getString("tutor_name"));
					b.setYear(rset.getInt("year"));
					b.setMonth(rset.getInt("month"));
					b.setDay(rset.getInt("day"));
					b.setApplyExpireYn(rset.getString("apply_expire_yn"));

					list.add(b);
				}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}

	/*
	 * public List<BoardWithSch> selectRecord(Connection conn, String userId, int
	 * year, int month) { List<BoardWithSch> list = new ArrayList<BoardWithSch>();
	 * PreparedStatement pstmt = null; ResultSet rset = null; String sql =
	 * prop.getProperty("selectRecord"); BoardWithSch b = null;
	 * 
	 * try { pstmt = conn.prepareStatement(sql); pstmt.setString(1, userId);
	 * pstmt.setInt(2, year); pstmt.setInt(3, month); rset = pstmt.executeQuery();
	 * 
	 * while(rset.next()) { b = new BoardWithSch();
	 * b.setClassNo(rset.getInt("class_no")); b.setTitle(rset.getString("title"));
	 * b.setYear(rset.getInt("year")); b.setMonth(rset.getInt("month"));
	 * list.add(b); }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }finally { close(rset); close(pstmt); }
	 * 
	 * return list; }
	 */

	public List<BoardWithSch> selectTutorRecord(Connection conn, String userId, int year, int month) {
		List<BoardWithSch> tlist = new ArrayList<BoardWithSch>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectTutorRecord");
		BoardWithSch b = null;
				
		try {
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, userId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);
			rset = pstmt.executeQuery();
//			System.out.println(rset.next());
			while(rset.next()) {
				b = new BoardWithSch();
				b.setClassNo(rset.getInt("class_no"));
				b.setTitle(rset.getString("title"));
				b.setYear(rset.getInt("year"));
				b.setMonth(rset.getInt("month"));
//				System.out.println("bbbbbbbbbbbbbbbbbb"+b);
				tlist.add(b);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return tlist;
	}

	
	public List<BoardWithSch> selectRecord(Connection conn, String userId, int year, int month) {
		List<BoardWithSch> list = new ArrayList<BoardWithSch>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectRecord");
		BoardWithSch b = null;
				
		try {
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, userId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);
			rset = pstmt.executeQuery();
//			System.out.println(rset.next());
			while(rset.next()) {
				b = new BoardWithSch();
				b.setClassNo(rset.getInt("class_no"));
				b.setTitle(rset.getString("title"));
				b.setYear(rset.getInt("year"));
				b.setMonth(rset.getInt("month"));
//				System.out.println("bbbbbbbbbbbbbbbbbb"+b);
				list.add(b);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	

}
