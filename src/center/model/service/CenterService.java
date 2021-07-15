package center.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import center.model.dao.CenterDAO;
import center.model.vo.Center;
import center.model.vo.CenterComment;

public class CenterService{
	
	private CenterDAO centerDAO = new CenterDAO();
	
	
	


	public List<Center> selectCenterList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Center> list = centerDAO.selectCenterList(conn, cPage, numPerPage);
		close(conn);
		return list;
	}





	public int selectCenterCount() {
		Connection conn = getConnection();
		int totalCenterCount = centerDAO.selectCenterCount(conn);
		close(conn);
		return totalCenterCount;
	}





	public Center selectOne(int csNo) {
		Connection conn = getConnection();
		int result =0;
		
		Center center = centerDAO.selectOne(conn, csNo);
		if(result>0) 
			commit(conn);
		else rollback(conn);
		
		close(conn);
		return center;
	}





	public List<CenterComment> selectCenterList(int csNo) {
		Connection conn = getConnection();
		List<CenterComment> commentList = centerDAO.selectCommentList(conn, csNo);
		close(conn);
		return commentList;
	}





	public List<CenterComment> selectCommentList(int csNo) {
		Connection conn = getConnection();
		List<CenterComment> commentList = centerDAO.selectCommentList(conn, csNo);
		close(conn);
		return commentList;
	}





	public int insertCenter(Center center) {
		Connection conn = getConnection();
		
		int result = centerDAO.insertCenter(conn,center);
	
		if(result>0)
		commit(conn);
		else
		rollback(conn);
		
		int csNo = centerDAO.selectCenterLastSeq(conn); //마지막으로 추가된 고객센터 문의글의 cs_no를 가져온다.
		center.setCsNo(csNo);
		
		close(conn);
		
		return result;
	}





	public int insertCenterComment(CenterComment centerComment) {
		Connection conn = getConnection();
		int result = centerDAO.insertCenterComment(conn, centerComment);
		
		if(result>0) 
		commit(conn);
		else
		rollback(conn);
		
		close(conn);
		
		return result;
	}

	





	
	
}
