package user.tutor.model.vo;

import java.io.Serializable;

public class Schedule implements Serializable{

	private int scheduleNo;
	private int classNo;
	private String userId;
	
	public Schedule() {}
	public Schedule(int scheduleNo, int classNo, String userId) {
		super();
		this.scheduleNo = scheduleNo;
		this.classNo = classNo;
		this.userId = userId;
	}
	
	public int getScheduleNo() {
		return scheduleNo;
	}
	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}
	public int getClassNo() {
		return classNo;
	}
	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "Schedule [scheduleNo=" + scheduleNo + ", classNo=" + classNo + ", userId=" + userId + "]";
	}
	
	
}
