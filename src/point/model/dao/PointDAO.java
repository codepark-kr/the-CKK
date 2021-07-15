package point.model.dao;

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

import point.model.vo.Point;
import user.vo.User;

public class PointDAO {
	
	Properties prop = new Properties();
	
	public PointDAO() {
		String fileName = "/sql/point/point-query.properties";
		fileName = PointDAO.class.getResource(fileName).getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int updatePoint(Connection conn, Point point) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updatePoint");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, point.getUserId());
			pstmt.setString(2, point.getPointIO());
			pstmt.setString(3, point.getPointIO() == "I" ? "포인트 충전" : "포인트 환전");
			pstmt.setDouble(4, point.getPointAmount());

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}

	public List<Point> viewPoint(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("pointView");
		ResultSet rset = null;
		Point point = null;
		List<Point> pointView = null;
	
		try {

			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			pointView = new ArrayList<>();
			
			
			while(rset.next()) {
				point = new Point();
		

//이게 오류뜸
				point.setLogNum(rset.getInt("log_no"));
				point.setUserId(rset.getString("user_id"));
				point.setPointIO(rset.getString("point_io"));
				point.setPointContent(rset.getString("point_content"));
				point.setPointAmount(rset.getInt("point_amount"));
				point.setPointIODate(rset.getDate("point_io_date"));
				
				pointView.add(point);
			
//			    System.out.println("="+pointView);
	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
//		System.out.println(member);
		return pointView;
	}

	public User selectCommGradeOne(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectCommGradeOne");
		ResultSet rset = null;
		User grade = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				grade = new User();
				grade.setCommission(rset.getDouble("comm"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		System.out.println("grade@dao= " + grade);
		return grade;
	}

	public void commToAdmin(Connection conn, Point adminPoint) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("commToAdmin");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adminPoint.getUserId());
			pstmt.setString(2, adminPoint.getPointIO());
			pstmt.setString(3, adminPoint.getPointContent());
			pstmt.setInt(4, adminPoint.getPointAmount());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
	}

	public List<Point> pointAll(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("pointAll");
		ResultSet rset = null;
		Point point = null;
		List<Point> pointAll = null;
	
		try {

			pstmt =  conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			pointAll = new ArrayList<>();
			
			while(rset.next()) {
				point = new Point();
				point.setLogNum(rset.getInt("log_no"));
				point.setUserId(rset.getString("user_id"));
				point.setPointIO(rset.getString("point_io"));
				point.setPointContent(rset.getString("point_content"));
				point.setPointAmount(rset.getInt("point_amount"));
				point.setPointIODate(rset.getDate("point_io_date"));
				
				pointAll.add(point);
			
//			    System.out.println("="+pointView);
	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
//		System.out.println(member);
		return pointAll;

	}

}
