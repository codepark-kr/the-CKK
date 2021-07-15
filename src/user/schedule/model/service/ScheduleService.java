package user.schedule.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.model.vo.Board;
import board.model.vo.BoardWithSch;
import board.model.vo.Schedule;
import user.schedule.model.dao.ScheduleDAO;

public class ScheduleService {
	private ScheduleDAO scheduleDAO = new ScheduleDAO();


	public Map<String, Object> selectSchedule(String userId) {
		
		Connection conn = getConnection();
		
		Map<String, Object> map = new HashMap<>();
		List<Board> blist = null;
		List<Board> tlist = null;
		
		List<Schedule> slist = scheduleDAO.selectSchedule(conn, userId);
			
		blist = scheduleDAO.selectBoard(conn, slist);

		tlist = scheduleDAO.selectTutorSchedule(conn, userId);
			
		map.put("tlist", tlist);
		map.put("slist", slist);
		map.put("blist", blist);
		
//		System.out.println("map@service = " + map);
//		System.out.println("유저 일정 " +userId);
//		
		close(conn);
		
		return map;
	}


	public List<BoardWithSch> selectRecord(int classNo, int year, int month) {
		Connection conn = getConnection();
		Map<String, Object> map = new HashMap<>();
		List<BoardWithSch> list = null;
		
		list =  scheduleDAO.selectRecord(conn,classNo, year, month);
	
		map.put("sclist", list);
		close(conn);
		return list;
		/*
		 * Connection conn = getConnection(); Map<String, Object> map = new HashMap<>();
		 * List<BoardWithSch> list = null;
		 * 
		 * list = scheduleDAO.selectRecord(conn,year, month); map.put("sclist", list);
		 * close(conn); return list;
		 */
	}


	public List<BoardWithSch> selectCowork(int classNo) {
		Connection conn = getConnection();
		Map<String, Object> map = new HashMap<>();
		List<BoardWithSch> list = null;
		
		list =  scheduleDAO.selectCowork(conn, classNo);
		map.put("colist", list);
		close(conn);
		return list;
	}

	/*
	 * public List<BoardWithSch> selectRecord(String userId, int year, int month) {
	 * Connection conn = getConnection(); Map<String, Object> map = new HashMap<>();
	 * List<BoardWithSch> list = null;
	 * 
	 * list = scheduleDAO.selectRecord(conn, userId, year, month); map.put("sclist",
	 * list); close(conn); return list; }
	 * 
	 */
	public Map<String, Object> selectRecord(String userId, String userComm, int year, int month) {
		Connection conn = getConnection();
		Map<String, Object> map = new HashMap<>();
		List<BoardWithSch> list = null;
		List<BoardWithSch> tlist = null;
		
		list =  scheduleDAO.selectRecord(conn, userId, year, month);
	   
		if(!(userComm.equals("US") || userComm.equals("AD"))){
	    	tlist = scheduleDAO.selectTutorRecord(conn, userId, year, month);
	    	map.put("tlist", tlist);
	    }
	   
		map.put("sclist", list);
		close(conn);
		return map;
	}

	
}
