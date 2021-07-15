package user.schedule.model.vo;

public class ClassSchedule {
	private int scheduleNo;
	private int classNo;
	private String userId;

	public ClassSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassSchedule(int scheduleNo, int classNo, String userId) {
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
		return "ClassSchedule [scheduleNo=" + scheduleNo + ", classNo=" + classNo + ", userId=" + userId + "]";
	}

}
