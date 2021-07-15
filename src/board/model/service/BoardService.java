package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.model.dao.BoardDAO;
import board.model.vo.Board;
import board.model.vo.Schedule;
import user.tutor.model.vo.Review;
import user.tutor.model.vo.TutorResume;
import user.vo.User;

public class BoardService {

	private BoardDAO boardDAO = new BoardDAO();

	public Map<String, Object> getBoardList(int numPerPage, int cPage, Map<String, Object> param) {
		
		Connection conn = getConnection();
		
		Map<String, Object> map = boardDAO.getBoardList(conn, numPerPage, cPage, param);
		
		close(conn);
		
		return map;
	}

	public Map<String, Object> getBoardView(int classNo) {

		Connection conn = getConnection();
		
		Map<String, Object> map = new HashMap<>();
		
		Board board = boardDAO.selectOneBoard(conn, classNo);
		
		User user = boardDAO.selectOneUser(conn, board.getTutorId());
		
		TutorResume tr = boardDAO.selectOneTutorResume(conn, board.getTutorId());
		
		List<Review> reviewList = boardDAO.getReview(conn, board.getTutorId());
		
//		int cntApplied = boardDAO.getCntApplied(conn, classNo);
		
		List<Schedule> scheduleList = boardDAO.selectSchdules(conn, classNo);
		
		close(conn);
		
		map.put("board", board);
		map.put("user", user);
		map.put("tutorResume", tr);
		map.put("reviewList", reviewList);
		map.put("scheduleLost", scheduleList);
		
		System.out.println("map@service = " + map);
		
		return map;
	}

	public int insertBoard(Board b) {

		Connection conn = getConnection();
		
		int result = boardDAO.insertBoard(conn, b);
		
		if(result <= 0) {
			rollback(conn);
			return result;
		}
		
		int lastSeq = boardDAO.selectBoardLastSeq(conn);
		b.setClassNo(lastSeq);
		
		result = boardDAO.insertImages(conn, b);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	public Board selectOneBoard(int classNo) {

		Connection conn = getConnection();
		
		Board b = boardDAO.selectOneBoard(conn, classNo);
		
		close(conn);
		
		return b;
	}

	public int updateBoard(Board b) {

		Connection conn = getConnection();
		
		int result = boardDAO.updateBoard(conn, b);
		
		if(result <= 0) {
			rollback(conn);
			return result;
		}
		
		result = boardDAO.updateImages(conn, b);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	public Map<String, Object> deleteBoard(int classNo) {

		Connection conn = getConnection();
		
		Map<String, Object> map = new HashMap<>();
		//스케쥴 가져와서
		List<Schedule> slist = boardDAO.selectSchdules(conn, classNo);
		//보드 객체 가져와서 
		Board b = boardDAO.selectOneBoard(conn, classNo);
		map.put("board", b);
		
		// 학생 환불
		if(!slist.isEmpty() && slist != null) {
			int refund = boardDAO.refundPoint(conn, slist, b);
			if(refund <= 0) {
				rollback(conn);
				map.put("result", refund);
				return map;
			}
		
		// 스케쥴 삭제
			int delSch = boardDAO.deleteSchedules(conn, classNo);
			if(delSch <= 0) {
				rollback(conn);
				if(map.containsKey("result")) map.replace("result", delSch);
				else map.put("result", delSch);
				return map;
			}
		}

		// 클래스 이미지 테이블 삭제
		int delImg = boardDAO.deleteImages(conn, classNo);
		if(delImg <= 0) {
			rollback(conn);
			if(map.containsKey("result")) map.replace("result", delImg);
			else map.put("result", delImg);
			return map;
		}
		// 클래스 테이블 삭제
		int result = boardDAO.deleteBoard(conn, classNo);
		if(result <= 0) {
			rollback(conn);
			if(map.containsKey("result")) map.replace("result", result);
			else map.put("result", delImg);
			return map;
		}
		
		result = boardDAO.noticeToUser(conn, slist, b);
		
		if(result > 0) {
			if(map.containsKey("result")) map.replace("result", result);
			else map.put("result", result);
			commit(conn);
		}
		else {
			if(map.containsKey("result")) map.replace("result", result);
			else map.put("result", result);
			rollback(conn);
		}
		
		return map;
	}

	public int enrolClass(int classNo, String userId) {

		Connection conn = getConnection();
		
		Board b = boardDAO.selectOneBoard(conn, classNo);
		int capacity = b.getCapacity();
		List<Schedule> list = boardDAO.selectSchdules(conn, classNo);
		int count = list.size();
		if(count >= capacity) return -1;
		//스케쥴 등록
		int result = boardDAO.insertSchedule(conn, classNo, userId);
		if(result <= 0 ) {
			rollback(conn);
			return result;
		}
		//포인트 로그 학생 - 관리자 +
		result = boardDAO.enrolPoint(conn, userId, b.getPrice());
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		return result;
	}

	public int cancelClass(int classNo, String userId) {
		
		Connection conn = getConnection();
		
		Board b = boardDAO.selectOneBoard(conn, classNo);
		
		//스케쥴 취소
		int result = boardDAO.deleteSchedule(conn, classNo, userId);
		if(result <= 0 ) {
			rollback(conn);
			return result;
		}
		//포인트 로그 학생 + 관리자 -
		result = boardDAO.cancelPoint(conn, userId, b.getPrice());
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		return result;
	}

	
}
