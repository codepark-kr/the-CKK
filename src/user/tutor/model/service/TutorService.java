package user.tutor.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;

import user.tutor.model.dao.TutorDAO;
import user.tutor.model.vo.TutorResume;

public class TutorService {
	
	private TutorDAO tutorDAO = new TutorDAO();
	
	public int writeResume(TutorResume tutorResume, String profileOrg) {
		Connection conn = getConnection();
		int result = tutorDAO.writeResume(conn, tutorResume, profileOrg);
		if(result > 0) {
			commit(conn);
		}
		else	rollback(conn);
		close(conn);
//		System.out.println("@service="+tutorResume);
		return result;
	}
	
	public TutorResume selectTutorResume(String tutorId) {
		Connection conn = getConnection();
		System.out.println(tutorId);
		TutorResume result = tutorDAO.selectTutorResume(conn, tutorId);
		close(conn);
		return result; 
	}
	
	public int updateResume(TutorResume tutorResume, String tutorId) {
		Connection conn = getConnection();
		int result = tutorDAO.updateResume(conn,tutorResume, tutorId);
		if(result > 0)	commit(conn);
		else	rollback(conn);
		close(conn);
//		System.out.println("@service="+tutorResume);
		return result;
	}

	public int writeResumeWithSubmit(TutorResume tuResume) {
		Connection conn = getConnection();
		int result = tutorDAO.writeResumeWithSubmit(conn, tuResume);
		if(result > 0) {
			//ynp를 p로 바꿔주는 메소드 함께 실행
			tutorDAO.updateYNP(conn, tuResume);
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		System.out.println("intResume@Service = " +result);
		close(conn);
		return result;
	}

	public int updateResumeWithSubmit(TutorResume tuResume) {
		Connection conn = getConnection();
		int result = tutorDAO.updateResumeWithSubmit(conn, tuResume);
		if(result > 0) {
			//ynp를 p로 바꿔주는 메소드 함께 실행
			tutorDAO.updateYNP(conn, tuResume);
			commit(conn);
		}
		else rollback(conn);
		close(conn);
		
		System.out.println("intResume@Service = " +result);
		return result;
	}

}
