package user.tutor.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import point.model.dao.PointDAO;
import user.tutor.model.vo.TutorResume;

public class TutorDAO {
	private Properties prop = new Properties();
	
	public TutorDAO() {
		String fileName = "/sql/member/tutor/tutor-query.properties";
		fileName = PointDAO.class.getResource(fileName).getPath();
		
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int writeResume(Connection conn, TutorResume tutorResume, String tutorId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = null;
		query = prop.getProperty("insertResume"); 

		try {
			
				//미완성쿼리문을 가지고 객체생성.
				pstmt = conn.prepareStatement(query);
				//쿼리문미완성
				pstmt.setString(1, tutorResume.getTutorId());
				pstmt.setString(2, tutorResume.getAwardRecord());
				pstmt.setString(3, tutorResume.getCareer());
				pstmt.setString(4, "".equals(tutorResume.getProfileOrg()) ? "default" : tutorResume.getProfileOrg());		
				pstmt.setString(5, tutorResume.getProfileRen());
				pstmt.setString(6, tutorResume.getCertC1());
				pstmt.setString(7, tutorResume.getCertN1());
				pstmt.setString(8, tutorResume.getCertC2());
				pstmt.setString(9, tutorResume.getCertN2());
				pstmt.setString(10, tutorResume.getCertC3());
				pstmt.setString(11, tutorResume.getCertN3());
				
				result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
			close(pstmt);
		}
		return result;
	}

	public TutorResume selectTutorResume(Connection conn, String tutorId) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectOne");
		ResultSet rset = null;
		TutorResume tutorResume = null;
	
		try {
			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, tutorId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				tutorResume = new TutorResume();
				tutorResume.setTutorId(rset.getString("tutor_id"));
				tutorResume.setAwardRecord(rset.getString("award_record"));
				tutorResume.setCareer(rset.getString("career"));
				tutorResume.setProfileOrg(rset.getString("profile_org"));
				tutorResume.setProfileRen(rset.getString("profile_ren"));
				tutorResume.setCertC1(rset.getString("cert_n_1"));
				tutorResume.setCertN1(rset.getString("cert_c_1"));
				tutorResume.setCertC2(rset.getString("cert_n_2"));
				tutorResume.setCertN2(rset.getString("cert_c_2"));
				tutorResume.setCertC3(rset.getString("cert_n_3"));
				tutorResume.setCertN3(rset.getString("cert_c_3"));
	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return tutorResume;
	}

	public int updateResume(Connection conn,TutorResume tutorResume,  String tutorId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateResume");
	
		try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, tutorResume.getAwardRecord());
				pstmt.setString(2, tutorResume.getCareer());
				pstmt.setString(3, "".equals(tutorResume.getProfileOrg()) ? "default" : tutorResume.getProfileOrg());		
				pstmt.setString(4, tutorResume.getProfileRen());
				pstmt.setString(5, tutorResume.getCertC1());
				pstmt.setString(6, tutorResume.getCertN1());
				pstmt.setString(7, tutorResume.getCertC2());
				pstmt.setString(8, tutorResume.getCertN2());
				pstmt.setString(9, tutorResume.getCertC3());
				pstmt.setString(10, tutorResume.getCertN3());
				pstmt.setString(11, tutorResume.getTutorId());
				
				result = pstmt.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
				
		
			close(pstmt);
		}
	
		return result;
	}

	public int writeResumeWithSubmit(Connection conn, TutorResume tuResume) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = null;
		query = prop.getProperty("insertResume"); 

		try {
			
				//미완성쿼리문을 가지고 객체생성.
				pstmt = conn.prepareStatement(query);
				//쿼리문미완성
				pstmt.setString(1, tuResume.getTutorId());
				pstmt.setString(2, tuResume.getAwardRecord());
				pstmt.setString(3, tuResume.getCareer());
				pstmt.setString(4, tuResume.getProfileOrg());		
				pstmt.setString(5, tuResume.getProfileRen());
				pstmt.setString(6, tuResume.getCertC1());
				pstmt.setString(7, tuResume.getCertN1());
				pstmt.setString(8, tuResume.getCertC2());
				pstmt.setString(9, tuResume.getCertN2());
				pstmt.setString(10, tuResume.getCertC3());
				pstmt.setString(11, tuResume.getCertN3());
				
				result = pstmt.executeUpdate();
				
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public void updateYNP(Connection conn, TutorResume tuResume) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateYNP");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, tuResume.getTutorId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int updateResumeWithSubmit(Connection conn, TutorResume tuResume) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateResume");
		try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, tuResume.getAwardRecord());
				pstmt.setString(2, tuResume.getCareer());
				pstmt.setString(3, tuResume.getProfileOrg());		
				pstmt.setString(4, tuResume.getProfileRen());
				pstmt.setString(5, tuResume.getCertC1());
				pstmt.setString(6, tuResume.getCertN1());
				pstmt.setString(7, tuResume.getCertC2());
				pstmt.setString(8, tuResume.getCertN2());
				pstmt.setString(9, tuResume.getCertC3());
				pstmt.setString(10, tuResume.getCertN3());
				pstmt.setString(11, tuResume.getTutorId());
				
				result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
}
