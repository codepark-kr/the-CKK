package user.admin.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import user.tutor.model.vo.TutorResume;
import user.vo.User;

public class AdminDAO {
	
private Properties prop = new Properties();

	public AdminDAO() {
//		(예빈)
		//MemberDAO객체 생성시, 
		//resources/sql/member/member-query.properties 에 작성된 sql문을
		//필드 prop에 저장한다.
		
		//buildpath /WEB-INF/classes를 루트디렉토리로 참조한다.
		String fileName = "/sql/member/admin/admin-query.properties";
		fileName =  AdminDAO.class.getResource(fileName)
								   .getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public User selectOne(Connection conn, String userId) {
		User u = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		
		try {
			//1. PreparedStatement객체생성, 미완성쿼리 전달
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			//2. 실행 
			rset = pstmt.executeQuery();
			
			//3.ResultSet -> Member
			if(rset.next()) {
				u = new User();
				u.setUserId(rset.getString("user_id"));
				u.setCommGrade(rset.getString("comm_grade"));
				u.setPassword(rset.getString("password"));
				u.setUserName(rset.getString("user_name"));
				u.setGender(rset.getString("gender"));
				u.setBirthday(rset.getDate("birthday"));
				u.setEmail(rset.getString("email"));
				u.setAddress(rset.getString("address"));
				u.setPhone(rset.getString("phone"));
				u.setEnrollDate(rset.getDate("enroll_date"));
				u.setPointSum(rset.getInt("point_sum"));
				u.setResumeYNP(rset.getString("resume_ynp"));
				
				System.out.println(u.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return u;
	}

	public int userCount(Connection conn, String tbl, String col, String target) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countAll");
		System.out.println("param-test@DAO = "+tbl+" "+col+" "+target);
		sql = sql.replace("○", tbl);
		sql = sql.replace("●", col);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + target + "%");
			
			rset= pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt("COUNTED");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int getRevenue(Connection conn, String io) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("pAmount");
		int revenue = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, io);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
			int base = rset.getInt("BASE");
			String grade = rset.getString("GRADE");
			
			switch(grade) {
			case "T1" : revenue += (0.01 * base);
				break;
			case "T2" : revenue += (0.02 * base);
				break;
			case "T3" : revenue += (0.03 * base);
				break;
			case "T4" : revenue += (0.04 * base);
				break;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return revenue;
	}

	public int getPCnt(Connection conn, String io) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("pCnt");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, io);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("PCNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}


	public List<Integer> getRW(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Integer> rw = new ArrayList<>();
		String sql = prop.getProperty("rWeek");
		int revenue = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			for(int i = -7; i <= 1; i++) {
				String pres = Integer.toString(i);
				String prev = Integer.toString(i-1);

				
				pstmt.setString(1, pres);
				pstmt.setString(2, prev);
				System.out.println(i);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					int base = rset.getInt("AMOUNT");
					String grade = rset.getString("G");
					
					switch(grade) {
					case "T1" : revenue += (0.01 * base);
						break;
					case "T2" : revenue += (0.02 * base);
						break;
					case "T3" : revenue += (0.03 * base);
						break;
					case "T4" : revenue += (0.04 * base);
						break;
						}
					}
//				주의! 리스트에 해당 데이터를 추가한 후 revenue = 0; 으로 초기화 해주지 않으면 누적 수익이 그대로 기록된다.
//				"일일" 매출을 알고 싶은 경우라면 반드시 초기화를 진행할 것.
				rw.add(revenue);
				revenue = 0;
					System.out.println("revenue = "+revenue);
//					DecimalFormat을 대입하여 String으로 리턴받는 경우 chart에 값을 대입하기 불편하므로 생략하고 view단에서 진행한다.
//					DecimalFormat revF = new DecimalFormat("###,###,###,###,###,###,###원");
//					String rWeek = revF.format(revenue);
				}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return rw;
	}
	
			
	public List<Integer> getMW(Connection conn, String grade) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Integer> mw = new ArrayList<>();
		String sql = prop.getProperty("mWeek");
		int count = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			
			for(int i = -7; i <= 1; i++) {
				String pres = Integer.toString(i);
				String prev = Integer.toString(i-1);

				pstmt.setString(1, "%"+grade+"%");
				pstmt.setString(2, pres);
				pstmt.setString(3, prev);
				System.out.println(i);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					count = rset.getInt("MEMBERWEEK");
				
//					DecimalFormat memF = new DecimalFormat("###,###,###,###,###,###,###명");
//					String mWeek = memF.format(count);
					System.out.println("count@DAO = "+count);
					mw.add(count);
				}
				}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return mw;
	}
	
	
//	(준혁)
	public List<User> selectAllUsers(Connection conn) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<User> uList = new ArrayList<>();
		User u = null;
		String sql = prop.getProperty("selectAllUsers");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				u = new User();
				u.setUserId(rset.getString("user_id"));
				u.setCommGrade(rset.getString("comm_grade"));
				u.setPassword(rset.getString("password"));
				u.setUserName(rset.getString("user_name"));
				u.setGender(rset.getString("gender"));
				u.setBirthday(rset.getDate("birthday"));
				u.setEmail(rset.getString("email"));
				u.setAddress(rset.getString("address"));
				u.setPhone(rset.getString("phone"));
				u.setEnrollDate(rset.getDate("enroll_date"));
				u.setPointSum(rset.getInt("point_sum"));
				u.setResumeYNP(rset.getString("resume_ynp"));
				uList.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return uList;
	}

	public List<User> selectProcessUsers(Connection conn) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<User> tList = new ArrayList<>();
		User u = null;
		String sql = prop.getProperty("selectProcessUsers");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				u = new User();
				u.setUserId(rset.getString("user_id"));
				u.setCommGrade(rset.getString("comm_grade"));
				u.setPassword(rset.getString("password"));
				u.setUserName(rset.getString("user_name"));
				u.setGender(rset.getString("gender"));
				u.setBirthday(rset.getDate("birthday"));
				u.setEmail(rset.getString("email"));
				u.setAddress(rset.getString("address"));
				u.setPhone(rset.getString("phone"));
				u.setEnrollDate(rset.getDate("enroll_date"));
				u.setPointSum(rset.getInt("point_sum"));
				u.setResumeYNP(rset.getString("resume_ynp"));
				tList.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return tList;
	}

	
	public int updateResume(Connection conn, String tutorId, String resumeYNP) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateResume");
		String comm = resumeYNP.equals("Y") ? "T4" : "US";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, resumeYNP);
			pstmt.setString(2, comm);
			pstmt.setString(3, tutorId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
//		System.out.println("결과확인 = " + result);
		return result;
	}

	public User selectOneUser(Connection conn, String userId) {

		User u = new User();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOneUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				u.setUserId(rset.getString("user_id"));
				u.setCommGrade(rset.getString("comm_grade"));
				u.setPassword(rset.getString("password"));
				u.setUserName(rset.getString("user_name"));
				u.setGender(rset.getString("gender"));
				u.setBirthday(rset.getDate("birthday"));
				u.setEmail(rset.getString("email"));
				u.setAddress(rset.getString("address"));
				u.setPhone(rset.getString("phone"));
				u.setEnrollDate(rset.getDate("enroll_date"));
				u.setPointSum(rset.getInt("point_sum"));
				u.setResumeYNP(rset.getString("resume_ynp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return u;
	}

	public TutorResume selectTutorResume(Connection conn, String userId) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOneUser");
		TutorResume tutorResume = new TutorResume();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				
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

}
