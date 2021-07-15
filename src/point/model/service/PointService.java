package point.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import point.model.dao.PointDAO;
import point.model.vo.Point;
import user.vo.User;

public class PointService {

	PointDAO pointDAO = new PointDAO();
	
	
	public int updatePoint(Point point) {
		Connection conn = getConnection();
		int result = pointDAO.updatePoint(conn, point);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}
	
	

	
	public List<Point> viewPoint(String userId) {

		Connection conn = getConnection();
		List<Point> pointView = pointDAO.viewPoint(conn, userId);
		close(conn);

		return pointView;

	}


	public User selectCommGradeOne(String userId) {
		Connection conn = getConnection();
		User grade = pointDAO.selectCommGradeOne(conn, userId);
		close(conn);
		System.out.println("grade@service= " + grade);
		return grade;
	}


//	public void commToAdmin(Point adminPoint) {
//		Connection conn = getConnection();
//		pointDAO.commToAdmin(conn, adminPoint);
//		close(conn);
//	}

	public int updatePointWithAdComm(Point tutorPoint, Point adminPoint) {
		Connection conn = getConnection();
		int result = pointDAO.updatePoint(conn, tutorPoint);
		if(result > 0) {
			pointDAO.commToAdmin(conn, adminPoint);
			commit(conn);
		}
		else {
			rollback(conn);
		}
		return result;
	}
	public int minusPoint(Point point) {
		Connection conn = getConnection();
		
		int result = pointDAO.updatePoint(conn, point);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}

// (예빈) 모든 포인트 로그 조회
	public List<Point> pointAll() {
		Connection conn = getConnection();
		List<Point> pointAll = pointDAO.pointAll(conn);
		close(conn);

		return pointAll;
	}


}
