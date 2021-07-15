package center.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;

import static common.JDBCTemplate.*;

import center.model.exception.CenterException;
import center.model.vo.Center;
import center.model.vo.CenterComment;
import center.model.vo.CenterWithCommentCnt;

public class CenterDAO {
	private Properties prop = new Properties();
	
	public CenterDAO() {
		
		try {
			String fileName = CenterDAO.class.getResource("/sql/center/center-query.properties").getPath();
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	
	}
	
	//cs보드 전체 가져오기
	public List<Center> selectCenterList(Connection conn, int cPage, int numPerPage) {
		
		List<Center> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectCenterList");
		System.out.println(cPage  + " : " +  numPerPage);
		
		int startNum = (cPage-1) * numPerPage + 1 ;
		int endNum = cPage*numPerPage;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				CenterWithCommentCnt b = new CenterWithCommentCnt();
				b.setCsNo(rset.getInt("cs_no"));
				b.setCsTitle(rset.getString("cs_title"));
				b.setUserId(rset.getString("user_id"));
				b.setCsContent(rset.getString("cs_content"));
				b.setCsFileOrg(rset.getString("cs_file_org"));
				b.setCsFileRen(rset.getString("cs_file_ren"));
				b.setCsWriteDate(rset.getDate("cs_write_date"));
				b.setCsSecretYN(rset.getString("cs_secret_yn"));
				list.add(b);
			}
		
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	//cs보드 하나 가져오기
	public Center selectOne(Connection conn, int csNo) {
		Center center = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selectOne");
		
		try {
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1,csNo);
		rset = pstmt.executeQuery();
		
			if(rset.next()) {
				center = new Center();
				center.setCsNo(rset.getInt("cs_no"));
				center.setCsTitle(rset.getString("cs_title"));
				center.setUserId(rset.getString("user_id"));
				center.setCsContent(rset.getString("cs_content"));
				center.setCsFileOrg(rset.getString("cs_file_org"));
				center.setCsFileRen(rset.getString("cs_file_ren"));
				center.setCsWriteDate(rset.getDate("cs_write_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return center;
	}

	//cs보드 댓글 가져오기
	public List<CenterComment> selectCommentList(Connection conn, int csNo) {
		List<CenterComment> commentList = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCommentList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, csNo);
			rset = pstmt.executeQuery();
			
			commentList = new ArrayList<>();
			while(rset.next()) {
				CenterComment bc = new CenterComment();
				bc.setAdminId(rset.getString("admin_id"));
				bc.setCsaContent(rset.getString("csa_content"));
				bc.setCsaDate(rset.getDate("csa_date"));
				bc.setCsNo(rset.getInt("cs_no"));
				
			
				commentList.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return commentList;
	}

	//마지막번호 가져오는건데
	public int selectCenterLastSeq(Connection conn) {
		int csNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql= prop.getProperty("selectCenterLastSeq");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				csNo = rset.getInt("cs_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return csNo;
	}

	//cs보드 추가
	public int insertCenter(Connection conn, Center center) {
		int result =0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertCenter");
		System.out.println(center);
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, center.getUserId());
			pstmt.setString(2, center.getCsTitle());
			pstmt.setString(3, center.getCsContent());
			pstmt.setString(4, center.getCsFileOrg());
			pstmt.setString(5, center.getCsFileRen());
			pstmt.setString(6, center.getCsSecretYN());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	//cs보드 댓글 달기
	public int insertCenterComment(Connection conn, CenterComment centerComment) {
		int result =0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertCenterComment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, centerComment.getCsNo());
			pstmt.setString(2,  centerComment.getAdminId());
			pstmt.setString(3, centerComment.getCsaContent());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			
			throw new CenterException("댓글 등록 오류!",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	// cs보드 갯수 세는거
	public int selectCenterCount(Connection conn) { 
		
		PreparedStatement pstmt = null;
		int totalMember = 0;
		ResultSet rset = null;
		String query = prop.getProperty("selectCenterCount");
		
		try{
			
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				totalMember = rset.getInt("cnt");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		
		return totalMember;
	}
		

	
}
